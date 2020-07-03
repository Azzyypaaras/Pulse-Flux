package azzy.fabric.forgottenfruits.util.recipe.programatic;

import azzy.fabric.forgottenfruits.config.programatic.BarrelRecipes;
import azzy.fabric.forgottenfruits.util.recipe.FFRecipe;
import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.templates.FFFermentingOutput;
import net.minecraft.block.entity.BlockEntity;

import static azzy.fabric.forgottenfruits.ForgottenFruits.FFLog;
import static azzy.fabric.forgottenfruits.ForgottenFruits.REGISTEREDRECIPES;

public class FermentationHandler extends RecipeHandler {
    public FermentationHandler(RecipeRegistryKey id) {
        super(id);
    }

    @Override
    public FFFermentingOutput search(Object[] args) {
        String key = (String) args[0];
        BarrelRecipes recipes = (BarrelRecipes) REGISTEREDRECIPES.get(id).getRecipes();

        if(recipes.RECIPES.containsKey(key))
            if(valid((FFRecipe) recipes.RECIPES.get(key)))
                return (FFFermentingOutput) recipes.RECIPES.get(key);
        return null;
    }

    @Override
    public boolean matches(FFRecipe recipe, BlockEntity entity) {
        return false;
    }

    @Override
    public boolean valid(FFRecipe recipe) {
        if(recipe.type == RecipeRegistryKey.BARREL){
            return true;
        }
        else{
            FFLog.error("Barrel Fermenting recipe "+recipe.id+" is invalid!");
            return false;
        }
    }

    @Override
    public void craft(FFRecipe recipe, BlockEntity entity) {
    }
}
