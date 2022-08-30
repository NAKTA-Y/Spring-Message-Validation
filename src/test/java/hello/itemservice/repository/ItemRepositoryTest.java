package hello.itemservice.repository;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository = ItemRepository.getInstance();

    @AfterEach
    public void afterEach() {
        itemRepository.clear();
    }

    @Test
    public void save() {

        // given
        Item item1 = new Item("item1", 1000, 5);
        Item item2 = new Item("item2", 1000, 5);
        Item item3 = new Item("item3", 1000, 5);
        Item item4 = new Item("item4", 1000, 5);

        // when
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);

        // then
        assertThat(itemRepository.size()).isEqualTo(4);
        assertThat(itemRepository.findOne(1L)).isSameAs(item1);
    }

    @Test
    public void findAll() {

        //given
        Item item1 = new Item("item1", 1000, 5);
        Item item2 = new Item("item2", 1000, 5);
        Item item3 = new Item("item3", 1000, 5);
        Item item4 = new Item("item4", 1000, 5);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);

        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        assertThat(itemList.size()).isEqualTo(4);
        assertThat(itemList.get(0)).isEqualTo(item1);
        assertThat(itemList.get(1)).isEqualTo(item2);
        assertThat(itemList.get(2)).isEqualTo(item3);
        assertThat(itemList.get(3)).isEqualTo(item4);
    }

}