package azzy.fabric.forgottenfruits.util.recipe.handlers;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.forgottenfruits.staticentities.blockentity.PressEntity;
import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.RecipeTemplate;
import azzy.fabric.forgottenfruits.util.recipe.templates.FFPressRecipe;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;

import static azzy.fabric.forgottenfruits.ForgottenFruits.FFLog;
import static azzy.fabric.forgottenfruits.ForgottenFruits.config;

public class PressRecipeHandler extends RecipeHandler<FFPressRecipe, ItemStack> {

    public PressRecipeHandler(RecipeRegistryKey id) {
        super(id);
    }

    @Override
    public String getKey(ItemStack... args) {
        return RecipeTemplate.serialize(args);
    }

    @Override
    public boolean matches(FFPressRecipe recipe, BlockEntity entity) {
        PressEntity crafter = (PressEntity) entity;

        ItemStack craftIn = recipe.input, invIn = crafter.inventory.get(0), invBy = crafter.inventory.get(1);
        FluidVolume craftOut = recipe.output, invOut = crafter.fluidInv.getInvFluid(0);

        if (valid(recipe)) {
            if (config.isDebug() && entity.getWorld().getTime() % 100 == 0) {
                FFLog.error("DEBUG MESSAGE START");
                FFLog.error("ITEM MATCH " + (craftIn.getItem() == invIn.getItem()));
                FFLog.error("ITEM COUNT MATCH " + (invIn.getCount() >= craftIn.getCount()));
                FFLog.error("EMPTY BYPRODUCT " + (recipe.byproduct == null));
                FFLog.error("EMPTY BYPRODUCT SLOT " + invBy.isEmpty());
                FFLog.error("BYPRODUCT COUNT AND ITEM MATCH " + (invBy.getCount() < 64 && invBy.getItem() == recipe.byproduct));
                FFLog.error("WHOLE MATCH " + ((craftIn.getItem() == invIn.getItem() && invIn.getCount() >= craftIn.getCount()) && ((recipe.byproduct == null || invBy.isEmpty()) || (invBy.getCount() < 64 && invBy.getItem() == recipe.byproduct)) && ((invOut.isEmpty()) || craftOut.canMerge(invOut) && (invOut.getAmount_F().as1620() + craftOut.getAmount_F().as1620() < crafter.fluidInv.getMaxAmount_F(0).as1620()))));
                FFLog.error("DEBUG MESSAGE END");
            }
            return (craftIn.getItem() == invIn.getItem() && invIn.getCount() >= craftIn.getCount()) && ((recipe.byproduct == null || invBy.isEmpty()) || (invBy.getCount() < 64 && invBy.getItem() == recipe.byproduct)) && ((invOut.isEmpty()) || craftOut.canMerge(invOut) && (invOut.getAmount_F().as1620() + craftOut.getAmount_F().as1620() <= crafter.fluidInv.getMaxAmount_F(0).as1620()));
        }

        return false;
    }

    @Override
    public boolean valid(FFPressRecipe recipe) {
        if (recipe.type == RecipeRegistryKey.PRESS) {
            return true;
        } else {
            FFLog.error("Fruit Press recipe " + recipe.id + " is invalid!");
            return false;
        }
    }

    @Override
    public void craft(FFPressRecipe recipe, BlockEntity entity) {
        PressEntity crafter = (PressEntity) entity;
        if (crafter.getWorld().isClient())
            return;

        ItemStack craftBy = new ItemStack(recipe.byproduct), craftIn = recipe.input, invIn = crafter.inventory.get(0), invBy = crafter.inventory.get(1);
        FluidVolume craftOut = recipe.output, invOut = crafter.fluidInv.getInvFluid(0);

        if (!invOut.isEmpty())
            crafter.fluidInv.attemptInsertion(craftOut, Simulation.ACTION);
        else {
            crafter.fluidInv.setInvFluid(0, craftOut, Simulation.ACTION);
        }

        if (!invBy.isEmpty()) {
            invBy.increment(craftBy.getCount());
        } else {
            ((PressEntity) entity).inventory.set(1, craftBy);
        }

        invIn.decrement(craftIn.getCount());
    }
}
