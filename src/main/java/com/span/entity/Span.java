package com.span.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="boke_span")
public class Span {

    //id
    @Id
    @GeneratedValue(generator = "syssuergenerator")
    @GenericGenerator(name = "syssuergenerator", strategy = "uuid")
    @Column(name = "id", unique = true, updatable = false, nullable = true, length = 128)
    private String id;

    //name    标签名（Java，PHP，等）
    @Column(name="name")
    private String name;

    //ctime   创建时间
    @Column(name = "ctime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date ctime = new Date();

    //status状态
    @Column(name="status",columnDefinition="int default -1")
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
