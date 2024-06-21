package com.sptp.dawnary.elastic.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import com.sptp.dawnary.series.domain.Series;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "series", createIndex = true)
public class SeriesDocument {

    @Id
    private long id;
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
