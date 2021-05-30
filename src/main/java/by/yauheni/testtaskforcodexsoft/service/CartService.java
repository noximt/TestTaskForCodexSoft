package by.yauheni.testtaskforcodexsoft.service;

import by.yauheni.testtaskforcodexsoft.entity.Cart;
import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.entity.User;
import by.yauheni.testtaskforcodexsoft.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public boolean existsInCart(Item item){
        return cartRepository.existsByItemsContains(item);
    }

    public ResponseEntity<HttpStatus> removeFromCart(Item item, User user) {
        if (cartRepository.existsByUser(user)) {
            Cart cart = cartRepository.findByUser(user);
            List<Item> items = cart.getItems();
            if (items.remove(item)) {
                cart.setItems(items);
                cartRepository.save(cart);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<HttpStatus> addToCart(Item item, User user) {
        Cart cart = getCartOrCreateNew(user);
        if (setItemInCart(item, cart)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private boolean setItemInCart(Item item, Cart cart) {
        List<Item> items = cart.getItems();
        if (isExist(item, items)) {
            return false;
        }
        items.add(item);
        cart.setItems(items);
        cartRepository.save(cart);
        return true;
    }

    private boolean isExist(Item item, List<Item> items) {
        for (Item item1 : items) {
            if (item1.equals(item)) {
                return true;
            }
        }
        return false;
    }

    private Cart getCartOrCreateNew(User user) {
        Cart cart;
        if (cartRepository.existsByUser(user)) {
            cart = cartRepository.findByUser(user);
        } else {
            cart = new Cart(user);
        }
        return cart;
    }
}
