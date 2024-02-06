package jpabook.jpashop2.controller;

import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.item.Item;
import jpabook.jpashop2.service.ItemService;
import jpabook.jpashop2.service.MemberService;
import jpabook.jpashop2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count); //command성, 주문 등은 외부에서 컨트롤러 레벨에서는 식별자만 넘기고, 실제 핵심 비즈니스 서비스에서 엔티티를 찾는 것 부터 하게 개발함
        //핵심 비즈니스 로직을 트랜잭션 안에서 하게 되면 영속성 컨텍스트가 있는 상태에서 조회할 수 있어서 더티 체킹하며 값을 바꿔줘도 자연스럽게 적용이 됨
        return "redirect:/orders";
    }
}
