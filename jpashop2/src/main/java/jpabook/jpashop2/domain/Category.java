package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import jpabook.jpashop2.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item", //중간 테이블 매핑
            joinColumns = @JoinColumn(name = "category_id"), //중간 테이블에 있는 카테고리 ID
            inverseJoinColumns = @JoinColumn(name = "item_id")) //category_item 테이블에 item 쪽으로 가는 것
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
