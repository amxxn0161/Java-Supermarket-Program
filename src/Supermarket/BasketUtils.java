package Supermarket;

import java.text.NumberFormat;
import java.util.Locale;

public class BasketUtils {

    public BasketUtils() {
    }

    public String getMoney(double amount){
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.UK);
        return nf.format(amount);
    }
}
