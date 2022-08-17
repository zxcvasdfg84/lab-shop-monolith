package labshopmonolith.domain;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    public Inventory getInventory(Long id) {
        // TODO Auto-generated method stub
        return inventoryRepository.findById(id).get();
    }

    @Override
    public void decreaseStock(Long id, DecreaseStockCommand decreaseStockCommand) {
        // TODO Auto-generated method stub
        inventoryRepository.findById(id).ifPresent(inventory ->{
            inventory.decreaseStock(decreaseStockCommand);
            inventoryRepository.save(inventory);
        });
    }
    
}
