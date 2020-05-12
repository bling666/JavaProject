package com.example.backend.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_vocabulary")
public class UserVocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="word")
    private String word;

    @Column(name="uid")
    private int uid;

    @Column(name="wid")
    private int wid;

    @Column(name="joined_time")
    private Date joined_time;

    @Column(name="last_update")
    private Date last_update;

    @Column(name="stage")
    private int stage;

    @Column(name="errors")
    private int errors;

    @Column(name="recite_times")
    private int recite_times;
    
    //********
    @Column(name="frequency")
    private int frequency;
    //********
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public Date getJoined_time() {
        return joined_time;
    }

    public void setJoined_time(Date joined_time) {
        this.joined_time = joined_time;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getRecite_times() {
        return recite_times;
    }

    public void setRecite_times(int recite_times) {
        this.recite_times = recite_times;
    }
    
    //*******
    public int getFrequency() {
    	return frequency;
    }
    
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    //*********
}
