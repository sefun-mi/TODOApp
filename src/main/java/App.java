import Entities.*;
import AccessClasses.*;

import java.util.Scanner;

public class App {
    static Scanner scan= new Scanner(System.in);

    public static final int OPTION_LOGIN = 1;
    public static final int OPTION_REGISTER = 2;
    public static final int EXIT = 3;

    public static final int OPTION_UPDATE_USER = 10;
    public static final int OPTION_ADD_TODO = 11;
    public static final int OPTION_DELETE_TODO = 12;
    public static final int OPTION_UPDATE_TODO = 13;
    public static final int OPTION_FIND_TODO = 14;
    public static final int OPTION_GET_TODO = 15;

    public static final int OPTION_GET_ALL = 150;
    public static final int OPTION_GET_ACTIVE = 151;
    public static final int OPTION_GET_COMPLETE = 152;

    public static User HOME_USER;
    public static void main(String[] args){
        boolean running = true;
        int option =GET_HOME_OPTION();
        while (running){
            switch (option){
                case (OPTION_LOGIN):
                    LOGIN_USER();
                case(OPTION_REGISTER):
                    REGISTER_USER();
                case(EXIT):
                    EXIT();
                    running = false;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
        }

    }

    public static int GET_HOME_OPTION(){
        System.out.println("Welcome, what would you like to do");
        System.out.println("If you wold like to register as a user please enter: "+ OPTION_REGISTER);
        System.out.println("If you wold like to LOGIN as a user please enter: "+ OPTION_LOGIN);
        System.out.println("If you wold like to close the application please enter: "+ EXIT);
        int option = scan.nextInt();
        return option;
    }

    public static void LOGIN_USER(){
        System.out.println("Please enter your name");
        String name = scan.next().trim();
        System.out.println("Please enter your username/email");
        String email = scan.next().trim();
        System.out.println("Please enter your password");
        String password = scan.next().trim();
        HOME_USER= new User(email,password,name);

        if (!UserDao.checkUser(HOME_USER)){
            System.out.println("invalid user input");
            return;
        }

        USER_OPERATION();
    }

    public static void REGISTER_USER(){
        System.out.println("Please enter your firstname");
        String name = scan.next().trim();
        System.out.println("Please enter your username/email");
        String email = scan.next().trim();
        System.out.println("Please enter your password");
        String password = scan.next().trim();
        HOME_USER = new User(email,password,name);
        UserDao.addUser(HOME_USER);

        System.out.println("You may now proceed to login");
        LOGIN_USER();
    }

    public static void EXIT(){
        System.out.println("closing...");
        System.out.println("GoodBye.");

    }

    public static int GET_USER_OPERATION(){
        int operation;
        System.out.println("What action would you like to take ?");
        System.out.println("If you wold like update your user info please enter: "+ OPTION_UPDATE_USER);
        System.out.println("If you wold like to add a TODO please enter: "+ OPTION_ADD_TODO);
        System.out.println("If you wold like to delete a TODO please enter: "+ OPTION_DELETE_TODO);
        System.out.println("If you wold like to update a TODO please enter: "+ OPTION_UPDATE_TODO);
        System.out.println("If you wold like to find a TODO please enter: "+ OPTION_FIND_TODO);
        System.out.println("If you wold like to get a TODO or list of TODO items please enter: "+ OPTION_GET_TODO);
        System.out.println("If you wold like to close the application please enter: "+ EXIT);
        operation = scan.nextInt();
        return operation;
    }

    public static void USER_OPERATION(){
        int operation = GET_USER_OPERATION();
        boolean op_running = true;
        while(op_running){
            switch(operation){
                case(OPTION_UPDATE_USER):
                    System.out.println("updating user");
                    userUpdate();
                case(OPTION_ADD_TODO) :
                    System.out.println("adding todo");
                    TODOAdd();
                case(OPTION_DELETE_TODO):
                    System.out.println("deleting todo");
                    TODODelete();
                case (OPTION_UPDATE_TODO):
                    System.out.println("updating todo");
                    TODOUpdate();
                case(OPTION_FIND_TODO):
                    System.out.println("finding todo");
                    TODOFind();
                case(OPTION_GET_TODO):
                    System.out.println("getting TODO");
                    TODOGet();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + operation);
            }
        }
    }

    public static void userUpdate(){
        String metadata;
        String value;
        System.out.println("Please enter what credential you would like to change");
        System.out.println("If you would like to change your username please enter 'username': ");
        System.out.println("If you would like to change your password please enter 'password': ");
        metadata = scan.next().trim();
        System.out.println("What would you like to change it to: ");
        value = scan.next().trim();
        UserDao.updateUser(HOME_USER,metadata,value);
    }

    public static void TODOAdd(){
        TODO item;
        System.out.println("Enter the title of your TODO");
        String title = scan.next();
        item = new TODO(title);
        System.out.println("would you like to enter other information");
        System.out.println("yes or no (y/n): ");
        String opt = scan.next().trim();
        switch(opt){
            case("n"):
                if (TODODao.addTODO(HOME_USER.getEmail(), item)){
                    System.out.println("TODO added");
                }
                else{
                    System.out.println("There was an error adding the user");
                }
            case("y") :
                System.out.println("please enter a description of your TODO item: ");
                String descrip = scan.next();
                item.setDescription(descrip);
                System.out.println("please enter the day created: ");
                String day = scan.next().trim();
                item.setDayCreated(day);
                if (TODODao.addTODO(HOME_USER.getEmail(), item)){
                    System.out.println("TODO added");
                }
                else{
                    System.out.println("There was an error adding the user");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + opt);
        }

    }

    public static void TODODelete(){
        System.out.println("Enter the title of your TODO item: ");
        String title = scan.next();
        if (TODODao.removeTODO(HOME_USER.getEmail(), title)){
            System.out.println("removal successful");
        }
        else{
            System.out.println("there was an error removing the TODO");
        }

    }

    public static void TODOUpdate(){
        boolean update = false;
        System.out.println("Enter the title of your TODO item: ");
        String title = scan.next();
        System.out.println("Do you want to mark your TODO as active or completed");
        System.out.println("Active or Completed (a/c): ");
        String status = scan.next().trim();
        switch (status){
            case("a"):
                update = false;
            case("c"):
                update = true;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + status);
        }
        TODODao.updateTODO(HOME_USER.getEmail(), title, update);

    }

    public static void TODOFind(){
        System.out.println("Enter the reference text to your TODO item: ");
        String ref = scan.next();
        System.out.println(TODODao.findTODO(HOME_USER.getEmail(), ref).toString());
    }

    public static void TODOGet(){
        System.out.println("What TODO items do you want to get? ");
        System.out.println("To get all TODO items please enter: " + OPTION_GET_ALL);
        System.out.println("To get all TODO items please enter: " + OPTION_GET_ACTIVE);
        System.out.println("To get all TODO items please enter: " + OPTION_GET_COMPLETE);
        int get_opt = scan.nextInt();

        switch(get_opt){
            case(OPTION_GET_ALL):
                System.out.println(TODODao.getAllTODO(HOME_USER.getEmail()).toString());
            case(OPTION_GET_ACTIVE):
                System.out.println(TODODao.getActiveTODO(HOME_USER.getEmail()).toString());
            case(OPTION_GET_COMPLETE):
                System.out.println(TODODao.getCompletedTODO(HOME_USER.getEmail()).toString());
        }
    }
}
