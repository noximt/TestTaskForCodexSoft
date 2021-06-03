package by.yauheni.repository;

import by.yauheni.entity.Item;
import by.yauheni.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ItemRepositoryTests {
    private Item item;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void init(){
        this.item = new Item("Drill", "Some description", null);
    }

    @Test
    void saveItemTest() {
        Item savedItem = itemRepository.save(item);
        assertNotNull(savedItem);
        assertEquals(item, savedItem);
    }

    @Test
    void existsByIdTest(){
        Item save = itemRepository.save(this.item);
        assertTrue(itemRepository.existsById(save.getName()));
    }

    @Test
    void getByIdTest(){
        itemRepository.save(this.item);
        Item byId = itemRepository.getById(this.item.getName());
        assertNotNull(byId);
        assertEquals(this.item, byId);
    }

//    @Test
//    void findByTagTest(){
//        Tag tag = new Tag(1, "tool");
//        tagRepository.save(tag);
//        List<Tag> tags = new ArrayList<>();
//        tags.add(tag);
//        this.item.setTags(tags);
//        itemRepository.save(this.item);
//        Item byTagsContains = itemRepository.findByTagsContains(tag);
//        assertEquals(this.item, byTagsContains);
//    }

    @Test
    void findByDescriptionTest(){
        itemRepository.save(this.item);
        Item some_description = itemRepository.findByDescription("Some description");
        assertEquals(this.item, some_description);
    }
}