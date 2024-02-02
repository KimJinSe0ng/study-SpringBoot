package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) { //Model model 지워도 됨

        //@ModelAttribute 기능 1: 아래 4줄 자동으로 만들어줌
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);

        itemRepository.save(item);

        //@ModelAttribute 기능 2: 자동으로 모델에 넣어주는 역할도 함, @ModelAttribute("item") 지정한 이름(item)을 갖고 넣어줌
//        model.addAttribute("item", item); //@ModelAttribute 기능 2: 자동 추가, 생략 가능

        return "basic/item";
    }

    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     */

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model) { //1-1. @ModelAttribute 뒤에 클래스명(Item) 없으면

//        클래스명이 Hellodata 일 경우 -> hellodata //1-2. hellodata가 ModelAttribute에 담김, 첫글자를 소문자로 바꿔서 넣어줌
//        Item -> item
        itemRepository.save(item);
//        model.addAttribute("item", item); // @ModelAttribute 기능 2: 자동 추가, 생략 가능

        return "basic/item";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     */
//    @PostMapping("/add")
    public String addItemV4(Item item) {
        //1-1. @ModelAttribute 생략하면, String이나 단순 기본 타입이 오면 @RequestParam이 자동 적용
        //1-2. 우리가 만든 임의의 객체면 @ModelAttribute가 적용됨
        //V3을 좀 더 선호함
//        Item -> item
        itemRepository.save(item);

        return "basic/item";
    }

    /**
     * PRG - Post/Redirect/Get
     */
    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId(); //item.getId() 이게 문제가 될 수 있음 -> 항상 인코딩해서 넣어줘야 함
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        //리다이렉트 X : /basic/items/1/edit
        //리다이렉트 O : /basic/items/1
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
