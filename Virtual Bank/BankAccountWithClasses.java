import java.io.*;
import java.util.*; 

public class BankAccountWithClasses { 
  
  public static void main(String[] args) throws Exception {
    
    // setting how many accounts this program can run up to
    final int maxNum = 100;
    BankAccount[] bankCustomer = new BankAccount[maxNum];
    
    // variables:
    int numAccts;
    char choice;
    boolean notDone = true;
    
    // Scanners
    Scanner kybd = new Scanner(System.in);
    PrintStream ps = new PrintStream("OutFile.txt");
    
    // create the intial database
    numAccts = readAccts(bankCustomer, maxNum);
    printAccts(bankCustomer, numAccts, ps);

    // set up the menu
    
    do {
      menu();
      choice = kybd.next().charAt(0);
      switch (choice) {
        case 'Q':
        case 'q':
          notDone = false;
          printAccts(bankCustomer, numAccts, ps);
          break;
        case 'D':
        case 'd':
          deposit(bankCustomer, numAccts, ps, kybd);
          break;
        case 'W':
        case 'w':
          withdrawal(bankCustomer, numAccts, ps, kybd);
          break;
        case 'B':
        case 'b':
          balance(bankCustomer, numAccts, ps, kybd);
          break; 
        case 'N':
          case 'n':
          newAcct(bankCustomer, numAccts, ps, kybd);
          break;
        case 'X':
        case 'x':
          deleteAcct(bankCustomer, numAccts, ps, kybd);
          break;
        case 'I':
        case 'i':
          acctInfo(bankCustomer, numAccts, ps, kybd);
          break;
        default:
          System.out.println("The choice " + choice + " is not a valid option.");
          System.out.println();
          ps.flush(); 
      } // end of while method 
      } while (notDone);
      ps.close();
      kybd.close();
      System.out.println();
      System.out.println("The program is terminating."); 
  } // end of main method
      
      public static void menu() {
        System.out.println();
        System.out.println(" Please select one of the following transactions: ");
        System.out.println("\t**************************************");
        System.out.println("\t List of Transactions ");
        System.out.println("\t**************************************");
        System.out.println("\t W- Withdrawal");
        System.out.println("\t D- Deposit");
        System.out.println("\t N- New Account");
        System.out.println("\t B- Balance Inquiry");
        System.out.println("\t I- Personal Info");
        System.out.println("\t X- Delete Account");
        System.out.println("\t Q- Quit/ Exit");
        System.out.println();
        System.out.print("\t Please enter your selection: ");
      } // end of menu. 
      
      public static int readAccts(BankAccount[] BankCustomer, int maxNum) throws Exception {
        // Scanner that reads in the input file
        File inputFile = new File("inputFile2.txt");
        Scanner sc = new Scanner(inputFile);
        
        // set a counter that reads in the accounts/ variable for temp string
        int count = 0;
        String tempStr;
        
        while (sc.hasNext() && count < maxNum) {
          PersonalInfo personal = new PersonalInfo();
          BankInfo bank = new BankInfo();
          BankAccount customer = new BankAccount();
          
          String line = sc.nextLine();
          StringTokenizer myLine = new StringTokenizer(line);

          personal.setFirst(myLine.nextToken());
          personal.setLast(myLine.nextToken());
          personal.setSocial(myLine.nextToken());
          tempStr = myLine.nextToken();
          bank.setAcctNumber(Integer.parseInt(tempStr)); //converts the read-in
          //string to integer
          bank.setAcctType(myLine.nextToken());
          tempStr = myLine.nextToken();
          bank.setBalance(Double.parseDouble(tempStr)); // converts the read in
          //string to double
          customer.setPersonalInfo(personal);
          customer.setBankInfo(bank);
          BankCustomer[count] = customer;
          count++;
        }
        sc.close();
        return count;
      } // end of the readData Method
      
