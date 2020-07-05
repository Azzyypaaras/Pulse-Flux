package azzy.fabric.forgottenfruits.staticentities.blockentity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.forgottenfruits.util.controller.PressController;
import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.templates.FFPressRecipe;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.forgottenfruits.registry.BlockEntityRegistry.PRESS;

public class PressEntity extends MachineEntity implements PropertyDelegateHolder {

    final PropertyDelegate tankHolder = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch (index) {
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
        }

        @Override
        public int size() {
            return 4;
        }
    };

    public PressEntity() {
        super(PRESS);
        inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        fluidInv = new SimpleFixedFluidInv(1, new FluidAmount(8));
    }

    @Override
    public void tick() {
        super.tick();
        if (progress >= 200) {
            progress = 0;
            cycleComplete();
        } else if (cycleCheck()) {
            progress++;
        }
        if (!cycleCheck()) {
            progress = 0;
        }
    }

    private void cycleComplete() {
        RecipeHandler<FFPressRecipe, ?> handler = getRecipeHandler(RecipeRegistryKey.PRESS);
        if (handler != null) {
            FFPressRecipe recipe = handler.search(new Object[]{inventory.get(0)});
            handler.craft(recipe, this);
        }
    }

    private boolean cycleCheck() {
        if (inventory.get(0).isEmpty())
            return false;
        RecipeHandler<FFPressRecipe, ?> handler = getRecipeHandler(RecipeRegistryKey.PRESS);
        FFPressRecipe recipe = handler.search(new Object[]{inventory.get(0)});
        return recipe != null && handler.matches(recipe, this);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.UP && slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN && slot == 1;
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return tankHolder;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new PressController(syncId, inv, ScreenHandlerContext.create(world, pos));
    }
}
