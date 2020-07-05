package azzy.fabric.forgottenfruits.util.recipe.templates;

import azzy.fabric.forgottenfruits.util.recipe.FFRecipe;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FFCauldronRecipe extends FFRecipe {
    final public ItemStack[] inputs;
    final public ItemStack output;
    final public Item brew;

    public FFCauldronRecipe(RecipeRegistryKey type, String id, Item brew, ItemStack output, ItemStack... inputs) {
        super(type, id);
        this.brew = brew;
        this.output = output;
        this.inputs = inputs;
    }
}
