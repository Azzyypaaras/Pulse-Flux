package azzy.fabric.azzyfruits.util.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

public class BasketContainer extends GenericContainer {

    public BasketContainer(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(syncId, playerInventory, inventory);
    }

    @Override
    public boolean canUse (PlayerEntity player){
        return true;
    }
}
