package com.jackdyt.poem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "poems")
public class poemDTO {
    @Id
    private String id;

    @Field(analyzer = "ik_max_word", type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(analyzer = "ik_smart", type = FieldType.Text)
    private String content;

    private String href;

    @Field(analyzer = "ik_smart", type = FieldType.Text)
    private String authordes;

    @Field(type = FieldType.Keyword)
    private String origin;

    @Field(type = FieldType.Nested)
    private categoryDTO category = new categoryDTO();
}
