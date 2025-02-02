package kuke.board.hotarticle.service.eventhandler;

import kuke.board.common.Event;
import kuke.board.common.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean supports(Event<T> event);
    Long findArticleId(Event<T> event);
}
