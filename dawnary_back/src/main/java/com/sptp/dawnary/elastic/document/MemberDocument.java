package com.sptp.dawnary.elastic.document;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import com.sptp.dawnary.member.domain.Member;

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
@Document(indexName = "members", createIndex = true)
public class MemberDocument {

    @Id
    private Long id;
    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "edge_ngram_analyzer"),
            otherFields = {
                @InnerField(suffix = "exact", type = FieldType.Keyword),
                @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete_analyzer")
            })
    private String name;
    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "edge_ngram_analyzer"),
            otherFields = {
                @InnerField(suffix = "exact", type = FieldType.Keyword),
                @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete_analyzer")
            })
    private String email;
    private String imagePath;
    public static MemberDocument from(Member member) {
        return MemberDocument.builder()
                .name(member.getName())
                .email(member.getEmail())
                .imagePath(member.getImagePath())
                .build();
    }
}
