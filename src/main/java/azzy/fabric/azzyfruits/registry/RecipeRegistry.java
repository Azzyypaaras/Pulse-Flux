package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.recipetypes.PressRecipe;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {

    public static void init(){
        Registry.register(Registry.RECIPE_SERIALIZER, PressRecipe.ID, PressRecipe.PressRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, PressRecipe.ID, PressRecipe.PressRecipeType.INSTANCE);
    }
}
