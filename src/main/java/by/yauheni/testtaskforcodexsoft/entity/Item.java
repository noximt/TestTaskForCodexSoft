package by.yauheni.testtaskforcodexsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private String name;
    private String description;
    @ElementCollection(targetClass = Tag.class)
    private List<Tag> tags;
}
