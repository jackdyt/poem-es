package com.jackdyt.poem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class poemDTO {
    private String id;
    private String name;
    private String author;
    private String type;
    private String content;
    private String href;
    private String authordes;
    private String origin;
    private categoryDTO category = new categoryDTO();
}
