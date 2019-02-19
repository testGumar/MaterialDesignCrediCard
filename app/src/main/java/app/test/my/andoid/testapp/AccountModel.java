package app.test.my.andoid.testapp;

public class AccountModel {


    private String name;
    private double amount;
    private double percentage;
    private boolean isPositive;


    public AccountModel(String name, double amount, double percentage, boolean isPositive) {
        this.name = name;
        this.amount = amount;
        this.percentage = percentage;
        this.isPositive = isPositive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }
}
