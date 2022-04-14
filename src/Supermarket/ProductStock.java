package Supermarket;

import java.util.HashMap;
import java.util.Map;

public class ProductStock {
    private HashMap<Product, Integer> productStock = new HashMap<>();

    public void addProductToStock(Product p, Integer q){
        productStock.put(p, q);
    }

    public void reduceStock(Product p){
        if(productStock.containsKey(p)){

            System.out.println(productStock.get(p));

            productStock.put(p, productStock.get(p) -1);

            System.out.println(productStock.get(p));
        }
    }

    public void printProducts(){
        for(Map.Entry<Product, Integer> product : productStock.entrySet())
        {
            System.out.println("Product : "+product.getKey().getBrand()+"  Quantity : "+product.getValue());
        }
    }
}
