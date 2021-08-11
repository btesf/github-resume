package ml.bereket.githubresume.dto;

import lombok.Data;

import java.util.List;

@Data
public class GithubProfile {
    private User user;
    private List<Repository> popularRepositories;
    private Languages languages;
}
