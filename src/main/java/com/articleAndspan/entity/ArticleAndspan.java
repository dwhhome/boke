package com.articleAndspan.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="boke_span_article")
public class ArticleAndspan {

    //id
    @Id
    @GeneratedValue(generator = "syssuergenerator")
    @GenericGenerator(name = "syssuergenerator", strategy = "uuid")
    @Column(name = "id", unique = true, updatable = false, nullable = true, length = 128)
    private String id;

    //article_id 文章id
    @Column(name="article_id")
    private String article_id;

    //span_id 标签id
    @Column(name="span_id")
    private String span_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getSpan_id() {
        return span_id;
    }

    public void setSpan_id(String span_id) {
        this.span_id = span_id;
    }
}
