package by.yauheni.testtaskforcodexsoft.service;

import by.yauheni.testtaskforcodexsoft.entity.Cart;
import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.entity.Token;
import by.yauheni.testtaskforcodexsoft.entity.User;
import by.yauheni.testtaskforcodexsoft.repository.CartRepository;
import by.yauheni.testtaskforcodexsoft.repository.TokenRepository;
import by.yauheni.testtaskforcodexsoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final CartRepository cartRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository, CartRepository cartRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.cartRepository = cartRepository;
        this.mailSender = mailSender;
    }

    @ResponseBody
    public ResponseEntity<HttpStatus> register(User user) {
        if (userRepository.existsById(user.getNickname())) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> authorize(String nickname, String password) {
        if (userRepository.existsById(nickname)) {
            User userFromDB = userRepository.getById(nickname);
            if (password.equals(userFromDB.getPassword())){
                UUID uuid = UUID.randomUUID();
                String tokenId = uuid.toString();
                User byId = userRepository.getById(nickname);
                tokenRepository.save(new Token(tokenId, byId));
                return new ResponseEntity<>(tokenId,HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<HttpStatus> buy(Cart cart){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cart.getUser().getEmail());
        message.setSubject("Purchase");
        StringBuilder builder = createListOfItemsForMsg(cart);
        message.setText(builder.toString());
        this.mailSender.send(message);
        return clearCart(cart);
    }

    private ResponseEntity<HttpStatus> clearCart(Cart cart) {
        cartRepository.delete(cart);
        if (cartRepository.existsById(cart.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }else{
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    private StringBuilder createListOfItemsForMsg(Cart cart) {
        StringBuilder builder = new StringBuilder("Hello dear customer! You've just purchased: ");
        List<Item> items = cart.getItems();
        for (int i = 0; i < items.size(); i++) {
            builder.append(items.get(i).getName());
            if (i + 1 != items.size()){
                builder.append(", ");
            }
        }
        return builder;
    }
}
