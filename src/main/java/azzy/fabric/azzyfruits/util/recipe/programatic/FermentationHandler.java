package azzy.fabric.azzyfruits.util.recipe.programatic;

import azzy.fabric.azzyfruits.config.programatic.BarrelRecipes;
import azzy.fabric.azzyfruits.util.recipe.FFRecipe;
import azzy.fabric.azzyfruits.util.recipe.RecipeHandler;
import azzy.fabric.azzyfruits.util.recipe.templates.FFFermentingOutput;
import azzy.fabric.azzyfruits.util.recipe.templates.FFPressRecipe;
import net.minecraft.block.entity.BlockEntity;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;
import static azzy.fabric.azzyfruits.ForgottenFruits.REGISTEREDRECIPES;

public class FermentationHandler extends RecipeHandler {
    public FermentationHandler(String id) {
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
        if(recipe.type.equals("BARREL")){
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
