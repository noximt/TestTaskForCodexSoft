package by.yauheni.repository;

import by.yauheni.entity.Cart;
import by.yauheni.entity.Type;
import by.yauheni.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CartRepositoryTests {
    private User user;
    private Cart cart;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        this.user = new User(Type.USER, "nickname", "password", "e-mail");
        this.cart = new Cart(this.user, null);
    }

    @Test
    void autoIncrementIdTest() {
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        Cart savedCart1 = cartRepository.save(cart1);
        Cart savedCart2 = cartRepository.save(cart2);
        assertEquals(1, savedCart1.getId());
        assertEquals(2, savedCart2.getId());
    }

    @Test
    void existByUserTest() {
        userRepository.save(this.user);
        cartRepository.save(this.cart);
        boolean isExists = cartRepository.existsByUser(this.user);
        assertTrue(isExists);
    }

    @Test
    void findByUserTest() {
        User savedUser = userRepository.save(this.user);
        cartRepository.save(this.cart);
        Cart byUser = cartRepository.findByUser(savedUser);
        assertNotNull(byUser);
    }
}
