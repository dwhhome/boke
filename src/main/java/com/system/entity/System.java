package com.system.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="boke_system")
public class System {

    //id
    @Id
    @GeneratedValue(generator = "syssuergenerator")
    @GenericGenerator(name = "syssuergenerator", strategy = "uuid")
    @Column(name = "id", unique = true, updatable = false, nullable = true, length = 128)
    private String id;

   // logoImg     网站logo。存储logo
   @Column(name="logoImg")
   private String logoImg;

   // logoUrl     网站链接
   @Column(name="logoUrl")
   private String logoUrl;

   //signature   网站签名
   @Column(name="signature")
   private String signature;

   //webcoding   网站编码
   @Column(name="webcoding")
   private String webcoding;

   // total       总浏览量
   @Column(name="total",columnDefinition="int default 0")
   private int total;

   // ctime       创建时间（即修改时间）
   @Column(name = "ctime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
   private Date ctime = new Date();

   //status      网站使用状态（-1：停用中   1：使用中  网站配置默认使用最新的一条配置）
   @Column(name="status",columnDefinition="int default -1")
   private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getWebcoding() {
        return webcoding;
    }

    public void setWebcoding(String webcoding) {
        this.webcoding = webcoding;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
