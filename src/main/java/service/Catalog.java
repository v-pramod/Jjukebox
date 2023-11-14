package service;

import models.Podcast;
import models.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Catalog {
    private final List<Song> songCatalog = new ArrayList<>();
    private final List<Podcast> podcastCatalog = new ArrayList<>();

    // song catalog

    public void addSongToCatalog(Song song){
        songCatalog.add(song);
    }

    public void showSongCatalog(){
        System.out.println("Song Catalog");
        songCatalog.forEach(System.out::println);
        System.out.println();
    }

    public List<Song> sortSongCatalog(String category){
        switch (category.toLowerCase()){
            case "artist": return songCatalog.stream().sorted((Comparator.comparing(Song::getArtist))).collect(Collectors.toList());
            case "genre": return songCatalog.stream().sorted((Comparator.comparing(Song::getGenre))).collect(Collectors.toList());
            case "album": return songCatalog.stream().sorted((Comparator.comparing(Song::getAlbum))).collect(Collectors.toList());
            default: System.out.println("category not available. please try again");
            return null;
        }
    }

    public void showSortedSongCatalog(String category){
        List<Song> sortedSongsList = new ArrayList<>();
        try {
            sortedSongsList = sortSongCatalog(category);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        if (sortedSongsList.size() != 0){
            sortedSongsList.forEach(System.out::println);
        }
    }

    public List<Song> searchSortedSongCatalog(String category, String searchKeyword){
        switch (category.toLowerCase()){
            case "artist": return sortSongCatalog(category).stream().filter(s->s.getArtist().equals(searchKeyword)).collect(Collectors.toList());
            case "genre": return sortSongCatalog(category).stream().filter(s->s.getGenre().equals(searchKeyword)).collect(Collectors.toList());
            case "album": return sortSongCatalog(category).stream().filter(s->s.getAlbum().equals(searchKeyword)).collect(Collectors.toList());
            default:
                System.out.println("no results found. please try again");
                return null;
        }
    }

    public void showSearchResultsSongCatalog(String category, String searchKeyword){
        List<Song> searchResultsList = new ArrayList<>();
        try {
            searchResultsList = searchSortedSongCatalog(category,searchKeyword);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        if (searchResultsList.size() != 0){
            searchResultsList.forEach(System.out::println);
        }
        else {
            System.out.println("no search results");
        }
    }

    public Song searchSong(String songName){
       List<Song> searchSongList= songCatalog.stream().filter(s->s.getName().equals(songName)).collect(Collectors.toList());
       if (searchSongList.size() ==0){
           return null;
       }
       else {
           return searchSongList.get(0);
       }
    }

    //podcast catalog

    public void addPodcastToCatalog(Podcast podcast){
        podcastCatalog.add(podcast);
    }

    public List<Podcast> sortPodcastCatalog(String category){
        switch (category.toLowerCase()){
            case "artist": return podcastCatalog.stream().sorted((Comparator.comparing(Podcast::getArtist))).collect(Collectors.toList());
            case "date": return podcastCatalog.stream().sorted((Comparator.comparing(Podcast::getDate))).collect(Collectors.toList());
            default: System.out.println("category not available. please try again");
                return podcastCatalog;
        }
    }

    public List<Podcast> searchPodcastCatalog(String category, String searchKeyword){
//        List<Podcast> sortedPodcastCatalog = sortPodcastCatalog(category);
        if (category.toLowerCase().equals("artist")){
            return podcastCatalog.stream().filter(s->s.getArtist().equals(searchKeyword)).collect(Collectors.toList());
        }
        else {
            System.out.println("category not available. please try again");
            return null;
        }
    }

    public void showPodcastCatalog(){
        podcastCatalog.forEach(System.out::println);
    }

    public void showPodcastCatalog(String category){
        sortPodcastCatalog(category).forEach(System.out::println);
    }
}
