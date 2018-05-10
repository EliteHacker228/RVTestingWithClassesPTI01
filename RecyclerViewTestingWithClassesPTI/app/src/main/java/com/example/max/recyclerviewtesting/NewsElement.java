package com.example.max.recyclerviewtesting;

public class NewsElement {
    String sourceYear;
    String newsText;
    String newsDate;
    String newsTime;
    String newsPicURL;
    String newsLink;

    public NewsElement(String newsText, String newsDate, String newsTime, String newsPicURL, String newsLink, String sourceYear) {
        this.newsText = newsText;
        this.newsDate = newsDate;
        this.newsTime = newsTime;
        this.newsPicURL = newsPicURL;
        this.newsLink = newsLink;
        this.sourceYear=sourceYear;
    }

    public String getSourceYear() {
        return sourceYear;
    }

    public void setSourceYear(String sourceYear) {
        this.sourceYear = sourceYear;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsPicURL() {
        return newsPicURL;
    }

    public void setNewsPicURL(String newsPicURL) {
        this.newsPicURL = newsPicURL;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }
}
