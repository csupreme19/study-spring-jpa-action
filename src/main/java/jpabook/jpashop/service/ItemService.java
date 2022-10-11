package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long addItem(Item item) {
        return itemRepository.save(item);
    }

    // 변경감지로 수정
    @Transactional
    public void updateItem(Long itemId, String name, int price, String isbn, String author, int stockQuantity) {
        Book findItem = (Book) itemRepository.findById(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setIsbn(isbn);
        findItem.setAuthor(author);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findItem(Long id) {
        return itemRepository.findById(id);
    }

}
