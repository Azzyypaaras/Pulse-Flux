package azzy.fabric.azzyfruits.staticentities.blockentity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.azzyfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.azzyfruits.util.recipe.programatic.FermentationHandler;
import azzy.fabric.azzyfruits.util.recipe.templates.FFFermentingOutput;
import azzy.fabric.azzyfruits.util.tracker.FermentationTracker;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;
import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.BARREL_ENTITY;

public class BarrelEntity extends MachineEntity implements PropertyDelegateHolder {

    private FermentationTracker tracker;
    private int loop;
    private boolean hasMetadata;

    public BarrelEntity(){
        super(BARREL_ENTITY);
        inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        fluidInv = new SimpleFixedFluidInv(1, new FluidAmount(32));
        tracker = FermentationTracker.initiate(this);
        hasMetadata = false;
    }

    @Override
    public void tick() {
        super.tick();
        tracker.tick();
        hasMetadata = tracker.canOutput();
        if(!fluidInv.getTank(0).get().isEmpty() && !tracker.isActive() && !tracker.canOutput()){
            FFFermentingOutput recipe = lookUp(Registry.FLUID.getId(fluidInv.getTank(0).get().getRawFluid()).toString());
            if(recipe != null){
                tracker.start(recipe);
            }
        }
    }

    public FFFermentingOutput lookUp(String fluid){
        FermentationHandler handler = (FermentationHandler) getRecipeHandler(RecipeRegistryKey.BARREL);
        Optional<FFFermentingOutput> output = Optional.ofNullable(handler.search(new Object[]{fluid}));
        return output.orElse(null);
    }

    public FermentationTracker getTracker(){
        return tracker;
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



    private PropertyDelegate renderBuffer = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch(index){
                case 0: return fluidInv.getTank(0).get().getAmount_F().asInt(1);
                case 1: return hasMetadata ? Integer.MAX_VALUE : fluidInv.getMaxAmount_F(0).asInt(1);
                case 2: return Math.min(tracker.time, tracker.minTime);
                case 3: return tracker.minTime;
                case 4: return loop;
                case 5: return 20;
                case 6: return Registry.FLUID.getRawId(fluidInv.getTank(0).get().getRawFluid());
                case 7: return Math.max(tracker.time - ((tracker.minTime/8)*3), 0);
                case 8: return ((tracker.minTime/8)*2);
                case 9: return tracker.getCachedColor();
                case 10: return hasMetadata ? fluidInv.getMaxAmount_F(0).asInt(1) : Integer.MAX_VALUE;
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

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return renderBuffer;
    }
}
