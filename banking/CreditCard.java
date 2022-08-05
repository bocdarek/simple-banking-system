package banking;

public class CreditCard {

    private String number;
    private String PIN;

    public CreditCard(String number) {
        this.number = number;
        generatePIN();
    }

    public CreditCard(String number, String PIN) {
        this.number = number;
        this.PIN = PIN;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    private void generatePIN() {
        int num = (int) (Math.random() * 10000);
        PIN = String.format("%04d", num);
    }
}
