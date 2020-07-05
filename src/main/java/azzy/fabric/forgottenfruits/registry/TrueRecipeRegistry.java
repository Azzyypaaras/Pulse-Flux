package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.util.recipe.FFRecipe;
import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.forgottenfruits.util.recipe.RecipeTemplate;

import java.util.ArrayList;
import java.util.List;

import static azzy.fabric.forgottenfruits.ForgottenFruits.FFLog;
import static azzy.fabric.forgottenfruits.ForgottenFruits.REGISTERED_RECIPES;

//Don't ask why, just look at it and cry

public class TrueRecipeRegistry {
    private static boolean initComplete = false;
    private static final List<RegistryPair<?>> RegistryBuffer = new ArrayList<>();


    static <T extends FFRecipe> void registerRecipeType(RecipeRegistryKey id, RecipeHandler<T, ?> handler, RecipeTemplate<T> recipes) {
        if (initComplete) {
            FFLog.error("Someone has attempted to register a recipe type after the recipe registry phase was complete, this WILL cause issues!");
            return;
        }

        RegistryBuffer.add(new RegistryPair<>(id, new RecipeType<>(handler, recipes)));
    }

    static void sealRegistry() {
        for (RegistryPair<?> register : RegistryBuffer) {
            REGISTERED_RECIPES.put(register.id, register.type);
        }

        initComplete = true;
    }

    private static class RegistryPair<T extends FFRecipe> {
        protected final RecipeRegistryKey id;
        protected final RecipeType<T> type;

        public RegistryPair(RecipeRegistryKey id, RecipeType<T> type) {
            this.id = id;
            this.type = type;
        }
    }

    public static class RecipeType<T extends FFRecipe> {
        private final RecipeHandler<T, ?> handler;
        private final RecipeTemplate<T> recipes;

        public RecipeType(RecipeHandler<T, ?> handler, RecipeTemplate<T> recipes) {
            this.handler = handler;
            this.recipes = recipes;
        }

        public RecipeHandler<T, ?> getHandler() {
            return handler;
        }

        public RecipeTemplate<T> getRecipes() {
            return recipes;
        }
    }
}
