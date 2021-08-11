package ml.bereket.githubresume.service;

import ml.bereket.githubresume.dto.Repository;
import ml.bereket.githubresume.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class GithubApiServiceIntegrationTest {

    @Value("${github.user.info.endpoint}")
    private String usersEndpointUrl;

    @Autowired
    private GithubApiService githubApiService;

    @Test
    public void checkGetUserInfoTest(){

        Optional<User> user = githubApiService.getUserInfo("mxcl");
        Assertions.assertTrue(user.isPresent());
        System.out.println(user.get().toString());
    }

    @Test
    public void checkUserReposTest(){

        final String reposUrl = "https://api.github.com/users/mxcl/repos";
        Repository[] repositories = githubApiService.getUserRepositories(reposUrl);
        Assertions.assertTrue(repositories.length > 0);
        System.out.println(Arrays.toString(repositories));
    }
}
