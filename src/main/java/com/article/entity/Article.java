package com.article.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="boke_article")
public class Article {
    //id
    @Id
    @GeneratedValue(generator = "syssuergenerator")
    @GenericGenerator(name = "syssuergenerator", strategy = "uuid")
    @Column(name = "id", unique = true, updatable = false, nullable = true, length = 128)
    private String id;

    //title    标题
    @Column(name="title")
    private String title;

    //context  内容（使用富文本fck编辑器）
    @Type(type="text")
    @Column(name="context")
    private String context;

    //coverImg 封面图片
    @Column(name="coverImg")
    private String coverImg;

    //lableId  分类id
    @Column(name="lableId")
    private String lableId;

    //status   状态（只做逻辑删除：-1 删除  1：正常）
    @Column(name="status",columnDefinition="int default -1")
    private int status;

    //ctime    创建时间（即修改时间）
    @Column(name = "ctime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date ctime = new Date();

    //hottest  点赞次数（点赞前三的博客优先显示）
    @Column(name="hottest",columnDefinition="int default 0")
    private int hottest;

    //tread    被踩次数
    @Column(name="tread",columnDefinition="int default 0")
    private int tread;

    //total    游览量
    @Column(name="total",columnDefinition="int default 0")
    private int total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getLableId() {
        return lableId;
    }

    public void setLableId(String lableId) {
        this.lableId = lableId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public int getHottest() {
        return hottest;
    }

    public void setHottest(int hottest) {
        this.hottest = hottest;
    }

    public int getTread() {
        return tread;
    }

    public void setTread(int tread) {
        this.tread = tread;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
