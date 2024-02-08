package jpabook.jpashop2.api;

import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.repository.OrderRepository;
import jpabook.jpashop2.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne(ManyToOne, OneToOne) 에서 성능 최적화를 어떻게 하는지 보자.
 * Order
 * Order -> Member @ManyToOne
 * Order -> Delivery @OneToOne
 * ToMany 관계는 컬렉션이다.
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리 * - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch()); //주문 조건이 없어서 다 들고옴
        //양방향 연관관계 문제로 인해 무한 루프에 빠짐 -> 양방향이 걸리는데를 다 @JsonIgnore 걸어줘야 함, 그래야 반대쪽에 안 함
        for (Order order : all) {
            order.getMember().getName(); //getMember()까지는 프록시, getName()하면 Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기화
        }
        return all;
    }
}
