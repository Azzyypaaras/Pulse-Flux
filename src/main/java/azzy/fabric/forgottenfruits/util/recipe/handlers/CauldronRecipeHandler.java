package azzy.fabric.forgottenfruits.util.recipe.handlers;

import azzy.fabric.forgottenfruits.config.recipes.CauldronRecipes;
import azzy.fabric.forgottenfruits.staticentities.blockentity.WitchCauldronEntity;
import azzy.fabric.forgottenfruits.util.recipe.FFRecipe;
import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.RecipeTemplate;
import azzy.fabric.forgottenfruits.util.recipe.templates.FFCauldronRecipe;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.forgottenfruits.ForgottenFruits.FFLog;
import static azzy.fabric.forgottenfruits.ForgottenFruits.REGISTERED_RECIPES;

public class CauldronRecipeHandler extends RecipeHandler {

    public CauldronRecipeHandler(RecipeRegistryKey id) {
        super(id);
    }

    @Override
    public FFRecipe search(Object[] args) {
        ItemStack[] in = (ItemStack[]) args;
        CauldronRecipes recipes = (CauldronRecipes) REGISTERED_RECIPES.get(id).getRecipes();

        String key = RecipeTemplate.serialize(in);

        if(recipes.RECIPES.containsKey(key))
            if(valid((FFCauldronRecipe) recipes.RECIPES.get(key)))
                return (FFCauldronRecipe) recipes.RECIPES.get(key);

        return null;
    }

    @Override
    public boolean matches(FFRecipe recipe, BlockEntity entity) {
        FFCauldronRecipe crafted = (FFCauldronRecipe) recipe;
        WitchCauldronEntity cauldron = (WitchCauldronEntity) entity;

        if(crafted.inputs.length == cauldron.inventory.indexOf(null)){
            boolean match = Registry.ITEM.getId(crafted.brew.asItem()).toString().equals(cauldron.getCachedBrew());
            for(int i = 0; i < crafted.inputs.length && match; i++){
                match = crafted.inputs[i] == cauldron.inventory.get(i);
            }
            return match;
        }
        return false;
    }

    @Override
    public boolean valid(FFRecipe recipe) {
        if(recipe.type == RecipeRegistryKey.CAULDRON){
            return true;
        }
        else{
            FFLog.error("Fruit Press recipe "+recipe.id+" is invalid!");
            return false;
        }
    }

    @Override
    public void craft(FFRecipe recipe, BlockEntity entity) {
        WitchCauldronEntity cauldron = (WitchCauldronEntity) entity;
        ItemStack stone = cauldron.inventory.get(0);
        cauldron.inventory.clear();
        cauldron.inventory.set(0, stone);
        //Finish this
    }
}
