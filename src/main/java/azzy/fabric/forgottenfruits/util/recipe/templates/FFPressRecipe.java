package azzy.fabric.forgottenfruits.util.recipe.templates;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.forgottenfruits.util.recipe.FFRecipe;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FFPressRecipe extends FFRecipe {
    final public ItemStack input;
    final public Item byproduct;
    final public FluidVolume output;

    public FFPressRecipe(RecipeRegistryKey type, String id, ItemStack input, Item byproduct, FluidVolume output) {
        super(type, id);
        this.input = input;
        this.byproduct = byproduct;
        this.output = output;
    }
}
