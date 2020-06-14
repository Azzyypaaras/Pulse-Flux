package azzy.fabric.azzyfruits.config.recipes;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.registry.FluidRegistry;
import azzy.fabric.azzyfruits.registry.ItemRegistry;
import azzy.fabric.azzyfruits.util.recipe.FFRecipe;
import azzy.fabric.azzyfruits.util.recipe.RecipeTemplate;
import azzy.fabric.azzyfruits.util.recipe.templates.FFPressRecipe;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static azzy.fabric.azzyfruits.ForgottenFruits.DEBUG;
import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;

public class PressRecipes extends RecipeTemplate {
    public PressRecipes(){
        inject(new ItemStack(ItemRegistry.CLOUDBERRY_FRUIT, 4), ItemRegistry.MULCH, FluidVolume.create(FluidRegistry.STILL_CLOUDJUICE, BUCKET), "cloudberry");
    }

    public void inject(ItemStack in, Item by, FluidVolume out, String id) {
        RECIPES.put(serialize(in), new FFPressRecipe("PRESS", id, in, by, out));
        if(DEBUG)
            FFLog.error("DEBUG - KEY - "+serialize(in));
    }
}
