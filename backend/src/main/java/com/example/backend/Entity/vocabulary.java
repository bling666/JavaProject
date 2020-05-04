package com.example.backend.Entity;

import javax.persistence.*;

@Entity
@Table(name="vocabulary")
public class vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="word")
    private String word;

    @Column(name = "sw")
    private String sw;

    @Column(name="phonetic")
    private String phonetic;

    @Column(name="definition")
    private String definition;

    @Column(name="pos")
    private String pos;

    @Column(name="collins")
    private boolean collins;

    @Column(name="oxford")
    private boolean oxford;

    @Column(name = "tag")
    private String tag;

    @Column(name="bnc")
    private Integer bnc;

    @Column(name="frq")
    private Integer frq;

    @Column(name = "exchange")
    private String exchange;

    @Column(name ="detail")
    private String detail;

    @Column(name="audio")
    private String audio;

    public vocabulary(){}

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

    public String getSw() {
        return sw;
    }

    public void setSw(String sw) {
        this.sw = sw;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public boolean isCollins() {
        return collins;
    }

    public void setCollins(boolean collins) {
        this.collins = collins;
    }

    public boolean isOxford() {
        return oxford;
    }

    public void setOxford(boolean oxford) {
        this.oxford = oxford;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getBnc() {
        return bnc;
    }

    public void setBnc(Integer bnc) {
        this.bnc = bnc;
    }

    public Integer getFrq() {
        return frq;
    }

    public void setFrq(Integer frq) {
        this.frq = frq;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
