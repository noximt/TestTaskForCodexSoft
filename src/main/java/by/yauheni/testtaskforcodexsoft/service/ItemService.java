package by.yauheni.testtaskforcodexsoft.service;

import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.entity.Tag;
import by.yauheni.testtaskforcodexsoft.repository.CartRepository;
import by.yauheni.testtaskforcodexsoft.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CartRepository cartService) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartService;
    }

    public ResponseEntity<HttpStatus> save(Item item) {
        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Item> update(Item item){
        if (cartRepository.existsByItemsContains(item)){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }else{
            itemRepository.save(item);
            return new ResponseEntity<>(item, HttpStatus.NOT_ACCEPTABLE);
        }
    }


    public ResponseEntity<HttpStatus> forceUpdate(Item item){
        if (itemRepository.existsById(item.getName())) {
            itemRepository.save(item);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> delete(String name){
        if (itemRepository.existsById(name)){
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
