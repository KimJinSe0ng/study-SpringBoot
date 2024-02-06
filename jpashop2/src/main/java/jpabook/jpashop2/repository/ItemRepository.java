package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { //저장하기 전까지 ID값이 없다 -> 새로운 객체다 -> 신규 등록
            em.persist(item);
        } else {
            em.merge(item); //이미 값이 있네 -> DB에서 값을 가져옴 -> merge는 강제로 save 또는 update와 비슷하다고 보자.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
