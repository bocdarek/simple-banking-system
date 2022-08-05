package banking;

public class BankingSystem {

    private static BankingSystem bankingSystem;
    private final DataBaseService dbs = DataBaseService.getInstance();

    private BankingSystem() {}

    public static BankingSystem getInstance() {
        if (bankingSystem == null) {
            bankingSystem = new BankingSystem();
        }
        return bankingSystem;
    }

    public Account addAccount() {
        String newID = generateAccountID();
        Account account = new Account(newID);
        dbs.addAccount(account);
        return account;
    }


    private String generateAccountID() {
        int idNumber;
        do {
            idNumber = (int) (Math.random() * 1_000_000_000);
        } while (dbs.containsID(idNumber));
        return String.format("%09d", idNumber);
    }
}
