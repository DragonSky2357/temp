package kuke.board.hotarticle.service.eventhandler;

import kuke.board.common.Event;
import kuke.board.common.EventType;
import kuke.board.common.payload.ArticleViewdEventPayload;
import kuke.board.hotarticle.repository.ArticleViewCountRepository;
import kuke.board.hotarticle.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleViewdEventHandler implements EventHandler<ArticleViewdEventPayload> {
    private final ArticleViewCountRepository articleViewCountRepository;

    @Override
    public void handle(Event<ArticleViewdEventPayload> event) {
        ArticleViewdEventPayload payload = event.getPayload();
        articleViewCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleViewCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<ArticleViewdEventPayload> event) {
        return EventType.ARTICLE_VIEWED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleViewdEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
