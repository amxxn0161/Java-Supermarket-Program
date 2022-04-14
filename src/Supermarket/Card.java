package Supermarket;

import java.io.Serializable;
import java.util.ArrayList;

public class Card implements Serializable {
    private int accountNumber;
    private int sortCode;
    private String name;
    private int cvv;
    private String expiryDate;
    private ArrayList<String> paymentNotes;

    public Card(){
        paymentNotes = new ArrayList<String>();
    }

    public Card(int accountNumber, int sortCode, String name, int cvv, String expiryDate) {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.name = name;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    public void addPaymentNote(String notes){
        paymentNotes.add(notes);
    }

    public ArrayList<String> getPaymentNotes(){
        return paymentNotes;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

}

