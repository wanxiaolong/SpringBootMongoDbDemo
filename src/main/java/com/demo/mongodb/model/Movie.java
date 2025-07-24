package com.demo.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 映射到MongoDB的'movies'集合
@Document(collection = "movies")
public class Movie {

    @Id // 标注这个字段为MongoDB文档的_id字段
    private String id;

    private Integer year;
    private String title;
    private String director;
    private String genre;
    private Integer likes;
}