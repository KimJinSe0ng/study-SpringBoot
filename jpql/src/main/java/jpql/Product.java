package jpql;

import javax.persistence.*;

@Entity
public class Product {

    @Id @GeneratedValue
    private Long id;
    private int orderAmount;
    @Embedded
    private Address address;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}