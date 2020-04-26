package azzy.fabric.azzyfruits.tileentities.blockentity;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.Direction;

import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.BASKET_ENTITY;

public class BasketEntity extends MachineEntity {

    public BasketEntity(){
        super(BASKET_ENTITY);
        inventory = DefaultedList.ofSize(18, ItemStack.EMPTY);
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public void setFromItem(CompoundTag data){
        Inventories.fromTag(data, inventory);
        this.markDirty();
    }
}
