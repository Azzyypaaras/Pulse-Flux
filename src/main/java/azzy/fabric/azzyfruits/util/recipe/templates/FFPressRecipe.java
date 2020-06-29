package azzy.fabric.azzyfruits.util.recipe.templates;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.util.recipe.FFRecipe;
import azzy.fabric.azzyfruits.util.recipe.RecipeRegistryKey;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FFPressRecipe extends FFRecipe {
    final public ItemStack input;
    final public Item byproduct;
    final public FluidVolume output;

    public FFPressRecipe(RecipeRegistryKey type, String id, ItemStack input, Item byproduct, FluidVolume output){
        super(type, id);
        this.input = input;
        this.byproduct = byproduct;
        this.output = output;
    }
}
