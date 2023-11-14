package models;

import service.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Jukebox {
    boolean userLogIn;
    private final List<User> allUsers = new ArrayList<>();

    public boolean isUserLogIn() {
        return userLogIn;
    }

    public User createUser(String username,String password){
        User user = new User(username, password);
        allUsers.add(user);
        System.out.println("user created");
        return user;
    }

    public User verifyUser(String username,String password){
       List<User> verifyUserList = allUsers.stream().filter(s->s.getUsername().equals(username)).filter(s->s.getPassword().equals(password)).collect(Collectors.toList());
       if (verifyUserList.size()==0){
           System.out.println("incorrect username or password");
           userLogIn = false;
           return null;
       }
       else {
           System.out.println("logged in");
           userLogIn = true;
           return verifyUserList.get(0);
       }
    }

}
