package service;
import models.Playlist;
import models.Song;

import javax.sound.sampled.*;
import java.io.File;
import java.util.*;

public class Player implements Iplayer {
    AudioInputStream audioStream;
    Clip clip;
    Scanner sc = new Scanner(System.in);
    String response = "init";
    boolean playerActive;
    boolean shuffleActive;
    int shuffleIndex=1;
    Random random = new Random();
    List <Song> songPlaylist = new ArrayList<>();
    List<Integer> songsPlayedIndex = new ArrayList<>();

//    public int getRandomUniqueNumber(){
//        int randomNum = random.nextInt(songPlaylist.size()-1);
//        boolean uniqueVerify = songsPlayedIndex.stream().anyMatch(s->s==randomNum);
//        if (!uniqueVerify){
//            songsPlayedIndex.add(randomNum);
//        }
//        else {
//            getRandomUniqueNumber();
//        }
//        return randomNum;
//    }

    public void playSong(Song song) {
        if (song != null){
            playerActive = true;
            try {
                audioStream = AudioSystem.getAudioInputStream(new File(song.getFilePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                clip = AudioSystem.getClip();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                assert clip != null;
                clip.open(audioStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            clip.start();
            long songDuration = clip.getMicrosecondLength() / 1000000;

            System.out.println("---------------");
            System.out.println("Playing " +song.getName()+" from "+song.getAlbum()+" album\n" +
                    "Song Duration: "+songDuration/60+":"+songDuration%60+" mins");
            System.out.println("Player options: play, pause, fastforward, rewind, restart, loop, exit");
            System.out.println("---------------");

            while (playerActive) {
                response = sc.next();
                response = response.toLowerCase();
                long timeRemaining = songDuration - clip.getMicrosecondPosition() / 1000000;

                switch (response) {
                    case ("play"): play();
                        break;
                    case ("pause"): pause();
                        System.out.println("Time remaining: "+timeRemaining/60+":"+timeRemaining%60+" mins");
                        break;
                    case ("next"): next();
                        break;
                    case ("previous"): previous();
                        break;
                    case ("rewind"): rewind();
                        break;
                    case ("fastforward"): fastForward();
                        break;
                    case ("restart"): restart();
                        break;
                    case ("loop"): loop();
                        break;
                    case ("shuffle"): shuffle();
                        break;
                    case ("exit"): exit();
                        break;
                    default:
                        System.out.println("invalid response\n " + "Player options: play, pause, fastforward, rewind, restart, loop, shuffle, exit");
                }
            }
        }
        else {
            System.out.println("no song found");
        }
    }

    public void playPlaylist(Playlist playlist){
        if (playlist != null){
            playerActive = true;
            songPlaylist = playlist.getSongList();
            ListIterator<Song> i = songPlaylist.listIterator();
            songsPlayedIndex.add(i.nextIndex());
            Song song = i.next();
            playSong(song);
            while (response.equals("next") || response.equals("previous")){
                if (response.equals("next") && i.hasNext()){
                    if (shuffleActive){
                        int randomIndex = random.nextInt(songPlaylist.size()-1);
                        i.set(songPlaylist.get(randomIndex));
                        playSong(songPlaylist.get(randomIndex));
                    }
                    else {
                        playSong(i.next());
                    }
                    continue;
                }
                if (response.equals("previous")&& i.hasPrevious()){
                    playSong(i.previous());
                }
            }
        }
    }

    //previous() doesn't work in this function
    public void playPlaylistFW(List<Playlist> playlist){
        playlist.get(0).getSongList().forEach(this::playSong);
    }

    @Override
    public void play() {
        clip.start();
        System.out.println("Player resumed");
    }

    @Override
    public void pause() {
        clip.stop();
        System.out.println("player paused");
    }

    @Override
    public void next() {
        clip.stop();
        clip.close();
        playerActive = false;
        response = "next";
    }

    @Override
    public void previous() {
        clip.stop();
        clip.close();
        playerActive = false;
        response = "previous";
    }

    @Override
    public void restart() {
        clip.setMicrosecondPosition(0);
        System.out.println("song restarted. song will be playing from beginning");
    }

    @Override
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        System.out.println("player set to loop");
    }

    @Override
    public void rewind() {
        clip.setMicrosecondPosition(clip.getMicrosecondPosition()- 10000000);
        System.out.println("song rewind by 10 seconds");
    }

    @Override
    public void fastForward() {
        clip.setMicrosecondPosition(clip.getMicrosecondPosition()+ 10000000);
        System.out.println("song fast forwarded by 10 seconds");
    }

    @Override
    public void exit() {
        clip.close();
        playerActive = false;
        System.out.println("closing player");
    }

    @Override
    public void shuffle() {
        shuffleIndex+=1;
        if (shuffleIndex%2 == 0){
            shuffleActive = true;
            System.out.println("shuffle turned on");
        }
        else{
            shuffleActive = false;
            System.out.println("shuffle turned off");
        }
    }

}
