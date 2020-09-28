import java.util.Date;

public class Transaction {

    //the amount of this transaction
    private double amount;

    //the time and date of this transaction
    private Date timestamp;

    //a memo for this transaction
    private String memo;

    //the account in which the transaction was performed.
    private Account inAccount;

    public Transaction(double amount, Account inAccount){

        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo="";

    }

    public Transaction(double amount,String memo, Account inAccount){

        //call the 2-arc constructor first
        this(amount, inAccount);

        //set the memo
        this.memo = memo;
    }

    //returns amount
    public double getAmount(){
        return this.amount;
    }

    // summarize the transaction
    public String getSummaryLine(){

        if (this.amount >= 0){
            return String.format("%s : $%.02f : %s", this.timestamp,toString(),this.amount, this.memo);
        } else {
            return String.format("%s : $%.02f : %s", this.timestamp,toString(),-this.amount, this.memo);
        }
    }


}
