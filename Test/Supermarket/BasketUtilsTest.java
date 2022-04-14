package Supermarket;

import static org.junit.jupiter.api.Assertions.*;

class BasketUtilsTest {

    private BasketUtils bu;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        bu = new BasketUtils();
    }

    @org.junit.jupiter.api.Test
    void getMoney() {
        String result = bu.getMoney(100.00);
        assertEquals(result, "£100.00");
    }
}