package by.yauheni.service;

import by.yauheni.entity.Cart;
import by.yauheni.entity.Item;
import by.yauheni.entity.Tag;
import by.yauheni.entity.User;
import by.yauheni.repository.CartRepository;
import by.yauheni.repository.ItemRepository;
import by.yauheni.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final JavaMailSender mailSender;
    private final TagRepository tagRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CartRepository cartRepository, JavaMailSender mailSender, TagRepository tagRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.mailSender = mailSender;
        this.tagRepository = tagRepository;
    }

    public ResponseEntity<HttpStatus> save(Item item) {
        saveTags(item);
        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private void saveTags(Item item) {
        List<Tag> tags = item.getTags();
        for (Tag tag : tags) {
            if (!tagRepository.existsById(tag.getId())) {
                tagRepository.save(tag);
            }
        }
    }

    public boolean update(Item item) {
        if (itemRepository.existsById(item.getName())) {
            if (cartRepository.existsByItemsContains(item)) {
                return false;
            } else {
                saveTags(item);
                itemRepository.save(item);
                return true;
            }
        }else {
            return false;
        }
    }

    public boolean forceUpdate(Item item) {
        if (itemRepository.existsById(item.getName())) {
            saveTags(item);
            itemRepository.save(item);
            List<Cart> byItemsContains = cartRepository.findByItemsContains(item);
            for (Cart cart : byItemsContains) {
                User user = cart.getUser();
                sendMessage(item, user);
            }
            return true;
        } else {
            return false;
        }
    }

    private void sendMessage(Item item, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Notification");
        message.setText("The item: " + item.getName() + "was changed by admin");
        this.mailSender.send(message);
    }

    public boolean delete(String name) {
        if (itemRepository.existsById(name)) {
            itemRepository.deleteById(name);
            return true;
        }
        return false;
    }

    public Optional<Item> searchByDescription(String description) {
        Optional<Item> byDescription = Optional.of(itemRepository.findByDescription(description));
        return byDescription;
    }

    public List<Item> searchByTags(List<Tag> tags) {
        List<Item> items = new ArrayList<>();
        for (Tag tag : tags) {
            List<Item> byTagsContains = itemRepository.findByTagsContains(tag);
            items.addAll(byTagsContains);
        }
        return items;
    }
}
