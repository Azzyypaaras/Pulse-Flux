package azzy.fabric.azzyfruits.util.recipe.handlers;

import azzy.fabric.azzyfruits.config.recipes.CauldronRecipes;
import azzy.fabric.azzyfruits.staticentities.blockentity.WitchCauldronEntity;
import azzy.fabric.azzyfruits.util.recipe.FFRecipe;
import azzy.fabric.azzyfruits.util.recipe.RecipeHandler;
import azzy.fabric.azzyfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.azzyfruits.util.recipe.RecipeTemplate;
import azzy.fabric.azzyfruits.util.recipe.templates.FFCauldronRecipe;
import azzy.fabric.azzyfruits.util.recipe.templates.FFPressRecipe;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;
import static azzy.fabric.azzyfruits.ForgottenFruits.REGISTEREDRECIPES;

public class CauldronRecipeHandler extends RecipeHandler {

    public CauldronRecipeHandler(RecipeRegistryKey id) {
        super(id);
    }

    @Override
    public FFRecipe search(Object[] args) {
        ItemStack[] in = (ItemStack[]) args;
        CauldronRecipes recipes = (CauldronRecipes) REGISTEREDRECIPES.get(id).getRecipes();

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
