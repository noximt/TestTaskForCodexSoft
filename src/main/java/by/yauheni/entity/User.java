package by.yauheni.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Enumerated(EnumType.STRING)
    private Type type;
    @Id
    private String nickname;
    private String password;
    private String email;
}
