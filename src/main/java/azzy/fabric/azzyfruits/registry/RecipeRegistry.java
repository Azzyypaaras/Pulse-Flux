package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.config.programatic.BarrelRecipes;
import azzy.fabric.azzyfruits.config.recipes.CauldronRecipes;
import azzy.fabric.azzyfruits.config.recipes.PressRecipes;
import azzy.fabric.azzyfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.azzyfruits.util.recipe.handlers.CauldronRecipeHandler;
import azzy.fabric.azzyfruits.util.recipe.handlers.PressRecipeHandler;
import azzy.fabric.azzyfruits.util.recipe.programatic.FermentationHandler;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {

    public static void init(){
        TrueRecipeRegistry.registerRecipeType(RecipeRegistryKey.PRESS, new PressRecipeHandler(RecipeRegistryKey.PRESS), new PressRecipes());
        TrueRecipeRegistry.registerRecipeType(RecipeRegistryKey.BARREL, new FermentationHandler(RecipeRegistryKey.BARREL), new BarrelRecipes());
        TrueRecipeRegistry.registerRecipeType(RecipeRegistryKey.CAULDRON, new CauldronRecipeHandler(RecipeRegistryKey.CAULDRON), new CauldronRecipes());
        TrueRecipeRegistry.sealRegistry();
    }
}
