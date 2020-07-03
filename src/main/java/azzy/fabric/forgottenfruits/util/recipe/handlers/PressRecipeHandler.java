package azzy.fabric.forgottenfruits.util.recipe.handlers;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.forgottenfruits.config.recipes.PressRecipes;
import azzy.fabric.forgottenfruits.staticentities.blockentity.PressEntity;
import azzy.fabric.forgottenfruits.util.recipe.FFRecipe;
import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.RecipeTemplate;
import azzy.fabric.forgottenfruits.util.recipe.templates.FFPressRecipe;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;

import static azzy.fabric.forgottenfruits.ForgottenFruits.*;

public class PressRecipeHandler extends RecipeHandler {

    public PressRecipeHandler(RecipeRegistryKey id) {
        super(id);
    }

    @Override
    public FFPressRecipe search(Object[] args) {
        ItemStack item = (ItemStack) args[0];
        PressRecipes recipes = (PressRecipes) REGISTEREDRECIPES.get(id).getRecipes();

        String key = RecipeTemplate.serialize(new ItemStack[]{item});

        if(recipes.RECIPES.containsKey(key))
            if(valid((FFRecipe) recipes.RECIPES.get(key)))
                return (FFPressRecipe) recipes.RECIPES.get(key);

        return null;
    }

    @Override
    public boolean matches(FFRecipe recipe, BlockEntity entity) {
        PressEntity crafter = (PressEntity) entity;
        FFPressRecipe crafted = (FFPressRecipe) recipe;

        ItemStack craftIn = crafted.input, invIn = crafter.inventory.get(0), invBy = crafter.inventory.get(1);
        FluidVolume craftOut = crafted.output, invOut = crafter.fluidInv.getInvFluid(0);

        if(valid(recipe)){
            if(config.isDebugOn() && entity.getWorld().getTime() % 100 == 0){
                FFLog.error("DEBUG MESSAGE START");
                FFLog.error("ITEM MATCH "+ (craftIn.getItem() == invIn.getItem()));
                FFLog.error("ITEM COUNT MATCH "+ (invIn.getCount() >= craftIn.getCount()));
                FFLog.error("EMPTY BYPRODUCT "+ (crafted.byproduct == null));
                FFLog.error("EMPTY BYPRODUCT SLOT "+ invBy.isEmpty());
                FFLog.error("BYPRODUCT COUNT AND ITEM MATCH "+ (invBy.getCount() < 64 && invBy.getItem() == crafted.byproduct));
                FFLog.error("WHOLE MATCH "+ ((craftIn.getItem() == invIn.getItem() && invIn.getCount() >= craftIn.getCount()) && ((crafted.byproduct == null || invBy.isEmpty()) || (invBy.getCount() < 64 && invBy.getItem() == crafted.byproduct)) && ((invOut.isEmpty()) || craftOut.canMerge(invOut) && (invOut.getAmount_F().as1620() + craftOut.getAmount_F().as1620() < crafter.fluidInv.getMaxAmount_F(0).as1620()))));
                FFLog.error("DEBUG MESSAGE END");
            }
            return (craftIn.getItem() == invIn.getItem() && invIn.getCount() >= craftIn.getCount()) && ((crafted.byproduct == null || invBy.isEmpty()) || (invBy.getCount() < 64 && invBy.getItem() == crafted.byproduct)) && ((invOut.isEmpty()) || craftOut.canMerge(invOut) && (invOut.getAmount_F().as1620() + craftOut.getAmount_F().as1620() <= crafter.fluidInv.getMaxAmount_F(0).as1620()));
        }

        return false;
    }

    @Override
    public boolean valid(FFRecipe recipe) {
        if(recipe.type == RecipeRegistryKey.PRESS){
            return true;
        }
        else{
            FFLog.error("Fruit Press recipe "+recipe.id+" is invalid!");
            return false;
        }
    }

    @Override
    public void craft(FFRecipe recipe, BlockEntity entity) {
        PressEntity crafter = (PressEntity) entity;
        FFPressRecipe crafted = (FFPressRecipe) recipe;
        boolean fuck = false;

        if(crafter.getWorld().isClient())
            return;

        ItemStack craftBy = new ItemStack(crafted.byproduct), craftIn = crafted.input, invIn = crafter.inventory.get(0), invBy = crafter.inventory.get(1);
        FluidVolume craftOut = crafted.output, invOut = crafter.fluidInv.getInvFluid(0);

        if(!invOut.isEmpty())
            crafter.fluidInv.attemptInsertion(craftOut, Simulation.ACTION);
        else{
            crafter.fluidInv.setInvFluid(0, craftOut, Simulation.ACTION);
        }

        if(fuck){
            FFLog.error("Recipe "+recipe.id+", Being performed by a press at "+entity.getPos()+" Failed to be performed post-validation, something is seriously broken because this should not even be possible!");
            return;
        }

        if(!invBy.isEmpty())
            invBy.increment(craftBy.getCount());
        else
            ((PressEntity) entity).inventory.set(1, craftBy);

        invIn.decrement(craftIn.getCount());
    }
}
