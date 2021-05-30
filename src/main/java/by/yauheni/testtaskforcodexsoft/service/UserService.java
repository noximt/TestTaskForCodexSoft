package by.yauheni.testtaskforcodexsoft.service;

import by.yauheni.testtaskforcodexsoft.entity.Token;
import by.yauheni.testtaskforcodexsoft.entity.User;
import by.yauheni.testtaskforcodexsoft.repository.TokenRepository;
import by.yauheni.testtaskforcodexsoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

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
}
