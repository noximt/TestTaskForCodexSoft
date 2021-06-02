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

import java.util.List;

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
            if (!tagRepository.existsById(tag.getId())){
                tagRepository.save(tag);
            }
        }
    }

    public ResponseEntity<Item> update(Item item) {
        if (cartRepository.existsByItemsContains(item)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            itemRepository.save(item);
            return new ResponseEntity<>(item, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<HttpStatus> forceUpdate(Item item) {
        if (itemRepository.existsById(item.getName())) {
            itemRepository.save(item);

            List<Cart> byItemsContains = cartRepository.findByItemsContains(item);
            for (Cart cart : byItemsContains) {
                User user = cart.getUser();
                sendMessage(item, user);
            }

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void sendMessage(Item item, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Notification");
        message.setText("The item: " + item.getName() + ", which was in your cart was changed by admin");
        this.mailSender.send(message);
    }

    public ResponseEntity<HttpStatus> delete(String name) {
        if (itemRepository.existsById(name)) {
            Item byId = itemRepository.getById(name);
            itemRepository.delete(byId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Item>> search(Item item) {
        String description = item.getDescription();
        List<Item> byDescription = itemRepository.findByDescription(description);
        List<Tag> tags = item.getTags();
        for (Tag tag : tags) {
            Item byTagsContains = itemRepository.findByTagsContains(tag);
            byDescription.add(byTagsContains);
        }
        return new ResponseEntity<>(byDescription, HttpStatus.ACCEPTED);
    }
}
