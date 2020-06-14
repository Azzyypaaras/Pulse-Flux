package azzy.fabric.azzyfruits.staticentities.blockentity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.azzyfruits.util.recipe.handlers.PressRecipeHandler;
import azzy.fabric.azzyfruits.util.recipe.templates.FFPressRecipe;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

import static azzy.fabric.azzyfruits.ForgottenFruits.DEBUG;
import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;
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
        if(progress >= 200) {
            progress = 0;
            cycleComplete();
        }
        else if(cycleCheck()){
            progress++;
        }
        if(!cycleCheck()){
            progress = 0;
        }
    }

    private void cycleComplete(){
        if(DEBUG){
            FFLog.error("DEBUG - "+this.toString()+" COMPLETED A CRAFTING CYCLE");
        }

        PressRecipeHandler handler = (PressRecipeHandler) getRecipeHandler("PRESS");
        if(handler != null){
            FFPressRecipe recipe = handler.search(new Object[]{inventory.get(0)});
            handler.craft(recipe, this);
        }
    }

    private boolean cycleCheck(){
        if(inventory.get(0).isEmpty())
            return false;
        PressRecipeHandler handler = (PressRecipeHandler) getRecipeHandler("PRESS");
        if(handler != null){
            Optional<FFPressRecipe> recipe = Optional.ofNullable(handler.search(new Object[]{inventory.get(0)}));
            if(recipe.isPresent()){
                return handler.matches(recipe.get(), this);
            }
        }
        return false;
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
