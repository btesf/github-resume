package ml.bereket.githubresume.service;

import ml.bereket.githubresume.dto.GithubProfile;
import java.util.Map;

public interface ResumeService {

    GithubProfile buildProfile(String username);
    Map<String, Double> calculateLanguagesRatio(Map<String, Double> totalLanguageOccurrence);
}
