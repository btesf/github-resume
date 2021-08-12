package ml.bereket.githubresume.service;

import ml.bereket.githubresume.dto.GithubProfile;
import ml.bereket.githubresume.dto.Repository;
import ml.bereket.githubresume.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ResumeServiceIntegrationTest {

    @Autowired
    ResumeService resumeService;

    @Test
    public void checkBuildProfileTest(){

        GithubProfile githubProfile = resumeService.buildProfile("mxcl");
        Assertions.assertNotNull(githubProfile.getUser());

        //User profile details
        User user = githubProfile.getUser();
        Assertions.assertTrue(user.name.equalsIgnoreCase("MAX HOWELL"));
        Assertions.assertEquals(user.blog, "https://mxcl.dev");

        //languages ratio
        Map<String, Double> languageRatio = githubProfile.getLanguages();
        Assertions.assertTrue(languageRatio.keySet().containsAll(Arrays.asList("Objective-C", "Swift", "TypeScript", "Swift")));

        //popular repositories
        List<Repository> repositories = githubProfile.getRepositories();
        Assertions.assertEquals(repositories.get(0).name, "PromiseKit");//1. the most popular one
        Assertions.assertEquals(repositories.get(1).name, "swift-sh");//2nd most popular one
    }
}
