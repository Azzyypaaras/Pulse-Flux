package azzy.fabric.azzyfruits.util.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public abstract class RecipeTemplate<T> {
    public HashMap<String, FFRecipe> RECIPES = new HashMap<String, FFRecipe>();

    public static final int BUCKET = 1620;

    public String serialize(ItemStack... in){
        StringBuilder serializer = new StringBuilder();

        for (ItemStack sec: in) {
            serializer.append(Registry.ITEM.getId(sec.getItem()));
        }

        return serializer.toString();
    }
}
