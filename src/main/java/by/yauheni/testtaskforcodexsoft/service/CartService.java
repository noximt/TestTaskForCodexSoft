package by.yauheni.testtaskforcodexsoft.service;

import by.yauheni.testtaskforcodexsoft.entity.Cart;
import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.entity.User;
import by.yauheni.testtaskforcodexsoft.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
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
        Cart cart;
        List<Item> items;
        if (cartRepository.existsByUser(user)) {
            cart = cartRepository.findByUser(user);
            items = cart.getItems();
            for (Item itm : items) {
                if (itm.equals(item)){
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
            }
        }else{
            cart = new Cart();
            items = new ArrayList<>();
            cart.setItems(items);
            cart.setUser(user);
        }
        addToItemsInCart(item, cart, items);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private void addToItemsInCart(Item item, Cart cart, List<Item> items) {
        items.add(item);
        cart.setItems(items);
        cartRepository.save(cart);
    }
}
