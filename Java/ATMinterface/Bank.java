import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;


    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID(){

        // inits
        String uuid;
        Random rnd = new Random();
        int len = 9;
        boolean nonUnique;




        // continue looping untill we get an unique ID
        do{

            uuid = "";
            for(int i=0;i<len;i++){
                uuid += ((Integer)rnd.nextInt(10)).toString();
            }

            //check to make sure its unique
            nonUnique = false;
            for (User u:this.users){
                if(uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);


        return uuid;
    }

    // Generate a new universally unique ID for an account
    public String getNewAccountUUID(){
        // inits
        String uuid;
        Random rnd = new Random();
        int len = 9;
        boolean nonUnique;


        // continue looping untill we get an unique ID
        do{

            uuid = "";
            for(int i=0;i<len;i++){
                uuid += ((Integer)rnd.nextInt(10)).toString();
            }

            //check to make sure its unique
            nonUnique = false;
            for (Account a:this.accounts){
                if(uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);


        return uuid;



    }

    // an account for the user
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param pin
     * @return
     */

    // add user
    public User addUser(String firstName, String lastName, String pin){

        // create a new User object and add it to our list
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);

        //create a savings account for the user
        Account newAccount = new Account("Savings", newUser, this);

        // add to holder and bank lists
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    //get the user object associated with userID and pin,if they are valid
    public User userLogin(String userID, String pin){

        //search through the list of users
        for(User u:this.users){

            //check user ID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
               return u;
            }
        }

        // if we not found the user or have an incorrect pin
        return null;

    }
    public String getName(){
        return this.name;
    }



}

