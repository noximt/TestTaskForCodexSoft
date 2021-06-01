package by.yauheni.testtaskforcodexsoft.controller;

import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.entity.User;
import by.yauheni.testtaskforcodexsoft.service.CartService;
import by.yauheni.testtaskforcodexsoft.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/users")
@Api("User Controller")
public class UserController {
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public UserController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping(path = "/reg")
    @ApiOperation("User Registration")
    public ResponseEntity<HttpStatus> register(@RequestBody User user) {
        ResponseEntity<HttpStatus> response = userService.register(user);
        return response;
    }

    @GetMapping(path = "/auth")
    public ResponseEntity<String> authorize(@RequestParam String nickname, @RequestParam String password) {
        ResponseEntity<String> response = userService.authorize(nickname, password);
        return response;
    }

    @PostMapping(path = "/addToCart")
    public ResponseEntity<HttpStatus> addToCart(@RequestBody Item item, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ResponseEntity<HttpStatus> response = cartService.addToCart(item, user);
        return response;
    }

    @PostMapping(path = "/removeFromCart")
    public ResponseEntity<HttpStatus> removeFromCart(@RequestBody Item item, HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        ResponseEntity<HttpStatus> response = cartService.removeFromCart(item, user);
        return response;
    }
}
