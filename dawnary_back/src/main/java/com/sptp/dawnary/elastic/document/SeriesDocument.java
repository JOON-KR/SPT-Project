package com.sptp.dawnary.elastic.document;

import com.sptp.dawnary.series.domain.Series;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "series", createIndex = true)
public class SeriesDocument {

    @Id
    private Long id;
    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ngram"),
            otherFields = {
                @InnerField(suffix = "exact", type = FieldType.Keyword),
                @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete_analyzer")
            })
    private String memberName;
    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ngram"),
            otherFields = {
                @InnerField(suffix = "exact", type = FieldType.Keyword),
                @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete_analyzer")
            })
    private String title;
    private int status;
    private String imagePath;
    private long viewCnt;
    @Field(type = FieldType.Date)
    private LocalDateTime regDate;
    // 자동 완성을 위한 필드

    public static SeriesDocument from(Series series) {
    	System.out.println(series.getMember().toString());
        return SeriesDocument.builder()
                .memberName(series.getMember().getName())
                .title(series.getTitle())
                .status(series.getStatus())
                .imagePath(series.getImagePath())
                .viewCnt(series.getViewCnt())
                .regDate(series.getRegDate())
                .build();
    }
}
