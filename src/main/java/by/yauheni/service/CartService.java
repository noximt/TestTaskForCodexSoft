package by.yauheni.service;

import by.yauheni.entity.Item;
import by.yauheni.entity.Cart;
import by.yauheni.entity.User;
import by.yauheni.repository.CartRepository;
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

    public boolean removeFromCart(Item item, User user) {
        if (cartRepository.existsByUser(user)) {
            Cart cart = cartRepository.findByUser(user);
            List<Item> items = cart.getItems();
            if (items.remove(item)) {
                cart.setItems(items);
                cartRepository.save(cart);
                return true;
            }
        }
        return false;
    }

    public boolean addToCart(Item item, User user) {
        Cart cart;
        List<Item> items;
        if (cartRepository.existsByUser(user)) {
            cart = cartRepository.findByUser(user);
            items = cart.getItems();
            for (Item itm : items) {
                if (itm.equals(item)){
                    return false;
                }
            }
        }else{
            cart = new Cart();
            items = new ArrayList<>();
            cart.setItems(items);
            cart.setUser(user);
        }
        addToItemsInCart(item, cart, items);
        return true;
    }

    private void addToItemsInCart(Item item, Cart cart, List<Item> items) {
        items.add(item);
        cart.setItems(items);
        cartRepository.save(cart);
    }
}
