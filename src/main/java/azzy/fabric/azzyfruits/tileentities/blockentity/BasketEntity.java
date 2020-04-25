package azzy.fabric.azzyfruits.tileentities.blockentity;

import azzy.fabric.azzyfruits.util.container.BasketContainer;
import azzy.fabric.azzyfruits.util.container.GenericContainer;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.Direction;

import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.BASKET_ENTITY;
import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.PRESS_ENTITY;

public class BasketEntity extends MachineEntity {

    public BasketEntity(){
        super(BASKET_ENTITY);
        inventory = DefaultedList.ofSize(21, ItemStack.EMPTY);
    }

    @Override
    protected Container createContainer(int syncId, PlayerInventory playerInventory) {
        return new BasketContainer(syncId, playerInventory, this);
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction direction) {
        return true;
    }
}
