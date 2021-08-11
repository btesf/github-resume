package ml.bereket.githubresume.service;

import ml.bereket.githubresume.dto.GithubProfile;
import ml.bereket.githubresume.dto.Languages;
import ml.bereket.githubresume.dto.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Override
    public List<Repository> getPopularRepositories(List<Repository> repositories) {
        return null;
    }

    @Override
    public Languages getLanguages() {
        return null;
    }

    @Override
    public GithubProfile buildProfile(String username) {
        return null;
    }
}
