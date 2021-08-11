package ml.bereket.githubresume.service;

import ml.bereket.githubresume.dto.GithubProfile;
import ml.bereket.githubresume.dto.Languages;
import ml.bereket.githubresume.dto.Repository;

import java.util.List;

public interface ResumeService {

    //TODO: comment
    List<Repository> getPopularRepositories(List<Repository> repositories);
    //TODO: comment
    Languages getLanguages();
    //TODO: comment
    GithubProfile buildProfile(String username);
}
