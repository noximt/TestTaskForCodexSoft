package by.yauheni.testtaskforcodexsoft.service;

import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.entity.Tag;
import by.yauheni.testtaskforcodexsoft.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CartService cartService;

    @Autowired
    public ItemService(ItemRepository itemRepository, CartService cartService) {
        this.itemRepository = itemRepository;
        this.cartService = cartService;
    }

    public ResponseEntity<HttpStatus> save(Item item) {
        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Item> update(Item item){
        if (cartService.existsInCart(item)){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }else{
            itemRepository.save(item);
            return new ResponseEntity<>(item, HttpStatus.NOT_ACCEPTABLE);
        }
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
