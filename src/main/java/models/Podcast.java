package models;

import java.util.Date;

public class Podcast {
    private String name;
    private String artist;
    private String episodeNum;
    private Date date;

    public Podcast(String name, String artist, String episodeNum, String genre, Date date) {
        this.name = name;
        this.artist = artist;
        this.episodeNum = episodeNum;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getEpisodeNum() {
        return episodeNum;
    }

    public void setEpisodeNum(String episodeNum) {
        this.episodeNum = episodeNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "models.Podcast{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", episodeNum='" + episodeNum + '\'' +
                ", date=" + date +
                '}';
    }
}
