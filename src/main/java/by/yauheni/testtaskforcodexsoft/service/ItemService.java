package by.yauheni.testtaskforcodexsoft.service;

import by.yauheni.testtaskforcodexsoft.entity.Cart;
import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.entity.Tag;
import by.yauheni.testtaskforcodexsoft.entity.User;
import by.yauheni.testtaskforcodexsoft.repository.CartRepository;
import by.yauheni.testtaskforcodexsoft.repository.ItemRepository;
import by.yauheni.testtaskforcodexsoft.repository.TagRepository;
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

    public ResponseEntity<HttpStatus> forceUpdate(Item item) {
        if (itemRepository.existsById(item.getName())) {
            itemRepository.save(item);
            SimpleMailMessage message = new SimpleMailMessage();
            List<Cart> byItemsContains = cartRepository.findByItemsContains(item);
            for (Cart cart : byItemsContains) {
                User user = cart.getUser();
                message.setTo(user.getEmail());
            }
            message.setSubject("Notification");
            message.setText("The item: " + item.getName() + ", which was in your cart was changed by admin");
            this.mailSender.send(message);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
