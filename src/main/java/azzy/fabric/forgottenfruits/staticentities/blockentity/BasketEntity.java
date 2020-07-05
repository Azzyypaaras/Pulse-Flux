package azzy.fabric.forgottenfruits.staticentities.blockentity;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

import static azzy.fabric.forgottenfruits.registry.BlockEntityRegistry.BASKET;

public class BasketEntity extends MachineEntity {

    public BasketEntity() {
        super(BASKET);
        inventory = DefaultedList.ofSize(18, ItemStack.EMPTY);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public void setFromItem(CompoundTag data) {
        Inventories.fromTag(data, inventory);
        this.markDirty();
    }
}
