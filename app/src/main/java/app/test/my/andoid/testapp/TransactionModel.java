package app.test.my.andoid.testapp;

public class TransactionModel {

    private String name;
    private String description;
    private String amount;
    private String date;
    public boolean isPositive;


    public TransactionModel(String name, String description, String amount, String date, boolean s) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        isPositive = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
