package com.discuss.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="boke_discuss")
public class Discuss {

    //id
    @Id
    @GeneratedValue(generator = "syssuergenerator")
    @GenericGenerator(name = "syssuergenerator", strategy = "uuid")
    @Column(name = "id", unique = true, updatable = false, nullable = true, length = 128)
    private String id;

    //article_id    文章id
    @Column(name="article_id")
    private String article_id;

    //content       文章内容
    @Column(name="content")
    private String content;

    //userName      匿名评论名字
    @Column(name="userName")
    private String userName;

    //userEmail     邮箱
    @Column(name="userEmail")
    private String userEmail;

    //userUrl       链接
    @Column(name="userUrl")
    private String userUrl;

    //ctime         创建时间
    @Column(name = "ctime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date ctime = new Date();

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
