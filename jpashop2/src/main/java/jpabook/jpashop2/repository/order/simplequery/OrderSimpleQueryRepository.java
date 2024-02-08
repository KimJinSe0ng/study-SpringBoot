package jpabook.jpashop2.repository.order.simplequery;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;
    public List<OrderSimpleQueryDto> findOrderDtos() { //3번과 4번의 차이는 API 스펙이 바뀌면 다 뜯어 고쳐야 함
        //트레이드 오프 : 코드는 v3이 단순하나, v4가 성능이 좋음. 성능은 Join이나 Where에서 인덱스를 적용하는데 성능 차이가 있고 조회 컬럼 수는 대체로 차이가 없음.
        return em.createQuery(
                "select new jpabook.jpashop2.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        "from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }
}
