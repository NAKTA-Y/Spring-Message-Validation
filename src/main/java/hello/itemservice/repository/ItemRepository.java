package hello.itemservice.repository;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemRepository {

    private Map<Long, Item> store = new HashMap<>();
    private long sequence = 0L;

    private static final ItemRepository instance = new ItemRepository();

    public static ItemRepository getInstance() {
        return instance;
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void save(Item item) {

        item.setId(++sequence);

        store.put(item.getId(), item);
    }

    public Item findOne(Long itemId) {

        return store.get(itemId);
    }

    public void modify(Item updateItem) {

        store.replace(updateItem.getId(), updateItem);
    }

    public void delete(Long itemId) {

        store.remove(itemId);
    }

    public int size() {

        return store.size();
    }

    public void clear() {
        store.clear();
    }
}