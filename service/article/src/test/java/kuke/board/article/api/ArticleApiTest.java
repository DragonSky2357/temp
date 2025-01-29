package kuke.board.article.api;

import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void readAllTest(){
        ArticlePageResponse response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=1")
                .retrieve()
                .body(ArticlePageResponse.class);

        log.info("response.getArticleCount() = {}", response.getArticleCount());
        for(ArticleResponse article : response.getArticles()){
            log.info("articleId = {}", article.getArticleId());
        }
    }

    @Test
    void readAllInfiniteScrollTest(){
        List<ArticleResponse> articles1 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });

        log.info("firstPage");
        for(ArticleResponse articleResponse : articles1){
            log.info("articleResponse.getArticleId() = {}", articleResponse.getArticleId());
        }

        Long lastArticleId = articles1.getLast().getArticleId();
        List<ArticleResponse> articles2 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5&lastArticleId=%s".formatted(lastArticleId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });

        log.info("secondPage");
        for(ArticleResponse articleResponse : articles2){
            log.info("articleResponse.getArticleId() = {}", articleResponse.getArticleId());
        }
    }
}
