package banking;

public class Account {

    private String idNumber;
    private CreditCard creditCard;
    private int balance;

    public Account(String idNumber) {
        this(idNumber, 0);
    }

    public Account(String idNumber, int balance) {
        this.idNumber = idNumber;
        this.balance = balance;
        assignCreditCard();
    }

    public Account(String idNumber, int balance, CreditCard creditCard) {
        this.idNumber = idNumber;
        this.balance = balance;
        this.creditCard = creditCard;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    private void assignCreditCard() {
        String cardNumber = "400000" + idNumber;
        cardNumber = cardNumber + LuhnAlgorithm.calculateCheckSum(cardNumber);
        creditCard = new CreditCard(cardNumber);
    }

    public boolean checkPIN(String PIN) {
        return PIN.equals(creditCard.getPIN());
    }
}
