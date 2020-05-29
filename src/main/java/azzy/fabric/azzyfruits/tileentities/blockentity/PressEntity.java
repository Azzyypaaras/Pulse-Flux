package azzy.fabric.azzyfruits.tileentities.blockentity;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.util.fluids.FluidInventory;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.PRESS_ENTITY;

public class PressEntity extends MachineEntity implements PropertyDelegateHolder {

    public PressEntity(){
        super(PRESS_ENTITY);
        inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        fluidInv = new SimpleFixedFluidInv(1, new FluidAmount(8));
    }
    
    @Override
    public void tick(){
        super.tick();
        progress++;
        if(progress == 200)
            progress = 0;
    }

    PropertyDelegate tankHolder = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch(index){
                case 0:
                    return fluidInv.getTank(0).get().getAmount_F().asInt(1);
                case 1:
                    return fluidInv.getTank(0).getMaxAmount_F().asInt(1);
                case 2:
                    return progress;
                case 3:
                    return 200;
                case 4:
                    return Registry.FLUID.getRawId(fluidInv.getTank(0).get().getRawFluid());
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch(index) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return tankHolder;
    }
}
