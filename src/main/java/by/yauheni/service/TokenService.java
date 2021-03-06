package by.yauheni.service;

import by.yauheni.entity.Token;
import by.yauheni.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean tokenContains(String uuid){
        if (tokenRepository.existsById(uuid)) {
            return true;
        }
        return false;
    }

    public Token getByUUID(String uuid){
        Token byId = tokenRepository.getById(uuid);
        return byId;
    }
}
