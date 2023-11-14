package models;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    List<Song> songList = new ArrayList<>();
    List<Podcast> podcastList = new ArrayList<>();

    public List<Song> getSongList() {
        return songList;
    }

    public List<Podcast> getPodcastList() {
        return podcastList;
    }

    public Playlist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSong(Song song){
        songList.add(song);
    }

    public void removeSong(Song song){
        songList.remove(song);
    }

    public void addPodcast(Podcast podcast){
        podcastList.add(podcast);
    }

    public void removePodcast(Podcast podcast){
        podcastList.remove(podcast);
    }

    public void showAllSongs(){
        System.out.println(name);
        songList.forEach(System.out::println);
    }

    public void showAllPodcasts(){
        System.out.println(name);
        podcastList.forEach(System.out::println);
    }

    public void showFullPlaylist(){
        System.out.println(name);
        songList.forEach(System.out::println);
        podcastList.forEach(System.out::println);
    }
}
