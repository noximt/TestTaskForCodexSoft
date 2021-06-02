package by.yauheni.controller;

import by.yauheni.entity.Item;
import by.yauheni.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
@Api("Admin Controller")
public class AdminController {
    private final ItemService itemService;

    @Autowired
    public AdminController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(path = "/saveItem")
    @ApiOperation("Save item")
    public ResponseEntity<HttpStatus> saveItem(@RequestBody Item item){
        ResponseEntity<HttpStatus> response = itemService.save(item);
        return response;
    }

    @PatchMapping(path = "/updateItem")
    @ApiOperation("Update item")
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        ResponseEntity<Item> response = itemService.update(item);
        return response;
    }

    @DeleteMapping(path = "/deleteItem")
    @ApiOperation("delete item")
    public ResponseEntity<HttpStatus> deleteItem(@RequestParam String name){
        ResponseEntity<HttpStatus> response = itemService.delete(name);
        return response;
    }

    @PatchMapping(path = "/forceUpdate")
    @ApiOperation("Force update item")
    public ResponseEntity<HttpStatus> forceUpdate(@RequestBody Item item){
        ResponseEntity<HttpStatus> response = itemService.forceUpdate(item);
        return response;
    }
}
