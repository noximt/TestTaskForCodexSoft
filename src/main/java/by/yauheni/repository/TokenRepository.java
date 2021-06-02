package by.yauheni.repository;

import by.yauheni.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
    boolean existsById(String id);

}
