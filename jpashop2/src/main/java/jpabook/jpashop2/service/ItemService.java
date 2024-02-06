package jpabook.jpashop2.service;

import jpabook.jpashop2.item.Book;
import jpabook.jpashop2.item.Item;
import jpabook.jpashop2.repository.ItemRepository;
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
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) { //변경 감지 기능 사용, merge의 코드 동작 방식은 아래와 거의 똑같음
        Item findItem = itemRepository.findOne(itemId); //아이템을 찾으면, id를 기반으로 실제 DB에 있는 영속 상태 엔티티를 찾아옴
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
//        itemRepository.save(findItem); //따로 persist 할 필요 없음. 이미 영속 상태이기 때문에 @Transactional에 의해 커밋이 되면서 플러시가 됨
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
