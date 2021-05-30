package by.yauheni.testtaskforcodexsoft.repository;

import by.yauheni.testtaskforcodexsoft.entity.Item;
import by.yauheni.testtaskforcodexsoft.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    Item findByTagsContains(Tag tag);
    List<Item> findByDescription(String description);
}
