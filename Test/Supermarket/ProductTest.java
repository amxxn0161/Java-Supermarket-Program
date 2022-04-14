package Supermarket;

import org.junit.Test;

public class ProductTest {
    private Product testProduct = new Product("Cadbury", "Dairy Milk", "0001", .99);

    @Test
    public void productCode(){
        String code = testProduct.getCode();

        assert code.equals("0001");
    }

    @Test
    public void productName(){
        String brand = testProduct.getBrand();

        assert brand.equals("Cadbury");
    }

    @Test
    public void productDescription(){
        String description = testProduct.getDescription();

        assert description.equals("Crisps");
    }

    @Test
    public void productPrice(){
        double price = testProduct.getPrice();

        assert price == 0.99;
    }
}
