package by.yauheni.controller;

import by.yauheni.entity.Cart;
import by.yauheni.entity.Item;
import by.yauheni.entity.Tag;
import by.yauheni.entity.User;
import by.yauheni.service.CartService;
import by.yauheni.service.ItemService;
import by.yauheni.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
@Api("User Controller")
public class UserController {
    private final UserService userService;
    private final CartService cartService;
    private final ItemService itemService;

    @Autowired
    public UserController(UserService userService, CartService cartService, ItemService itemService) {
        this.userService = userService;
        this.cartService = cartService;
        this.itemService = itemService;
    }

    @PostMapping(path = "/reg")
    @ApiOperation("User registration")
    public ResponseEntity<HttpStatus> register(@RequestBody User user) {
        boolean bool = userService.register(user);
        if (bool) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "/auth")
    @ApiOperation("User authorization")
    public ResponseEntity<String> authorize(@RequestParam String nickname, @RequestParam String password) {
        String authorize = userService.authorize(nickname, password);
        if (authorize.equals("Wrong data")) {
            return new ResponseEntity<>(authorize, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(authorize, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/addToCart")
    @ApiOperation("Add item to cart")
    public ResponseEntity<HttpStatus> addToCart(@RequestBody Item item, WebRequest webRequest) {
        User user = (User) webRequest.getAttribute("user", 0);
        boolean bool = cartService.addToCart(item, user);
        if (bool) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping(path = "/removeFromCart")
    @ApiOperation("Remove item from cart")
    public ResponseEntity<HttpStatus> removeFromCart(@RequestBody Item item, WebRequest webRequest) {
        User user = (User) webRequest.getAttribute("user", 0);
        boolean bool = cartService.removeFromCart(item, user);
        if (bool) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/buy")
    @ApiOperation("Buy item")
    public ResponseEntity<HttpStatus> buy(@RequestBody Cart cart) {
        boolean bool = userService.buy(cart);
        if (bool) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "/searchByDescription")
    @ApiOperation("Search for item by description")
    public ResponseEntity<Item> searchByDescription(@RequestParam String description) {
        Optional<Item> item1 = itemService.searchByDescription(description);
        if (item1.isPresent()) {
            Item item = item1.get();
            return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/searchByTags")
    @ApiOperation("Search for item by tags")
    public ResponseEntity<List<Item>> searchByTags(@RequestBody List<Tag> tags) {
        List<Item> items = itemService.searchByTags(tags);
        return new ResponseEntity<>(items, HttpStatus.ACCEPTED);
    }
}
