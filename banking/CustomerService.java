package banking;

import java.util.Scanner;

public class CustomerService {

    private boolean clientLogged = false;
    private Account clientAccount = null;
    private final BankingSystem bankingSystem;
    private final DataBaseService dbs = DataBaseService.getInstance();

    public CustomerService() {
        bankingSystem = BankingSystem.getInstance();
    }

    public boolean isClientLogged() {
        return clientLogged;
    }

    public void createAccount() {
        Account newAccount = bankingSystem.addAccount();
        System.out.println("\nYour card has been created");
        System.out.println("Your card number:");
        System.out.println(newAccount.getCreditCard().getNumber());
        System.out.println("Your card PIN:");
        System.out.println(newAccount.getCreditCard().getPIN() + "\n");
    }

    public void logIn() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter your card number:");
        String number = sc.nextLine().trim();
        System.out.println("Enter your PIN:");
        String PIN = sc.nextLine().trim();

        Account account = null;
        if (isNumberValid(number)) {
            int accountID = Integer.parseInt(number.substring(6, 15));
            account = dbs.getAccount(accountID);
        }
        if (account == null || !PIN.matches("\\d{4}") ||
                !account.checkPIN(PIN)) {
            System.out.println("\nWrong card number or PIN!\n");
            return;
        }
        System.out.println("\nYou have successfully logged in!\n");
        clientLogged = true;
        clientAccount = account;
    }

    private boolean isNumberValid(String number) {
        return number.matches("\\d{16}") && LuhnAlgorithm.check(number);
    }

    public void logOut() {
        System.out.println("\nYou have successfully logged out!\n");
        clientLogged = false;
        clientAccount = null;
    }

    public void checkBalance() {
        int balance = clientAccount.getBalance();
        System.out.println("\nBalance: " + balance + "\n");
    }

    public void closeAccount() {
        dbs.removeAccount(clientAccount);
        System.out.println("\nThe account has been closed!\n");
        clientLogged = false;
        clientAccount = null;
    }

    public void deposit() {
        System.out.println("\nEnter income:");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();
        int income;
        if (input.matches("[0-9]+")) {
            income = Integer.parseInt(input);
        } else {
            System.out.println("Wrong input!\n");
            return;
        }
        clientAccount.setBalance(clientAccount.getBalance() + income);
        dbs.updateBalance(clientAccount);
        System.out.println("Income was added!\n");
    }

    public void transfer() {
        System.out.println("\nTransfer");
        System.out.println("Enter card number:");
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine().trim();
        if (!isNumberValid(number)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!\n");
            return;
        }
        if (number.equals(clientAccount.getCreditCard().getNumber())) {
            System.out.println("You can't transfer money to the same account!\n");
            return;
        }
        int accountID = Integer.parseInt(number.substring(6, 15));
        Account receiverAccount = dbs.getAccount(accountID);
        if (receiverAccount == null) {
            System.out.println("Such a card does not exist.\n");
            return;
        }
        System.out.println("Enter how much money you want to transfer:");
        String input = sc.nextLine().trim();
        int amount;
        if (input.matches("[0-9]+")) {
            amount = Integer.parseInt(input);
        } else {
            System.out.println("Wrong input!\n");
            return;
        }
        if (amount > clientAccount.getBalance()) {
            System.out.println("Not enough money!\n");
            return;
        }
        clientAccount.setBalance(clientAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        dbs.updateBalance(clientAccount);
        dbs.updateBalance(receiverAccount);
        System.out.println("Success!\n");

    }
}


