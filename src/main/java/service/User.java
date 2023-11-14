package service;

import models.Playlist;
import models.Podcast;
import models.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private String username;
    private String password;
    private List<Playlist> allPlaylists = new ArrayList<>();

    public List<Playlist> getAllPlaylists() {
        return allPlaylists;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void createPlaylist(String name){
        allPlaylists.add(new Playlist(name));
        System.out.println("playlist created");
    }

    public void removePlaylist(String name){
        try {
            allPlaylists.remove(searchPlaylist(name));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        System.out.println("playlist removed");
    }

    public Playlist searchPlaylist(String playlistName){
       List<Playlist> searchResult = allPlaylists.stream().filter(s->s.getName().equals(playlistName)).collect(Collectors.toList());
       if (searchResult.size()!=0){
           return searchResult.get(0);
       }
       else {
           System.out.println("no playlist found");
           return null;
       }
    }

    public void showAllPlaylists(){
        if (allPlaylists.size() > 0){
            for (Playlist playlist:allPlaylists){
                System.out.println(playlist.getName());
            }
        }
        else {
            System.out.println("** No playlists created by the user **");
        }
    }

    public void addSongToPlaylist(String playlistName, Song songName){
        List<Playlist> playlists = new ArrayList<>();
        try{
            playlists = allPlaylists.stream().filter(s->s.getName().equals(playlistName)).collect(Collectors.toList());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (playlists.size()!=0){
            playlists.forEach(s->s.addSong(songName));
        }
        else {
            System.out.println("incorrect playlist name or incorrect song name");
        }
    }

    public void removeSongFromPlaylist(String playlistName, Song songName){
        List<Playlist> playlists = new ArrayList<>();
        try{
            playlists = allPlaylists.stream().filter(s->s.getName().equals(playlistName)).collect(Collectors.toList());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (playlists.size()!=0){
            playlists.forEach(s->s.removeSong(songName));
        }
        else {
            System.out.println("incorrect playlist name or incorrect song name");
        }
    }

    public void showSongsInPlaylist(String playlistName){
        allPlaylists.stream().filter(s->s.getName().equals(playlistName)).forEach(Playlist::showAllSongs);
    }

    public void addPodcastToPlaylist(String playlistName, Podcast podcastName){
        allPlaylists.stream().filter(s->s.getName().equals(playlistName)).forEach(s->s.addPodcast(podcastName));
    }

    public void showPodcastsInPlaylist(String playlistName){
        allPlaylists.stream().filter(s->s.getName().equals(playlistName)).forEach(Playlist::showAllPodcasts);
    }
//    includes both songList and podcastList
    public void showFullPlaylist(String playlistName){
        allPlaylists.stream().filter(s->s.getName().equals(playlistName)).forEach(Playlist::showFullPlaylist);
    }

    @Override
    public String toString() {
        return "service.User{" +
                "username='" + username + '}';
    }
}
