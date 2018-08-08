package com.user.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="boke_user")
public class User {
    //用户id
    @Id
    @GeneratedValue(generator = "syssuergenerator")
    @GenericGenerator(name = "syssuergenerator", strategy = "uuid")
    @Column(name = "id", unique = true, updatable = false, nullable = true, length = 128)
    private String id;

    //username  用户名
    @Column(name="username",nullable=true)
    private String username;

    //password  密码
    @Column(name="password")
    private String password;

    //ctime     创建时间
    @Column(name = "ctime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date ctime = new Date();

    //Email     邮箱
    @Column(name="email")
    private String email;

    //用户头像
    @Column(name="headimgurl")
    private String headimgurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
}

