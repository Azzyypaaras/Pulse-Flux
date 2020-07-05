package azzy.fabric.forgottenfruits.util.recipe.handlers;

import azzy.fabric.forgottenfruits.staticentities.blockentity.WitchCauldronEntity;
import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.RecipeTemplate;
import azzy.fabric.forgottenfruits.util.recipe.templates.FFCauldronRecipe;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;

import static azzy.fabric.forgottenfruits.ForgottenFruits.FFLog;

public class CauldronRecipeHandler extends RecipeHandler<FFCauldronRecipe, ItemStack> {

    public CauldronRecipeHandler(RecipeRegistryKey id) {
        super(id);
    }

    @Override
    public String getKey(ItemStack... args) {
        return RecipeTemplate.serialize(args);
    }

    @Override
    public boolean matches(FFCauldronRecipe recipe, BlockEntity entity) {
        WitchCauldronEntity cauldron = (WitchCauldronEntity) entity;

        int index = -1;
        for (int i = 0; i < cauldron.inventory.size(); ++i) {
            if (cauldron.inventory.get(i).isEmpty()) {
                index = i;
                break;
            }
        }

        if (recipe.inputs.length == index) {
            boolean match = recipe.brew.asItem() == cauldron.getBrew();
            if (!match) return false;
            for (int i = 0; i < recipe.inputs.length; i++) {
                match &= recipe.inputs[i] == cauldron.inventory.get(i);
            }
            return match;
        }
        return false;
    }

    @Override
    public boolean valid(FFCauldronRecipe recipe) {
        if (recipe.type == RecipeRegistryKey.CAULDRON) {
            return true;
        } else {
            FFLog.error("Fruit Press recipe " + recipe.id + " is invalid!");
            return false;
        }
    }

    @Override
    public void craft(FFCauldronRecipe recipe, BlockEntity entity) {
        WitchCauldronEntity cauldron = (WitchCauldronEntity) entity;
        ItemStack stone = cauldron.inventory.get(0);
        cauldron.inventory.clear();
        cauldron.inventory.set(0, stone);
        //Finish this
    }
}
