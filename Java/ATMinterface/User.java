import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class User {

    //The first name of the user
    private String firstName;

    //The last name of the user
    private String lastName;

    //The ID number of the user
    private String uuid;

    //The MD5 hash of the user's pin number
    private byte pinHash[];

    //The list of accounts for this user
    private ArrayList<Account> accounts;

    /**
     * Create a new user
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param pin       the user's account pin number
     * @param theBank   the bank object that user is a customer of
     **/


    public User(String firstName, String lastName, String pin,Bank theBank){

        //set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // hash the pin with MD5 algorithm
        //store the pin's MD5 hash ,rather than original value for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e){
            System.err.println("error, caught NoSuchAlgorithm");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new, unique universal ID for the user
        this.uuid = theBank.getNewUserUUID();

        // create empty list of accounts
        this.accounts = new ArrayList<Account>();

        // print log message
        System.out.printf("New user %s, %s with ID %s created.\n", lastName ,firstName, this.uuid);





    }

    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    /**
     * Returns the user's UUID
     *
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     *
     * @param aPin
     * @return
     */

    public boolean validatePin(String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e){
            System.err.println("error, caught NoSuchAlgorithm");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void printAccountSummary(){
        System.out.printf("\n\n%s's account summary", this.firstName);
        for(int k=0;k<this.accounts.size();k++){
            System.out.printf("%d) %s\n", k+1, this.accounts.get(k).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAccountTransactionHistory(int accountID) {
        this.accounts.get(accountID).printTransactionHistory();
    }

    public double getAccountBalance(int fromAccount) {
        return this.accounts.get(fromAccount).getBalance();
    }

    public String getAccountUUID(int account){
       return this.accounts.get(account).getUUID();
    }

    public void addAccountTransaction(int acc, double amount, String memo){
        this.accounts.get(acc).addTransaction(amount,memo);
    }


}
