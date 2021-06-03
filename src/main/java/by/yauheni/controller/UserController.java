package by.yauheni.controller;

import by.yauheni.entity.Cart;
import by.yauheni.entity.Item;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        ResponseEntity<HttpStatus> response = userService.register(user);
        return response;
    }

    @GetMapping(path = "/auth")
    @ApiOperation("User authorization")
    public ResponseEntity<String> authorize(@RequestParam String nickname, @RequestParam String password) {
        ResponseEntity<String> response = userService.authorize(nickname, password);
        return response;
    }

    @PostMapping(path = "/addToCart")
    @ApiOperation("Add item to cart")
    public ResponseEntity<HttpStatus> addToCart(@RequestBody Item item, WebRequest webRequest) {
        User user = (User) webRequest.getAttribute("user", 0);
        ResponseEntity<HttpStatus> response = cartService.addToCart(item, user);
        return response;
    }

    @PostMapping(path = "/removeFromCart")
    @ApiOperation("Remove item from cart")
    public ResponseEntity<HttpStatus> removeFromCart(@RequestBody Item item, WebRequest webRequest) {
        User user = (User) webRequest.getAttribute("user", 0);
        ResponseEntity<HttpStatus> response = cartService.removeFromCart(item, user);
        return response;
    }

    @PostMapping("/buy")
    @ApiOperation("Buy item")
    public ResponseEntity<HttpStatus> buy(@RequestBody Cart cart) {
        ResponseEntity<HttpStatus> response = userService.buy(cart);
        return response;
    }

    @PostMapping(path = "/search")
    @ApiOperation("Search for item")
    public ResponseEntity<List<Item>> search(@RequestBody Item item) {
        ResponseEntity<List<Item>> search = itemService.search(item);
        return search;
    }
}
