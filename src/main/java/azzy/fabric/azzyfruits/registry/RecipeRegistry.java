package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.config.programatic.BarrelRecipes;
import azzy.fabric.azzyfruits.config.recipes.PressRecipes;
import azzy.fabric.azzyfruits.util.recipe.handlers.PressRecipeHandler;
import azzy.fabric.azzyfruits.util.recipe.programatic.FermentationHandler;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {

    public static void init(){
        TrueRecipeRegistry.registerRecipeType("PRESS", new PressRecipeHandler("PRESS"), new PressRecipes());
        TrueRecipeRegistry.registerRecipeType("BARREL", new FermentationHandler("BARREL"), new BarrelRecipes());
        TrueRecipeRegistry.sealRegistry();
    }
}
