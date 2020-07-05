package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.config.programatic.BarrelRecipes;
import azzy.fabric.forgottenfruits.config.recipes.CauldronRecipes;
import azzy.fabric.forgottenfruits.config.recipes.PressRecipes;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.handlers.CauldronRecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.handlers.PressRecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.programatic.FermentationHandler;

public class RecipeRegistry {

    public static void init() {
        TrueRecipeRegistry.registerRecipeType(RecipeRegistryKey.PRESS, new PressRecipeHandler(RecipeRegistryKey.PRESS), new PressRecipes());
        TrueRecipeRegistry.registerRecipeType(RecipeRegistryKey.BARREL, new FermentationHandler(RecipeRegistryKey.BARREL), new BarrelRecipes());
        TrueRecipeRegistry.registerRecipeType(RecipeRegistryKey.CAULDRON, new CauldronRecipeHandler(RecipeRegistryKey.CAULDRON), new CauldronRecipes());
        TrueRecipeRegistry.sealRegistry();
    }
}
