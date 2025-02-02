package kuke.board.hotarticle.service;

import kuke.board.common.Event;
import kuke.board.common.EventPayload;
import kuke.board.hotarticle.repository.ArticleCreatedTimeRepository;
import kuke.board.hotarticle.repository.HotArticleListRepository;
import kuke.board.hotarticle.service.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class HotArticleScoreUpdater {
    private final HotArticleListRepository hotArticleListRepository;
    private final HotArticleScoreCalculator hotArticleScoreCalculator;
    private final ArticleCreatedTimeRepository articleCreatedTimeRepository;

    private static final long HOT_ARTICLE_COUNT = 10;
    private static final Duration HOT_ARTICLE_TTL = Duration.ofDays(10);

    public void update(Event<EventPayload> event, EventHandler<EventPayload> eventHandler) {
        Long articleId = eventHandler.findArticleId(event);
        LocalDateTime createdTime = articleCreatedTimeRepository.read(articleId);

        if(!isArticleCreatedToday(createdTime)){
            return;
        }

        long score = hotArticleScoreCalculator.calculate(articleId);
    }

    private boolean isArticleCreatedToday(LocalDateTime createdTime){
        return createdTime != null && createdTime.toLocalDate().equals(LocalDateTime.now());
    }
}