      public static void printAccts(BankAccount[] BankCustomer, int numAccts, PrintStream ps) {
        // creating local java files
        PersonalInfo personal = new PersonalInfo();
        BankInfo bank = new BankInfo();
        BankAccount customer = new BankAccount();
        
        // table heading
        ps.println("\t***************************************");
        ps.println("\t\t\tDatabase of the bank");
        ps.println("\t***************************************");
        ps.println();
        // columns
        ps.printf("%-12s%-10s%-18s%-11s%-18s%-15s","FirstName","LastName",
                  "Social Security","AcctNumber","AccountType","Balance");
        ps.println();
        for (int i = 0; i < numAccts; i++) {
          personal = BankCustomer[i].getPersonalInfo();
          ps.printf("%-5s",personal.getFirst());
          ps.print("\t\t" + personal.getLast());
          ps.print("\t\t" + personal.getSocial());
          bank = BankCustomer[i].getBankInfo();
          ps.print("\t\t" + bank.getAcctNumber());
          ps.printf("\t%-10s", bank.getAcctType());
          ps.printf("\t\t$%9.2f", bank.getBalance());
          ps.println();
        }
        ps.println();
        
        ps.flush();
      } // end of printAccts
      
      public static int findAccts(BankAccount[] BankCustomer, int numAccts, int reqAcct) {
        for (int count = 0; count < numAccts; count++)
          if (BankCustomer[count].getBankInfo().getAcctNumber() == reqAcct)
          return count;
        return -1;
      } // end of the findAccts
      
      public static void withdrawal(BankAccount[] BankCustomer, int numAccts, PrintStream ps, Scanner kybd) {
        int reqAcct, index;
        double amountToWithdraw = 0;
        double balance;
        
        System.out.println();
        System.out.print("Enter your account number: ");
        reqAcct = kybd.nextInt();
        
        // using index to find if the account number enter matches with the database
        index = findAccts(BankCustomer, numAccts, reqAcct);
        if (index == -1) {
          ps.println("Transaction requested: Withdrawal");
          ps.println(" Error: Account Number: " + reqAcct + " is invalid.");
        } else {
          System.out.print("Enter amount to withdraw: ");
          amountToWithdraw = kybd.nextDouble();
        }
        balance = BankCustomer[index].getBankInfo().getBalance();

        if (amountToWithdraw <= 0.00 || amountToWithdraw > balance) {
          ps.println("Transaction requested: Withdraw");
          ps.println("Account Number : " + reqAcct);
          ps.printf("Account Balance: $%9.2f", balance);
          ps.println();
          ps.printf("Amount to Withdraw $%9.2f:", amountToWithdraw);
          ps.println();
          ps.printf("Error: $%9.2f", amountToWithdraw);
          ps.println(" is an invalid amount.");
        } else {
          ps.println("Transaction Requested: Withdraw");
          ps.println("Account Number: " + reqAcct);
          ps.printf("Old Balance: $%9.2f", balance);
          ps.println();
          ps.printf("Amount to Withdraw: $%.2f", amountToWithdraw);
          ps.println();
          balance -= amountToWithdraw;
          ps.printf("New Balance: $%9.2f", balance);
          ps.println();
        }
        ps.println();
        ps.println();
        ps.flush();
      } // end of withdrawal
      
      public static void deposit(BankAccount[] BankCustomer, int numAccts, PrintStream ps, Scanner kybd) {
        int reqAcct, index;
        double amountToDeposit = 0;
        
        System.out.println();
        System.out.print("Enter your account number: ");
        reqAcct = kybd.nextInt();
        
        // using index to find if the account number enter matches with the database
        index = findAccts(BankCustomer, numAccts, reqAcct);
        
        double balance = BankCustomer[index].getBankInfo().getBalance();
        if (index == -1) {
          ps.println("Transaction requested: Deposit");
          ps.println(" Error: Account Number: " + reqAcct + "is invalid.");
        } else {
          System.out.print("Enter amount to deposit: ");
          amountToDeposit = kybd.nextDouble();
        }
        //double balance = BankCustomer[index].getBankInfo().getBalance();
        if (amountToDeposit < 0.00) {
          ps.println("Transaction requested: Deposit");
          ps.println("Account Number : " + reqAcct);
          ps.printf("Account Balance: $%9.2f", balance);
          ps.println();
          ps.printf("Amount to Deposit: $%9.2f", amountToDeposit);
          ps.println();
          ps.printf("Error: $%9.2f", amountToDeposit);
          ps.println(" is an invalid amount.");
        } else {
          ps.println("Transaction Requested: Deposit");
          ps.println("Account Number: " + reqAcct);
          ps.printf("Old Balance $%9.2f", balance);
          ps.println();
          ps.printf("Amount to Deposit: $%9.2f", amountToDeposit);
          ps.println();
          balance += amountToDeposit;
          ps.printf("New Balance: $%9.2f", balance);
          ps.println();
        }
        pause(kybd); 
        ps.println();
        ps.flush();
      } // end of deposit
      
