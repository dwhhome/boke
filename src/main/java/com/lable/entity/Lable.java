package com.lable.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="boke_lable")
public class Lable {

    //id
    @Id
    @GeneratedValue(generator = "syssuergenerator")
    @GenericGenerator(name = "syssuergenerator", strategy = "uuid")
    @Column(name = "id", unique = true, updatable = false, nullable = true, length = 128)
    private String id;

    // name     名字（默认分类，音乐，其他，心情）
    @Column(name="name")
    private String name;

    // iocImg   图标
    @Column(name="iocImg")
    private String iocImg;

    //ctime    创建时间
    @Column(name = "ctime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date ctime = new Date();

    //状态（默认停用 -1  使用 1）
    @Column(name="status",columnDefinition="int default -1")
    private int status;


}
