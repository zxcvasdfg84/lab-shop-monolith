package labshopmonolith.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import labshopmonolith.MonolithApplication;
import lombok.Data;

@Entity
@Table(name = "Order_table")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;

    private Integer qty;

    private String customerId;

    private Double amount;

    @PostPersist
    public void onPostPersist() {
        inventoryService().decreaseStock(Long.valueOf(getProductId()), new DecreaseStockCommand(getQty()));

    }

    @PrePersist
    public void checkAvailability(){
        if(inventoryService().getInventory(Long.valueOf(getProductId())).getStock() < getQty()) throw new RuntimeException("Out of stock");
    }

    public static InventoryService inventoryService(){
        return MonolithApplication.applicationContext.getBean(
            InventoryService.class
        );
    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = MonolithApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }
}
