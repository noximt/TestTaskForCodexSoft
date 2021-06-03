package by.yauheni.repository;

import by.yauheni.entity.Type;
import by.yauheni.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTests {
    private User user;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init(){
        this.user = new User(Type.USER,"nickname", "password", "e-mail");
    }

    @Test
    void saveUserTest(){
        User savedUser = userRepository.save(user);
        assertEquals(this.user, savedUser);
        assertEquals(this.user.getNickname(), savedUser.getNickname());
        assertEquals(this.user.getPassword(), savedUser.getPassword());
        assertEquals(this.user.getType(), savedUser.getType());
        assertEquals(this.user.getEmail(), savedUser.getEmail());
    }
    
    @Test
    void existsByIdTest(){
        userRepository.save(this.user);
        boolean isExist = userRepository.existsById(this.user.getNickname());
        assertTrue(isExist);
    }

    @Test
    void getByIdTest(){
        userRepository.save(this.user);
        User byId = userRepository.getById(this.user.getNickname());
        assertNotNull(byId);
        assertEquals(this.user, byId);
    }
}
