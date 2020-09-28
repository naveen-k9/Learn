import java.util.Scanner;

public class bankingApplication {

    public static void main(String[] args){
        //TO DO AUTO generated method
        bankAccount obj = new bankAccount("XYZ","0A0001");
        obj.showMenu();
    }

}

class bankAccount{
    int balance;
    int previousTranscation;
    String customerName;
    String customerId;

    bankAccount(String cname, String cid){
        customerName = cname;
        customerId = cid;
    }

    void deposit(int amount){
        if(amount != 0){
            balance = balance + amount;
            previousTranscation = amount;
        }
    }

    void withdraw(int amount){
        if(amount != 0){
            balance = balance - amount;
            previousTranscation = -amount;//since withdra-w
        }
    }

    void getPreviousTransaction(){
        if(previousTranscation > 0){
            System.out.println("Deposited : "+previousTranscation);
        }
        else if(previousTranscation < 0){
            System.out.println("Withdrawn : "+Math.abs(previousTranscation));
        }
        else{
            System.out.println("No transaction occured!");
        }
    }


    void showMenu(){
        char option='\0';
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome "+customerName);
        System.out.println("Your ID is "+customerId);
        System.out.println("\n");
        System.out.println("A. Check Balance");
        System.out.println("B. Deposit");
        System.out.println("C. Withdraw");
        System.out.println("D. Previous transaction");
        System.out.println("E. Exit");

        do {
            System.out.println("===================================================");
            System.out.println("Enter an option");
            System.out.println("===================================================");
            option = scanner.next().charAt(0);
            System.out.println("\n");

            switch (option) {

                case 'A':
                    System.out.println("--------------------------------------");
                    System.out.println("Balance = " + balance);
                    System.out.println("--------------------------------------");
                    System.out.println("\n");
                    break;
                case 'B':
                    System.out.println("--------------------------------------");
                    System.out.println("Enter an amount to deposit : ");
                    System.out.println("--------------------------------------");
                    int amount = scanner.nextInt();
                    deposit(amount);
                    System.out.println("Deposited");
                    System.out.println("\n");
                    break;
                case 'C':
                    System.out.println("--------------------------------------");
                    System.out.println("Enter an amount to withdraw : ");
                    System.out.println("--------------------------------------");
                    int amountt = scanner.nextInt();
                    withdraw(amountt);
                    System.out.println("Withdrawn");
                    System.out.println("\n");
                    break;

                case 'D':
                    System.out.println("--------------------------------------");
                    getPreviousTransaction();
                    System.out.println("--------------------------------------");
                    System.out.println("\n");
                    break;

                case 'E':
                    System.out.println("=========================================");
                    break;

                default:
                    System.out.println("Invalid option! Please try again");
                    break;
            }


        }while (option != 'E');

        System.out.println("Thank you for using our services");
    }
}
