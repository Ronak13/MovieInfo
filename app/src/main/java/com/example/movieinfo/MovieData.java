package com.example.movieinfo;

public class MovieData {

    String poster;
    String title;

    public MovieData(String poster, String title) {
        this.poster = poster;
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
