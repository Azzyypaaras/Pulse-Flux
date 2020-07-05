package azzy.fabric.forgottenfruits.staticentities.blockentity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.templates.FFFermentingOutput;
import azzy.fabric.forgottenfruits.util.tracker.FermentationTracker;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.forgottenfruits.registry.BlockEntityRegistry.BARREL;

public class BarrelEntity extends MachineEntity implements PropertyDelegateHolder {

    private final FermentationTracker tracker;
    private boolean hasMetadata;
    private final PropertyDelegate renderBuffer = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return fluidInv.getTank(0).get().getAmount_F().asInt(1);
                case 1:
                    return hasMetadata ? Integer.MAX_VALUE : fluidInv.getMaxAmount_F(0).asInt(1);
                case 2:
                    return Math.min(tracker.time, tracker.minTime);
                case 3:
                    return tracker.minTime;
                case 5:
                    return 20;
                case 6:
                    return Registry.FLUID.getRawId(fluidInv.getTank(0).get().getRawFluid());
                case 7:
                    return Math.max(tracker.time - ((tracker.minTime / 8) * 3), 0);
                case 8:
                    return ((tracker.minTime / 8) * 2);
                case 9:
                    return tracker.getCachedColor();
                case 10:
                    return hasMetadata ? fluidInv.getMaxAmount_F(0).asInt(1) : Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int size() {
            return 10;
        }
    };

    public BarrelEntity() {
        super(BARREL);
        inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        fluidInv = new SimpleFixedFluidInv(1, new FluidAmount(16));
        tracker = FermentationTracker.initiate(this);
        hasMetadata = false;
    }

    @Override
    public void tick() {
        super.tick();
        tracker.tick();
        hasMetadata = tracker.canOutput();
        if (!fluidInv.getTank(0).get().isEmpty() && !tracker.isActive() && !tracker.canOutput()) {
            FFFermentingOutput recipe = lookUp(Registry.FLUID.getId(fluidInv.getTank(0).get().getRawFluid()).toString());
            if (recipe != null) {
                tracker.start(recipe);
            }
        }
    }

    public FFFermentingOutput lookUp(String fluid) {
        RecipeHandler<FFFermentingOutput, ?> handler = getRecipeHandler(RecipeRegistryKey.BARREL);
        return handler.search(new Object[]{fluid});
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tracker.toTag(tag);
        tag.putBoolean("hasmetadata", hasMetadata);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        tracker.fromTag(tag, this);
        hasMetadata = tag.getBoolean("hasmetadata");
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        tracker.toTag(compoundTag);
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        tracker.fromTag(compoundTag, this);
        super.fromClientTag(compoundTag);
    }

    public FermentationTracker getTracker() {
        return tracker;
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return renderBuffer;
    }
}
