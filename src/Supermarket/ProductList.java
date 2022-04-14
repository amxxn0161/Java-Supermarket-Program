package Supermarket;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

public class ProductList extends ObservableListWrapper<Product> {

    public ProductList() {
        super(FXCollections.observableArrayList());
        loadProducts();
    }

    public void addProduct(String name, String description, String code, double price) {
        super.add(new Product(name, description, code, price));
    }

    public Product findProduct(String brand, String description, String code, double price){
        Product temp;
        int indexLocation = -1;

        for (int i = 0; i < super.size(); i++) {
            temp = super.get(i);

            if (temp.getBrand().equals(brand)){
                indexLocation = i;
                break;
            }
        }

        if (indexLocation == -1) {
            return null;
        } else {
            return super.get(indexLocation);
        }
    }

    public void removeProduct(String brand, String description, String code, double price){
        Product productToGo = this.findProduct(brand, description, code, price);
        super.remove(productToGo);
    }

    public Product findProductByCode(String code) {
        Product temp;
        int indexLocation = -1;

        for (int i = 0; i < super.size(); i++) {
            temp = super.get(i);

            if (temp.getCode().equals(code)) {
                indexLocation = i;
                break;
            }
        }

        if (indexLocation == -1) {
            return null;
        } else {
            return super.get(indexLocation);
        }

    }
    
    private void loadProducts() {
        ProductStock ps = new ProductStock();

        Product cadbury = new Product("Cadbury", "Dairy Milk", "0001", .99);
        super.add(cadbury);

        Product walkers = new Product("Walkers", "Crisps", "0002", 2.65);
        super.add(walkers);

        Product fairtrade = new Product("Fairtrade", "Banana", "0003", 1.35);
        super.add(fairtrade);

        Product oreo = new Product("Oreo O's", "Cereal", "0004", 3.99);
        super.add(oreo);

        Product fredo = new Product("Cadbury", "Fredo", "0005", .99);
        super.add(fredo);

        Product jd = new Product("Jack Daniels", "Imran's Whiskey", "0006", 32.50);
        super.add(jd);

        Product asda = new Product("ASDA", "Milk", "0007", 1.03);
        super.add(asda);

        Product kingsmill = new Product("Kingsmill", "Bread", "0008", 0.99);
        super.add(kingsmill);

        Product freerange = new Product("Free Range", "Eggs", "0009", 1.33);
        super.add(freerange);

        Product birdseye = new Product("Birds Eye", "30 Fish Fingers", "0010", 6.55);
        super.add(birdseye);

        Product mccain = new Product("McCain", "Chips", "0011", 2.17);
        super.add(mccain);

        Product bj = new Product("Ben & Jerry's", "Ice Cream", "0012", 2.99);
        super.add(bj);

        Product coke = new Product("Coca Cola", "Drink", "0013", 1.59);
        super.add(coke);

        Product cc = new Product("Coca Cola", "24 Pack", "0014", 11.25);
        super.add(cc);

        Product pepsi = new Product("Pepsi", "Drink", "0015", 1.49);
        super.add(pepsi);

        Product dates = new Product("ASDA", "Medjoul Dates", "0016", 3.75);
        super.add(dates);

        Product shazan = new Product("Shazans", "Halal Chicken", "0017", 4.15);
        super.add(shazan);


        super.sort();

        ps.addProductToStock(cadbury, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(cadbury);
        //ps.printProducts();

        ps.addProductToStock(walkers, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(walkers);


        ps.addProductToStock(fairtrade, 50);
        System.out.println("Reduce Stock");
        ps.reduceStock(fairtrade);


        ps.addProductToStock(fredo, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(fredo);


        ps.addProductToStock(jd, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(jd);


        ps.addProductToStock(asda, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(asda);


        ps.addProductToStock(kingsmill, 10);
        System.out.println("Reduce Stock");
        ps.reduceStock(kingsmill);


        ps.addProductToStock(freerange, 10);
        System.out.println("Reduce Stock");
        ps.reduceStock(freerange);


        ps.addProductToStock(birdseye, 10);
        System.out.println("Reduce Stock");
        ps.reduceStock(birdseye);


        ps.addProductToStock(mccain, 10);
        System.out.println("Reduce Stock");
        ps.reduceStock(mccain);


        ps.addProductToStock(bj, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(bj);


        ps.addProductToStock(coke, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(coke);


        ps.addProductToStock(cc, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(cc);


        ps.addProductToStock(pepsi, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(pepsi);


        ps.addProductToStock(dates, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(dates);


        ps.addProductToStock(shazan, 100);
        System.out.println("Reduce Stock");
        ps.reduceStock(shazan);

        ps.printProducts();
    }

}

