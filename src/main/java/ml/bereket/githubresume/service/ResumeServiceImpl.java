package ml.bereket.githubresume.service;

import lombok.RequiredArgsConstructor;
import ml.bereket.githubresume.dto.GithubProfile;
import ml.bereket.githubresume.dto.Repository;
import ml.bereket.githubresume.dto.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final GithubApiService githubApiService;

    @Override
    public GithubProfile buildProfile(String username) {

        Optional<User> user = githubApiService.getUserInfo(username);
        if(user.isEmpty()) return null;

        GithubProfile profile = new GithubProfile();
        profile.setUser(user.get());

        Repository[] repositories = githubApiService.getUserRepositories(user.get().reposUrl);

        if(repositories.length != 0){
            //sort repositories by no. of stars first then by forks and set to profile
            List<Repository> sortedRepositories = getReposSortedByPopularity(repositories);
            profile.setRepositories(sortedRepositories);
        }

        Map<String, Double> totalLanguageOccurrence = getCodebaseSizeSummaryByLanguage(repositories);
        Map<String, Double> languagesRatio = calculateLanguagesRatio(totalLanguageOccurrence);
        profile.setLanguages(languagesRatio);

        return profile;
    }

    @Override
    public Map<String, Double> calculateLanguagesRatio(Map<String, Double> totalLanguageOccurrence){

        Map<String, Double> languagesRatio = new HashMap<>();
        //get the total amount of code size for all languages
        double totalCodeBaseSize = totalLanguageOccurrence.values().stream().mapToDouble(Double::doubleValue).sum();

        for(String language : totalLanguageOccurrence.keySet()){
            double occurrence = totalLanguageOccurrence.get(language);
            languagesRatio.put(language, (occurrence/totalCodeBaseSize * 100));
        }
        return languagesRatio;
    }

    public  Map<String, Double> getCodebaseSizeSummaryByLanguage(Repository[] repositories) {

        Map<String, Double> totalCodebaseSizeByLanguage = new HashMap<>();
        for(Repository repository : repositories){
            String languagesUrl = repository.languagesUrl;
            if(StringUtils.hasText(languagesUrl)){
                //get the language: occurrence/prevalence map for the current repository from Github
                Map<String, Double> languageOccurrence = githubApiService.languageRatioForRepository(repository.languagesUrl);
                for(String language : languageOccurrence.keySet()){
                    //if the language already exists, update the total occurrence by adding the current data
                    if(totalCodebaseSizeByLanguage.containsKey(language)){
                        double occurrence = totalCodebaseSizeByLanguage.get(language);
                        occurrence += languageOccurrence.get(language);
                        totalCodebaseSizeByLanguage.put(language, occurrence);
                    } else //if it is a new language, create a new entry
                        totalCodebaseSizeByLanguage.put(language, languageOccurrence.get(language));
                }
            }
        }
        return totalCodebaseSizeByLanguage;
    }

    public List<Repository> getReposSortedByPopularity(Repository[] repositories) {
        Comparator<Repository> comparator = Comparator.comparing((Repository repo) -> repo.stargazersCount).reversed()
                .thenComparing(Repository::getForksCount, Comparator.reverseOrder());
        return Arrays.stream(repositories).sorted(comparator).collect(Collectors.toList());
    }
}
