package ml.bereket.githubresume.service;

import ml.bereket.githubresume.dto.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class ResumeServiceImplTest {

    private ResumeServiceImpl resumeServiceImpl;

    @Mock
    private GithubApiService githubApiService;

    @BeforeEach
    void setUp() {
        resumeServiceImpl = new ResumeServiceImpl(githubApiService);
    }

    @Test
    void checkCalculateLanguagesRatioTest(){

        Map<String, Double> totalLanguageOccurrence = new HashMap<>();
        totalLanguageOccurrence.put("Java", 500d);
        totalLanguageOccurrence.put("C", 100d);
        totalLanguageOccurrence.put("Groovy", 400d);

        Map<String, Double> ratio = resumeServiceImpl.calculateLanguagesRatio(totalLanguageOccurrence);
        //500/1000 = 50%
        Assertions.assertEquals(ratio.get("Java"), 50d);
        //100/1000 = 10%
        Assertions.assertEquals(ratio.get("C"), 10d);
        //400/1000 = 40%
        Assertions.assertEquals(ratio.get("Groovy"), 40d);
    }

    @Test
    void checkGetCodebaseSizeSummaryByLanguageTest(){

        List<Repository> repositories = new ArrayList<>();
        Stream.of("Java", "Java", "C", "Groovy").forEach( lang -> {
            Repository repository = new Repository();
            repository.languagesUrl = lang;
            repositories.add(repository);
        });

        Mockito.when(githubApiService.languageRatioForRepository("Java")).thenReturn(Collections.singletonMap("Java", 500d)).thenReturn(Collections.singletonMap("Java", 100d));
        Mockito.when(githubApiService.languageRatioForRepository("C")).thenReturn(Collections.singletonMap("C", 300d));
        Mockito.when(githubApiService.languageRatioForRepository("Groovy")).thenReturn(Collections.singletonMap("Groovy", 100d));

        Map<String, Double> codebaseSizeSummaryByLanguage = resumeServiceImpl.getCodebaseSizeSummaryByLanguage(repositories);
        //Java = 600 bytes (lines)
        Assertions.assertEquals(codebaseSizeSummaryByLanguage.get("Java"), 600d);
        //C = 300 bytes (lines)
        Assertions.assertEquals(codebaseSizeSummaryByLanguage.get("C"), 300d);
        //Groovy = 100 bytes (lines)
        Assertions.assertEquals(codebaseSizeSummaryByLanguage.get("Groovy"), 100d);
    }

    @Test
    void checkGetReposSortedByPopularityTest(){

        List<Repository> repositories = new ArrayList<>();
        Repository repository = new Repository();
        repository.name = "a";
        repository.stargazersCount = 3;
        repository.forksCount = 1;
        repositories.add(repository);

        repository = new Repository();
        repository.name = "b";
        repository.stargazersCount = 2;
        repository.forksCount = 3;
        repositories.add(repository);

        repository = new Repository();
        repository.name = "c";
        repository.stargazersCount = 3;
        repository.forksCount = 4;
        repositories.add(repository);

        List<Repository> sortedRepositories = resumeServiceImpl.getReposSortedByPopularity(repositories);
        //"c" should come first; Has 3 stars and 4 forks
        Assertions.assertEquals(sortedRepositories.get(0).name, "c");
        //"a" should come second; It has equals number of stars as "c", but less no. of forks
        Assertions.assertEquals(sortedRepositories.get(1).name, "a");
        //"b" should come last; Has the least no. of stars
        Assertions.assertEquals(sortedRepositories.get(2).name, "b");
    }
}