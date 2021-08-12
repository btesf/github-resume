package ml.bereket.githubresume.service;

import lombok.RequiredArgsConstructor;
import ml.bereket.githubresume.dto.Repository;
import ml.bereket.githubresume.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RequiredArgsConstructor
@Service
public class GithubApiServiceImpl implements GithubApiService {

    private static final Logger logger = LoggerFactory.getLogger(GithubApiServiceImpl.class);

    final String USERNAME_PATH_PARAM = "USER_NAME";

    @Value("${github.user.info.endpoint}")
    private String usersEndpointUrl;

    private final RestTemplate restTemplate;

    @Override
    public Optional<User> getUserInfo(String username) {

        assert(StringUtils.hasText(username));
        try{

            Map<String, String> pathParams = new HashMap<>();
            pathParams.put(USERNAME_PATH_PARAM, username);

            URI uri = UriComponentsBuilder.fromUriString(usersEndpointUrl)
                    .buildAndExpand(pathParams)
                    .toUri();

            ResponseEntity<User> response = restTemplate.getForEntity(
                    uri,
                    User.class);
            return Optional.ofNullable(response.getBody());

        } catch (HttpClientErrorException httpClientException){
            logger.error(httpClientException.getMessage(), httpClientException);
            throw httpClientException;
        } catch (Exception exception){
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Repository[] getUserRepositories(final String reposUri) {

        assert(StringUtils.hasText(reposUri));
        try{

            final String searchUri = UriComponentsBuilder.fromUriString(reposUri)
                    .queryParam("per_page", "100")
                    .toUriString();

            ResponseEntity<Repository[]> response = restTemplate.getForEntity(
                    searchUri,
                    Repository[].class);
            return response.getBody();

        } catch (HttpClientErrorException httpClientException) {
            logger.error(httpClientException.getMessage(), httpClientException);
            throw httpClientException;
        } catch (Exception exception){
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Map<String, Double> languageRatioForRepository(final String languagesForRepoUrl) {

        assert(StringUtils.hasText(languagesForRepoUrl));
        try{
            ParameterizedTypeReference<Map<String, Double>> responseType =
                    new ParameterizedTypeReference<>() {};

            ResponseEntity<Map<String, Double>> responseEntity =
                    restTemplate.exchange(languagesForRepoUrl, HttpMethod.GET, null, responseType);
            return responseEntity.getBody();

        } catch (HttpClientErrorException httpClientException) {
            logger.error(httpClientException.getMessage(), httpClientException);
            throw httpClientException;
        } catch (Exception exception){
            logger.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }
}
