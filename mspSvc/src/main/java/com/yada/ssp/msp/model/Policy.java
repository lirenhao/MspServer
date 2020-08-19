package com.yada.ssp.msp.model;

import javax.persistence.*;

@Entity
@Table(name = "T_WEB_MER_POLICY")
public class Policy {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "UPDATE_TIME")
    private String updateTime;

    @Column(name = "STATUS")
    private String status;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
