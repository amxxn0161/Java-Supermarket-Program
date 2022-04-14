package Supermarket;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Product implements Serializable, Comparable<Product> {
    private String brand;
    private String description;
    private String code;
    private double price;

    public Product(String brand, String description, String code, double price) {
        this.brand = brand;
        this.description = description;
        this.code = code;
        this.price = price;

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "" + brand + ' ' +
                "-  Description:" + " " + description + ' ' +
                "," + "(" + code + ')' +
                ", Price: £" + price +
                ' ';
    }

    @Override
    public int compareTo(Product pr) {
        if (pr.getPrice() < this.getPrice()) {
            return 1;
        }else if(pr.getPrice() > this.getPrice()){
            return -1;
        }else{
            return 0;
        }

    }

}
