package by.yauheni.testtaskforcodexsoft.controller;

import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private final ItemService itemService;

    @Autowired
    public AdminController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(path = "/saveItem")
    public ResponseEntity<HttpStatus> saveItem(@RequestBody Item item){
        ResponseEntity<HttpStatus> response = itemService.save(item);
        return response;
    }

    @PatchMapping(path = "/updateItem")
    public ResponseEntity<HttpStatus> updateItem(@RequestBody Item item){
        ResponseEntity<HttpStatus> response = itemService.save(item);
        return response;
    }

    @DeleteMapping(path = "/deleteItem")
    public ResponseEntity<HttpStatus> deleteItem(@RequestParam String name){
        ResponseEntity<HttpStatus> response = itemService.delete(name);
        return response;
    }

    @PatchMapping(path = "/forceUpdate")
    public ResponseEntity<HttpStatus> forceUpdate(@RequestBody Item item){
        ResponseEntity<HttpStatus> response = itemService.forceUpdate(item);
        return response;
    }
}
