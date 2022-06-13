package AccessClasses;
import Entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    static List<User> userList = new ArrayList<User>();

    public static boolean addUser(User user){
        boolean addedStatus = false;

        //check if user already exists
        if (userList.contains(user)){
            System.out.println("user already exists.");
            return addedStatus;
        }
        userList.add(user);

        return addedStatus; // returns whether addition was successful
    }

    public static boolean checkUser(User user){
        boolean checkStatus = false;

        if (!userList.contains(user)){
            System.out.println("user does not exist");
            return checkStatus;
        }
        return true;
    }

    public static void removeUser(User user){
        if (!userList.contains(user)){
            return;
        }
        userList.remove(user);
    }

    public static void updateUser(User user,String metadata, String value){
        if (!userList.contains(user)){
            System.out.println("user does not exist");
            return;
        }
        int index = userList.indexOf(user);
        User new_user = new User(user.getEmail(), user.getPassword(), user.getName());
        switch (metadata){
            case("name"):
                new_user.setName(value);
            case("password"):
                new_user.setPassword(value);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + metadata);
        }
        System.out.println("user information updated");
        userList.set(index,new_user);

    }
}
