package ml.bereket.githubresume.service;

import ml.bereket.githubresume.dto.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GithubApiServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    private GithubApiServiceImpl githubApiServiceImpl;

    @BeforeEach
    public void setUp(){
        githubApiServiceImpl = new GithubApiServiceImpl(restTemplate);
    }

    @Test
    void checkGetUserRepositoriesTest(){

        Repository[] repositories = new Repository[2];
        Repository repository = new Repository();
        repository.name = "a";
        repository.fork = false;
        repositories[0] = repository;

        repository = new Repository();
        repository.name = "b";
        repository.fork = true;
        repositories[1] = repository;

        ResponseEntity<Repository[]> response = ResponseEntity.accepted().body(repositories);
        Mockito.when(restTemplate.getForEntity("url?per_page=100", Repository[].class)).thenReturn(response);
        List<Repository> sortedRepositories = githubApiServiceImpl.getUserRepositories("url");
        //only non-forked repository "a" should be returned
        Assertions.assertEquals(sortedRepositories.size(), 1);
        Assertions.assertEquals(sortedRepositories.get(0).name, "a");
    }
}
