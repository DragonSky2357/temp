package kuke.board.hotarticle.service;

import kuke.board.common.Event;
import kuke.board.common.EventPayload;
import kuke.board.hotarticle.client.ArticleClient;
import kuke.board.hotarticle.repository.HotArticleListRepository;
import kuke.board.hotarticle.service.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotArticleService {
    private final ArticleClient articleClient;
    private final List<EventHandler> eventHandlers;
    private final HotArticleScoreUpdater hotArticleScoreUpdater;
    private final HotArticleListRepository hotArticleListRepository;

    public void handleEvent(Event<EventPayload> event){
        EventHandler<EventPayload> eventHandler = findEventHandler(event);
        if(eventHandler == null){
            return;
        }

        if(isArticleCreatedOrDeleted(event)){
            eventHandler.handle(event);
        }else{
            hotArticleScoreUpdater.update(event, eventHandler);
        }
    }
}
