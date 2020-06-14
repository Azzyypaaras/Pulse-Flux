package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.config.recipes.PressRecipes;
import azzy.fabric.azzyfruits.util.recipe.handlers.PressRecipeHandler;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {

    public static void init(){
        TrueRecipeRegistry.registerRecipeType("PRESS", new PressRecipeHandler("PRESS"), new PressRecipes());

        TrueRecipeRegistry.sealRegistry();
    }
}
