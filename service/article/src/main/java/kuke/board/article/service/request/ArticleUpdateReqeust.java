package kuke.board.article.service.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleUpdateReqeust {
    private String title;
    private String content;
}
