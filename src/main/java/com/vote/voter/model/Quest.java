package com.vote.voter.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "t_quest")
public class Quest {

    private Quest() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(name = "valid_sec")
    private Integer validSec;

    @Column(name = "create_time")
    private LocalDate createTime;

    @Column(name = "update_time")
    private LocalDate updateTime;

    public Quest(Long id, Integer validSec, LocalDate create_time, LocalDate updateTime) {
        this.id = id;
        this.validSec = validSec;
        this.createTime = create_time;
        this.updateTime = updateTime;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the validSec
     */
    public Integer getValidSec() {
        return validSec;
    }

    /**
     * @param validSec the validSec to set
     */
    public void setValidSec(Integer validSec) {
        this.validSec = validSec;
    }

    /**
     * @return the createTime
     */
    public LocalDate getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public LocalDate getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

}
