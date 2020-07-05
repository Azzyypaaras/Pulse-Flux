package azzy.fabric.forgottenfruits.util.recipe.programatic;

import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.templates.FFFermentingOutput;
import net.minecraft.block.entity.BlockEntity;

import static azzy.fabric.forgottenfruits.ForgottenFruits.FFLog;

public class FermentationHandler extends RecipeHandler<FFFermentingOutput, String> {
    public FermentationHandler(RecipeRegistryKey id) {
        super(id);
    }

    @Override
    public String getKey(String... args) {
        return args[0];
    }

    @Override
    public boolean matches(FFFermentingOutput recipe, BlockEntity entity) {
        return false;
    }

    @Override
    public boolean valid(FFFermentingOutput recipe) {
        if (recipe.type == RecipeRegistryKey.BARREL) {
            return true;
        } else {
            FFLog.error("Barrel Fermenting recipe " + recipe.id + " is invalid!");
            return false;
        }
    }

    @Override
    public void craft(FFFermentingOutput recipe, BlockEntity entity) {
    }
}
