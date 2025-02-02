package kuke.board.common.payload;

import kuke.board.common.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleViewdEventPayload implements EventPayload {
    private Long articleId;
    private Long articleViewCount;
}
