package labshopmonolith.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.transaction.Transactional;

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

    //private String address;

    @PostPersist // Pre|Post + Persist|Remove|Load|Update --> PreUpdate PostUpdate PostRemove
    public void onPostPersist() {
        System.out.println("Saved");
    }

    @PrePersist
    public void checkAvailability(){
        if(inventoryService().getInventory(Long.valueOf(getProductId())).getStock() < getQty()) 
            throw new RuntimeException("Out of stock");

        inventoryService().decreaseStock(Long.valueOf(getProductId()), new DecreaseStockCommand(getQty()));

    }

    public static InventoryService inventoryService(){
        InventoryService inventoryService = MonolithApplication.applicationContext.getBean(
            InventoryService.class
        );

        return inventoryService;
    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = MonolithApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }
}
