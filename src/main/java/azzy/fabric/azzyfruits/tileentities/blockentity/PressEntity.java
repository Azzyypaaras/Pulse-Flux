package azzy.fabric.azzyfruits.tileentities.blockentity;

import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

public class PressEntity extends MachineEntity{

    public PressEntity(){
        inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        fluidInv = new SimpleFixedFluidInv(1, 8000);
    }
    
    @Override
    public void tick(){
    }
}
