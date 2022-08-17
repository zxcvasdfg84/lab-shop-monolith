package labshopmonolith.domain;


public interface InventoryService {
    public Inventory getInventory(Long id);
    public void decreaseStock(Long id, DecreaseStockCommand decreaseStockCommand);

}
