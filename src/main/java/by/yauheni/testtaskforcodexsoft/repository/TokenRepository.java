package by.yauheni.testtaskforcodexsoft.repository;

import by.yauheni.testtaskforcodexsoft.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
    boolean existsById(String id);

}
