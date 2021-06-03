package by.yauheni.repository;

import by.yauheni.entity.Item;
import by.yauheni.entity.Cart;
import by.yauheni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
    boolean existsByUser(User user);
    boolean existsByItemsContains(Item item);
    List<Cart> findByItemsContains(Item item);
}
