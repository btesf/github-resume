package ml.bereket.githubresume.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GithubProfile {
    private User user;
    private List<Repository> repositories;
    private Map<String, Double> languages;
}
