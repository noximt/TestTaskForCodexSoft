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
    public ResponseEntity<HttpStatus> updateItem(@RequestBody Item item){
        boolean bool = itemService.update(item);
        if (bool){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(path = "/deleteItem")
    @ApiOperation("Delete item")
    public ResponseEntity<HttpStatus> deleteItem(@RequestParam String name){
        boolean bool = itemService.delete(name);
        if (bool){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(path = "/forceUpdate")
    @ApiOperation("Force update item")
    public ResponseEntity<HttpStatus> forceUpdate(@RequestBody Item item){
        boolean bool = itemService.forceUpdate(item);
        if (bool){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
