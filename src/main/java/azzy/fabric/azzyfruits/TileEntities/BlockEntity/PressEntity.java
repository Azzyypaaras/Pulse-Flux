package azzy.fabric.azzyfruits.TileEntities.BlockEntity;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

public class PressEntity extends MachineEntity{

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    @Override
    public void tick(){
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, inventory);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        Inventories.fromTag(tag, inventory);
        super.fromTag(tag);
    }
}
