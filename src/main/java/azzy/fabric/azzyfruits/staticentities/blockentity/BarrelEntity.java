package azzy.fabric.azzyfruits.staticentities.blockentity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.azzyfruits.util.tracker.FermentationTracker;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.BARREL_ENTITY;

public class BarrelEntity extends MachineEntity implements PropertyDelegateHolder {

    private FermentationTracker tracker;
    private int loop;

    public BarrelEntity(){
        super(BARREL_ENTITY);
        inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        fluidInv = new SimpleFixedFluidInv(1, new FluidAmount(32));
        tracker = FermentationTracker.initiate(this);
    }

    @Override
    public void tick() {
        super.tick();
        tracker.tick();
        if(!fluidInv.getTank(0).get().isEmpty()){

        }
    }

    public void lookUp(){

    }

    private PropertyDelegate renderBuffer = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch(index){
                case 0: return fluidInv.getTank(0).get().getAmount_F().asInt(1);
                case 1: return fluidInv.getMaxAmount_F(0).asInt(1);
                case 2: return tracker.time;
                case 3: return tracker.minTime;
                case 4: return loop;
                case 5: return 20;
                case 6: return Registry.FLUID.getRawId(fluidInv.getTank(0).get().getRawFluid());
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int size() {
            return 6;
        }
    };

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return renderBuffer;
    }
}
