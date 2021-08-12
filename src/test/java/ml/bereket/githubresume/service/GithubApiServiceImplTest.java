package ml.bereket.githubresume.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

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
    void checkGetUserInfoTest(){

    }
}
