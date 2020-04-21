package azzy.fabric.azzyfruits.TileEntities.BlockEntity;

import azzy.fabric.azzyfruits.util.InventoryWrapper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.*;

public class MachineEntity extends BlockEntity implements Tickable, InventoryWrapper, SidedInventory {

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(64, ItemStack.EMPTY);

    public MachineEntity(){
        super(PRESS_ENTITY);
    }

    @Override
    public void tick(){
    }

    @Override
    public int[] getInvAvailableSlots(Direction var1) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, items);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag, items);
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.UP;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN;
    }
}
