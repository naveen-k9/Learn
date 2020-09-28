import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        //scan
        Scanner sc = new Scanner(System.in);

        // init bank
        Bank theBank = new Bank("Bank of Cain");

        // add a user, which also creates a savings account
        User aUser = theBank.addUser("John", "Carter", "9999");

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);


        User currentUser;
        while (true){

            //stay in the login prompt untill successful login
            currentUser = ATM.mainMenuPrompt(theBank, sc);

            //stay in main menu intill user quits
            ATM.printUserMenu(currentUser,sc);

        }


    }

    public static void printUserMenu(User theUser, Scanner sc) {

        //print a summary of the users accounts
        theUser.printAccountSummary();

        //init
        int choice;

        //user menu
        do{
            System.out.printf("Welcome %s, what would you like to do?\n",
                    theUser.getFirstName());
            System.out.println("1. Show account transaction history");
            System.out.println("2. Withdrawal");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println();
            System.out.println("Enter choice : ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice.Please choose 1-5.");
            }

        }while(choice < 1 || choice > 5);

        // process the choice
        switch (choice){

            case 1:
                ATM.showTransactionHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;

        }

        // redisplay this menu unless user wants to quit
        if(choice != 5){
            ATM.printUserMenu(theUser, sc);
        }

    }



    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        //inits
        String userID;
        String pin;
        User authUser;

        //prompts the user for user ID?Pin combo untill one is reached
        do{
            System.out.printf("\n\nWelcome to %s\n\n",theBank.getName());
            System.out.print("Enter user ID : ");
            userID = sc.nextLine();
            System.out.print("Enter pin : ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the ID and pin Combo
            authUser = theBank.userLogin(userID,pin);
            if (authUser == null){
                System.out.println("Incorrect user ID/pin."+
                        "Please try again");

            }

        }while (authUser == null);//continue looping untill successful login

        return authUser;
    }

    public static void showTransactionHistory(User theUser, Scanner sc) {

        int theAccount;

        //get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                            "whose transactions tou want to see : ",
                    theUser.numAccounts());
            theAccount = sc.nextInt() - 1;
            if (theAccount < 0 || theAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account try again");
            }
        } while (theAccount < 0 || theAccount >= theUser.numAccounts());


        //prints the transaction history
        theUser.printAccountTransactionHistory(theAccount);
    }


    public static void transferFunds(User theUser, Scanner sc) {

        //inits
        int fromAccount;
        int toAccount;
        double amount;
        double accountBal;

        //get the account to transfer from
        do{
           System.out.printf("Enter the number (1-%d) of the account\nto transfer from : ",theUser.numAccounts());
           fromAccount = sc.nextInt()-1;
           if (fromAccount < 0 || fromAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account try again");
            }
        } while (fromAccount < 0 || fromAccount >= theUser.numAccounts());

        accountBal = theUser.getAccountBalance(fromAccount);

        // get the account to transsfer to
        do{
            System.out.printf("Enter the number (1-%d) of the account\nto transfer to : ",theUser.numAccounts());
            toAccount = sc.nextInt()-1;
            if (toAccount < 0 || toAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account try again");
            }
        } while (toAccount < 0 || toAccount >= theUser.numAccounts());

        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    accountBal);
            amount = sc.nextInt();
            if (amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if (amount > accountBal){
                System.out.printf("Amount must not be greater than\n balance of $.02f.\n",accountBal);
            }
        }while (amount <0 || amount > accountBal);

        // finally , do the transfer
        theUser.addAccountTransaction(fromAccount, -1*amount,
                String.format("Transfer to account %s",theUser.getAccountUUID(toAccount)));
        theUser.addAccountTransaction(toAccount, amount,
                String.format("Transfer to account %s",theUser.getAccountUUID(fromAccount)));

    }

    /**
     * withdraw
     * @param theUser
     * @param sc
     */
    public static void withdrawFunds(User theUser, Scanner sc) {

        //inits
        int fromAccount;
        double amount;
        double accountBal;
        String memo;

        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+
                    "to withdraw from : ",theUser.numAccounts());
            fromAccount = sc.nextInt()-1;
            if (fromAccount < 0 || fromAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account try again");
            }
        } while (fromAccount < 0 || fromAccount >= theUser.numAccounts());

        accountBal = theUser.getAccountBalance(fromAccount);

        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    accountBal);
            amount = sc.nextInt();
            if (amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if (amount > accountBal){
                System.out.printf("Amount must not be greater than\n balance of $.02f.\n",accountBal);
            }
        }while (amount <0 || amount > accountBal);

        // gobble up rest of previous input
        sc.nextLine();

        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdrawl
        theUser.addAccountTransaction(fromAccount,-1*amount,memo);

    }

    /**
     * deposit
     * @param theUser
     * @param sc
     */
    public static void depositFunds(User theUser, Scanner sc) {

        //inits
        int toAccount;
        double amount;
        double accountBal;
        String memo;

        //get the account to transfer
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+
                    "to transfer from : ",theUser.numAccounts());
           toAccount = sc.nextInt()-1;
            if (toAccount < 0 || toAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account try again");
            }
        } while (toAccount < 0 || toAccount >= theUser.numAccounts());

        accountBal = theUser.getAccountBalance(toAccount);

        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to deposit in (max $%.02f): $",
                    accountBal);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero.");
            }
        }while (amount < 0);

        // gobble up rest of previous input
        sc.nextLine();

        //get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdrawl
        theUser.addAccountTransaction(toAccount,amount,memo);
    }
}

