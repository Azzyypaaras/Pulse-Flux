package azzy.fabric.forgottenfruits.util.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public abstract class RecipeTemplate<T extends FFRecipe> {
    public static final int BUCKET = 1620;

    public final HashMap<String, T> recipes = new HashMap<>();

    public static String serialize(ItemStack[] in) {
        StringBuilder serializer = new StringBuilder();

        serializer.append(Registry.ITEM.getId(in[0].getItem()).getNamespace());

        for (ItemStack sec : in) {
            String item = Registry.ITEM.getId(sec.getItem()).getPath();
            serializer.append(item, 0, item.length() / 4);
            serializer.append(item, (item.length() / 4) * 3, item.length() - 1);
        }

        return serializer.toString();
    }
}
