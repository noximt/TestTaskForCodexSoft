package by.yauheni.repository;

import by.yauheni.entity.Item;
import by.yauheni.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByTagsContains(Tag tag);
    Item findByDescription(String description);
    boolean existsByDescription(String description);
}