      public static void balance(BankAccount[] BankCustomer, int numAccts, PrintStream ps, Scanner kybd) {
        int reqAcct, index;
        System.out.println();
        System.out.print("Enter your account number: ");
        reqAcct = kybd.nextInt();
        
        // using index to find if the account number enter matches with the database
        index = findAccts(BankCustomer, numAccts, reqAcct);
        double balance = BankCustomer[index].getBankInfo().getBalance();
        
        if (index == -1) {
          ps.println("Transaction requested: Balance Inquiry");
          ps.println(" Error: Account Number: " + reqAcct + "is not invalid.");
          pause(kybd);
        } else {
          ps.println("Transaction requested: Balance Inquiry");
          ps.println("Account Number: " + reqAcct);
          ps.printf("Current Balance: $%9.2f", balance);
        }
        pause(kybd);
        ps.println();
        ps.flush();
      } // end of balance
      
      
 public static int newAcct(BankAccount[] BankCustomer, int numAccts, PrintStream ps, Scanner kybd) {
         int newAcctNum, index;
         double initialBalance = 0;
         String first, last, acctType, social;
         
         // creating local java files
         PersonalInfo personal = new PersonalInfo();
         BankInfo bank = new BankInfo();
         //BankAccount customer = new BankAccount();
         
         System.out.print("Enter your first name: ");
         first = kybd.next();
         personal.setFirst(first);
         kybd.nextLine();
         // System.out.println();
         
         System.out.print("Enter your last name: ");
         last = kybd.nextLine();
         personal.setLast(last);
         kybd.nextLine();
         System.out.println();
         
         System.out.print("Enter your social security: ");
         social = kybd.nextLine();
         personal.setSocial(social);
         kybd.nextLine();
         System.out.println();
         
         System.out.print("Enter 6 digit for your new account number: ");
         newAcctNum = kybd.nextInt();
         bank.setAcctNumber(newAcctNum);
         kybd.hasNextLine();
         System.out.println();
         
         System.out.print("Select the Account Type (Checking, Savings or CD): ");
         acctType = kybd.next();
         bank.setAcctType(acctType);
         kybd.nextLine();
         System.out.println();
         
         // using index to find if the account number enter matches with the database
         index = findAccts(BankCustomer, numAccts, newAcctNum);
         
         if (index != -1) {
           System.out.println("The account number you entered is in our database.");
           pause(kybd);
         } else {
           System.out.print("Enter the amount to deposit to your new Account: ");
           initialBalance = kybd.nextDouble();
           kybd.nextLine();
           bank.setBalance(initialBalance);
           
           if (initialBalance <= 00.00) {
             ps.println("Transaction Requested: New Account Deposit");
             ps.println("New Account Number" + newAcctNum);
             ps.println("Amount to First Deposit: $" + initialBalance);
             ps.printf("Error: $%9.2f", initialBalance + "is an invalid amount.");
             ps.println();
           } else {
             ps.println("Transaction Requested: First Account Setup");
             ps.println("Name of the Account Holder: " + first + " " + last);
             ps.println("Account Number: " + newAcctNum);
             ps.println("Social Security Number: " + social);
             ps.println("Account Type: " + acctType);
             ps.printf("Amount for First Deposit: $%9.2f", initialBalance);
             ps.println();
             numAccts++;
           }
         }
         pause(kybd);
         ps.println();
         ps.flush();
         return numAccts;
       } // end of NewAcct
 
