package azzy.fabric.azzyfruits.config.recipes;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.registry.FluidRegistry;
import azzy.fabric.azzyfruits.registry.ItemRegistry;
import azzy.fabric.azzyfruits.util.recipe.FFRecipe;
import azzy.fabric.azzyfruits.util.recipe.JanksonRecipeParser;
import azzy.fabric.azzyfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.azzyfruits.util.recipe.RecipeTemplate;
import azzy.fabric.azzyfruits.util.recipe.templates.FFPressRecipe;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;
import static azzy.fabric.azzyfruits.ForgottenFruits.config;

public class PressRecipes extends RecipeTemplate {
    public PressRecipes(){
        Queue<Iterator<String>> recipes = JanksonRecipeParser.getRecipeQueue(RecipeRegistryKey.PRESS);
        while(recipes.peek() != null) {
            Iterator<String> recipeBits = recipes.poll();
            try {
                inject(recipeBits.next(), Integer.parseInt(recipeBits.next()), recipeBits.next(), recipeBits.next(), recipeBits.next());
            }
            catch (NoSuchElementException e){
                FFLog.error("A PRESS RECIPE IS INVALID");
            }
        }
    }

    private void inject(String in, int amount, String by, String out, String id) {
        ItemStack input = new ItemStack(Registry.ITEM.get(new Identifier(in)), amount);
        RECIPES.put(serialize(new ItemStack[]{input}), new FFPressRecipe(RecipeRegistryKey.PRESS, id, input, Registry.ITEM.get(new Identifier(by)), FluidVolume.create(FluidKeys.get(Registry.FLUID.get(new Identifier(out))), BUCKET)));
        if(config.isDebugOn())
            FFLog.debug("DEBUG - KEY - "+serialize(new ItemStack[]{input}));
    }
}
