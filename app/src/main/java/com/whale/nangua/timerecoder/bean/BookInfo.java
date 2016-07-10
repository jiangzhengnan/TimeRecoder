package com.whale.nangua.timerecoder.bean;

/**
 * Created by nangua on 2016/6/28.
 */
public class BookInfo {
    private String title; //标题
    private String summary;//介绍
    private String author; //作者
    private String image; //图片url
    private String max;//最大页数

    private String catalog; //章节

    private String alt; // 豆瓣地址

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public BookInfo() {

    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", image='" + image + '\'' +
                ", max='" + max + '\'' +
                ", catalog='" + catalog + '\'' +
                '}';
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public BookInfo(String author, String summary,String title, String max, String image,String catalog) {
        this.author = author;
        this.title = title;
        this.max = max;
        this.summary = summary;
        this.image = image;
        this.catalog = catalog;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
