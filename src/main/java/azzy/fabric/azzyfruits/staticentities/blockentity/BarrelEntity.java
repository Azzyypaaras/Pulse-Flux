package azzy.fabric.azzyfruits.staticentities.blockentity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.azzyfruits.util.tracker.FermentationTracker;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.BARREL_ENTITY;

public class BarrelEntity extends MachineEntity implements PropertyDelegateHolder {

    private FermentationTracker tracker;

    public BarrelEntity(){
        super(BARREL_ENTITY);
        inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        fluidInv = new SimpleFixedFluidInv(1, new FluidAmount(1));
        tracker = FermentationTracker.initiate(this);
    }

    @Override
    public void tick() {
        super.tick();
        tracker.tick();
    }

    PropertyDelegate renderBuffer = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return 0;
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int size() {
            return 0;
        }
    };

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return renderBuffer;
    }
}
