package ml.bereket.githubresume.service;

import ml.bereket.githubresume.dto.Repository;
import ml.bereket.githubresume.dto.User;

import java.util.Map;
import java.util.Optional;

public interface GithubApiService {

    //TODO: comment
    Optional<User> getUserInfo(String username);
    //TODO: comment
    Repository[] getUserRepositories(String username);
    //TODO: comment
    Map<String, Double> languageRatioForRepository(String repoUrl);
}
