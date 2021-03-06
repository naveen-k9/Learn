import java.util.ArrayList;

public class Account {

    //The name of the account
    private String name;

    //The account ID number
    private String uuid;

    //the user object that owns this account.
    private User holder;

    //the list of transactions for this account
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank theBank){

        // set the account name and holder
        this.name = name;
        this.holder = holder;

        //get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        //init transactions
        this.transactions = new ArrayList<Transaction>();

    }

    public String getUUID(){
            return this.uuid;
    }

    /**
     *
     * @return
     */
    public Object getSummaryLine() {

        //get the balance
        double balance = this.getBalance();

        //format the summary line, depending on whether the balance is -ve
        if (balance >= 0){
            String s = this.uuid + " : " + balance + " : " + this.name;
            return s;

        }else {
            String s = this.uuid + " : " + balance + " : " + this.name;
            return s;        }
    }

    //get balance
    public double getBalance(){

        double balance = 0;
        for(Transaction t:this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    //print the transaction of the history
    public void printTransactionHistory() {

        System.out.printf("\nTransaction history for account %s\n",this.uuid);
        for(int t=this.transactions.size()-1;t>=0;t--){
            System.out.printf(this.transactions.get(t).getSummaryLine());
        }

    }

    /**
     *
     * @param amount
     * @param memo
     */
    public void addTransaction(double amount, String memo) {

        //create new transaction object and add it to our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
