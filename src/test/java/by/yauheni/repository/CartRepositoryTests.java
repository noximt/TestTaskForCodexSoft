package by.yauheni.repository;

import by.yauheni.entity.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @Test
    void saveCart(){
        Cart cart = new Cart(1, null, null);
        cartRepository.save(cart);
        Cart byId = cartRepository.getById(1L);
        assertEquals(byId, cart);
    }
}