 public static int deleteAcct(BankAccount[] BankCustomer, int numAccts, PrintStream ps, Scanner kybd) {
   int index, reqAcct, nextAcct;
   double balance, nextBalance;
   String firstName, lastName, social, AcctType;
   String nextFirstName, nextLastName, nextSocial, nextAcctType;
   
   System.out.print("Enter Account Number: ");
   reqAcct = kybd.nextInt();
   index = findAccts(BankCustomer, numAccts, reqAcct);
   balance = BankCustomer[index].getBankInfo().getBalance();
   firstName = BankCustomer[index].getPersonalInfo().getFirst();
   lastName = BankCustomer[index].getPersonalInfo().getLast();
   social = BankCustomer[index].getPersonalInfo().getSocial();
   AcctType = BankCustomer[index].getBankInfo().getAcctType();
   nextBalance = BankCustomer[numAccts - 1].getBankInfo().getBalance();
   nextAcct = BankCustomer[numAccts - 1].getBankInfo().getAcctNumber();
   nextFirstName = BankCustomer[numAccts - 1].getPersonalInfo().getFirst();
   nextLastName = BankCustomer[numAccts - 1].getPersonalInfo().getLast();
   nextSocial = BankCustomer[numAccts - 1].getPersonalInfo().getSocial();
   nextAcctType = BankCustomer[numAccts - 1].getBankInfo().getAcctType();
   
   if (index == -1) {
     ps.println("Transaction Requested: Delete Account");
     ps.println("Error: ");
     ps.println("Account Number: " + reqAcct + "does not exist.");
     pause(kybd);
   } else {
     if (balance >= 0.01) {
       ps.println("Transaction Requested: Delete Account");
       ps.println("Account Number: " + reqAcct);
       ps.printf("Error: $%9.2f", balance);
       ps.println(" remaining.");
       ps.println("Please withdraw remaining balance.");
       ps.println();
     } else {
       ps.println("Transaction Requested: Delete Account");
       ps.println("Account Number: " + reqAcct);
       ps.printf("Balance: $%9.2f ", balance);
       ps.println();
       firstName = nextFirstName;
       lastName = nextLastName;
       social = nextSocial;
       AcctType = nextAcctType;
       balance = nextBalance;
       reqAcct = nextAcct;
       numAccts--;
       ps.println("Account has been terminated");
       ps.println();
     }
     pause(kybd);
   }
   return numAccts;
 } // end of the deleteAcct
 
 public static int findSocial(BankAccount[] BankCustomer, int numAccts, int reqSocial) {
   String social;
   int social2;
   
   for (int count = 0; count < numAccts; count++) {
     social = BankCustomer[count].getPersonalInfo().getSocial();
     social2 = Integer.parseInt(social);
     if (social2 == reqSocial)
       return BankCustomer[count].getBankInfo().getAcctNumber();
   }
   return -1;
   
 } // end of the findSocial
 
 public static void acctInfo(BankAccount[] BankCustomer, int numAccts, PrintStream ps, Scanner kybd) {
   int index, acctNumber, social;
   String firstName, lastName, AcctType;
   double balance;
   
   System.out.print("Enter the Social Security: ");
   social = kybd.nextInt();
   kybd.nextLine();
   
   acctNumber = findSocial(BankCustomer, numAccts, social);
   index = findAccts(BankCustomer, numAccts, acctNumber);
   balance = BankCustomer[index].getBankInfo().getBalance();
   firstName = BankCustomer[index].getPersonalInfo().getFirst();
   lastName = BankCustomer[index].getPersonalInfo().getLast();
   AcctType = BankCustomer[index].getBankInfo().getAcctType();
   
   if (index == -1) {
     ps.println("Transaction Requested: Account Information");
     ps.println("Error: ");
     ps.println("Social Security Number: " + social + "does not exist.");    
     
   } else {
     ps.println("Transaction requested: Account Information");
     ps.println("Name of Account Holder: " + firstName + " " + lastName);
     ps.println("Social Security Number: " + social);
     ps.println("Account Number: " + acctNumber);
     ps.println("Account Type: " + AcctType);
     ps.printf("Current Balance: $%9.2f", balance);
     ps.println();
   }
   pause(kybd);
 } // end of acctInfo
 
 public static void pause(Scanner kybd) {
   String tempStr;
   System.out.println();
   System.out.print("press Enter to continue.");
   tempStr = kybd.nextLine();
   tempStr = kybd.nextLine();
 } // end of pause method
  } // end of public class
  
  
       
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        