package by.yauheni.testtaskforcodexsoft.repository;

import by.yauheni.testtaskforcodexsoft.entity.Cart;
import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
    boolean existsByUser(User user);
    boolean existsByItemsContains(Item item);
    Cart findByItemsContains(Item item);
}
