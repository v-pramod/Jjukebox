import models.Jukebox;
import models.Playlist;
import models.Song;
import service.Catalog;
import service.Player;
import service.User;

import java.util.Scanner;

public class app {

    public static void main(String[] args){
        User user = null;
        Catalog catalog = new Catalog();
        Player player = new Player();
        Jukebox jukebox = new Jukebox();

        Scanner sc = new Scanner(System.in);

        String input;
        String username;
        String password;

        boolean jukeboxActive = true;
        boolean inMainMenu;
        boolean userLogIn = false;

//        pre defined user
        jukebox.createUser("admin","1234");

//        pre added songs
        Song drive = new Song("Drive Breakbeat","Rockot","Copyright Free Music","EDM");
        drive.setFilePath("/home/pramod/source/java/jukebox/src/main/resources/drive-breakbeat.wav");
        Song titanium = new Song("Titanium","Alisha Studo","Copyright Free Music","IDM");
        titanium.setFilePath("/home/pramod/source/java/jukebox/src/main/resources/titanium.wav");

        catalog.addSongToCatalog(drive);
        catalog.addSongToCatalog(titanium);

        //main
        while(jukeboxActive){
            System.out.println();
            System.out.println("-----------\n  Jukebox  \n-----------");
            catalog.showSongCatalog();
            System.out.println("To create user enter 'signup'\n" +
                    "To sign in to the jukebox enter 'login'\n" +
                    "To play a song enter 'play'\n" +
                    "To sort the catalog enter 'sort'\n" +
                    "To search the catalog enter 'search'\n" +
                    "To view your playlists enter 'playlist'");
            input = sc.nextLine();

            switch (input) {
                // create new user
                case "signup":
                    System.out.print("Enter username: ");
                    username = sc.nextLine();
                    System.out.print("Enter password: ");
                    password = sc.nextLine();
                    jukebox.createUser(username,password);
                    break;

                // login existing user
                case "login":
                    System.out.print("Enter username: ");
                    username = sc.nextLine();
                    System.out.print("Enter password: ");
                    password = sc.nextLine();
                    user = jukebox.verifyUser(username,password);
                    if (jukebox.isUserLogIn()){
                        userLogIn = true;
                    }
                    break;

                // play a song
                case "play":
                    if (userLogIn){
                        System.out.println("enter song name");
                        Song song = catalog.searchSong(sc.nextLine());
                        player.playSong(song);
                    }
                    // error handling for user not logged in
                    else {
                        System.out.println("user not logged in. Please log in");
                    }
                    break;

                // sort songs alphabetically based on categories - name, artist, album, genre
                case "sort":
                    System.out.println("enter category");
                    catalog.showSortedSongCatalog(sc.nextLine());
                    System.out.println("To play song enter 'play'\n" +
                            "To add song to playlist enter 'add song to playlist'\n" +
                            "hit enter to go back");
                    input = sc.nextLine();
                    switch (input){
                        // play a song inside sorted songs list
                        case "play":
                            if (userLogIn){
                                System.out.println("enter song name");
                                Song song = catalog.searchSong(sc.nextLine());
                                player.playSong(song);
                            }
                            // error handling for user not logged in
                            else {
                                System.out.println("user not logged in. Please log in");
                            }
                            break;

                        // add song to playlist inside sorted songs list
                        case "add song to playlist":
                            if (userLogIn){
                                System.out.print("enter song name: ");
                                Song song = catalog.searchSong(sc.nextLine());
                                System.out.print("Enter playlist name: ");
                                String playlistName = sc.nextLine();
                                Playlist playlist = user.searchPlaylist(playlistName);
                                if (playlist != null){
                                    playlist.addSong(song);
                                }
                                // create a new playlist if playlist is not found
                                else{
                                    System.out.println("playlist not found. created playlist and added the song");
                                    user.createPlaylist(playlistName);
                                    user.addSongToPlaylist(playlistName,song);
                                }
                            }
                            // error handling for user not logged in
                            else {
                                System.out.println("user not logged in. Please log in");
                            }
                            break;
                    }
                    break;

                // search for a song based on name / artist / album / genre
                case "search":
                    System.out.print("enter category: ");
                    String category = sc.nextLine();
                    System.out.print("enter search keyword: ");
                    String keyword = sc.nextLine();
                    catalog.showSearchResultsSongCatalog(category,keyword);
                    System.out.println("To play song enter 'play'\n" +
                            "To add song to playlist enter 'add song to playlist'\n" +
                            "hit enter to go back");
                    input = sc.nextLine();
                    switch (input){
                        // play the searched song
                        case "play":
                            if (userLogIn){
                                System.out.println("enter song name");
                                Song song = catalog.searchSong(sc.nextLine());
                                player.playSong(song);
                            }
                            else {
                                System.out.println("user not logged in. Please log in");
                            }
                            break;
                        // add the searched song to a playlist
                        case "add song to playlist":
                            if (userLogIn){
                                System.out.print("enter song name: ");
                                Song song = catalog.searchSong(sc.nextLine());
                                System.out.print("Enter playlist name: ");
                                String playlistName = sc.nextLine();
                                Playlist playlist = user.searchPlaylist(playlistName);
                                if (playlist != null){
                                    playlist.addSong(song);
                                }
                                // create a new playlist if playlist not found and add the song
                                else{
                                    System.out.print("playlist not found. ");
                                    user.createPlaylist(playlistName);
                                    System.out.println("added the song");
                                    user.addSongToPlaylist(playlistName,song);
                                }
                            }
                            // error handling for user not logged in
                            else {
                                System.out.println("user not logged in. Please log in");
                            }
                            break;
                    }
                    break;

                //view playlists of the user
                case "playlist": inMainMenu = false;
                    // error handling for user not logged in
                    if (!userLogIn){
                        System.out.println("user not logged in. Please log in");
                    }
                    else {
                        while (!inMainMenu){
                            System.out.println("----------\n"+user.getUsername()+"'s playlists"+"\n----------");
                            user.showAllPlaylists();
                            System.out.println("'create' - create new playlist\n" +
                                    "'select' - select a playlist\n" +
                                    "'play' - play the playlist\n" +
                                    "'delete' - delete the playlist\n" +
                                    "'back' - go back to main menu");
                            input = sc.nextLine();
                            switch (input){
                                // create new playlist
                                case "create":
                                    System.out.print("Enter playlist name: ");
                                    input = sc.nextLine();
                                    user.createPlaylist(input);
                                    break;
                                // select a playlist to play songs or add new songs into it
                                case "select":
                                    System.out.print("Enter playlist name: ");
                                    String playlistName = sc.nextLine();
                                    if (user.searchPlaylist(playlistName) != null){
                                        user.showSongsInPlaylist(playlistName);
                                        System.out.println("'play' - play a song" +
                                                "'add' - add song from catalog" +
                                                "'remove' - remove song from playlist");
                                        input = sc.nextLine();
                                        switch (input){
                                            // play a song from playlist
                                            case "play":
                                                System.out.println("enter song name");
                                                Song song = catalog.searchSong(sc.nextLine());
                                                player.playSong(song);
                                                break;
                                            // add a song to the playlist
                                            case "add":
                                                System.out.println("enter song name");
                                                song = catalog.searchSong(sc.nextLine());
                                                user.addSongToPlaylist(playlistName,song);
                                                break;
                                            // remove a song from the playlist
                                            case "remove":
                                                System.out.println("enter song name");
                                                song = catalog.searchSong(sc.nextLine());
                                                user.removeSongFromPlaylist(playlistName,song);
                                                break;
                                            case "back": break;
                                        }
                                    }
                                    break;
                                // play all songs from a playlist
                                case "play":
                                    System.out.println("select a playlist to play");
                                    player.playPlaylist(user.searchPlaylist(sc.nextLine()));
                                    break;
                                // delete a playlist
                                case "remove":
                                    System.out.print("Enter playlist name: ");
                                    input = sc.nextLine();
                                    user.removePlaylist(input);
                                    break;
                                // go back to main menu
                                case "back": inMainMenu = true;
                                break;
                            }
                        }
                    }
                    break;

                // exit jukebox
                case "exit": jukeboxActive = false;
                    System.out.println("closing jukebox");
                    break;

                // error handling incorrect user input
                default:
                    System.out.println("Wrong input. Please try again");
            }
        }
    }
}
