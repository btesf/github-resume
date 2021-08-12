package ml.bereket.githubresume.service;

import ml.bereket.githubresume.dto.Repository;
import ml.bereket.githubresume.dto.User;

import java.util.Map;
import java.util.Optional;

public interface GithubApiService {

    Optional<User> getUserInfo(String username);
    Repository[] getUserRepositories(String reposUri);
    Map<String, Double> languageRatioForRepository(String reposUri);
}
