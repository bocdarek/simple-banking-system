package banking;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CustomerService service = new CustomerService();
        Scanner sc = new Scanner(System.in);
        DataBaseService dbs = DataBaseService.getInstance();
        if (args.length == 2 && args[0].equals("-fileName")) {
            dbs.setDataBase(args[1]);
        } else {
            System.out.println("Failed to connect to Database.");
            return;
        }

        while (true) {
            if (!service.isClientLogged()) {
                System.out.println("1. Create an account");
                System.out.println("2. Log into account");
                System.out.println("0. Exit");
                String command = sc.nextLine().trim();
                if (command.length() == 0) {
                    System.out.println();
                    continue;
                }
                switch (command.charAt(0) + "") {
                    case "1":
                        service.createAccount();
                        break;
                    case "2":
                        service.logIn();
                        break;
                    case "0":
                        System.out.println("\nBye!");
                        return;
                    default:
                        System.out.println();
                }

            } else {   // Client is logged
                System.out.println("1. Balance");
                System.out.println("2. Add income");
                System.out.println("3. Do transfer");
                System.out.println("4. Close account");
                System.out.println("5. Log out");
                System.out.println("0. Exit");
                String command = sc.nextLine().trim();
                if (command.length() != 1) {
                    System.out.println();
                    continue;
                }
                switch (command.charAt(0) + "") {
                    case "1":
                        service.checkBalance();
                        break;
                    case "2":
                        service.deposit();
                        break;
                    case "3":
                        service.transfer();
                        break;
                    case "4":
                        service.closeAccount();
                        break;
                    case "5":
                        service.logOut();
                        break;
                    case "0":
                        System.out.println("\nBye!");
                        return;
                    default:
                        System.out.println();
                }
            }

        }
    }
}