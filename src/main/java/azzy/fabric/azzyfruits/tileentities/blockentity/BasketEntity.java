package azzy.fabric.azzyfruits.tileentities.blockentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.Direction;

public class BasketEntity extends MachineEntity {

    public BasketEntity(){
        inventory = DefaultedList.ofSize(21, ItemStack.EMPTY);
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
