package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.util.recipe.RecipeHandler;
import azzy.fabric.azzyfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.azzyfruits.util.recipe.RecipeTemplate;

import java.util.ArrayList;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;
import static azzy.fabric.azzyfruits.ForgottenFruits.REGISTEREDRECIPES;

//Don't ask why, just look at it and cry

public class TrueRecipeRegistry {
    static boolean initComplete = false;
    volatile static ArrayList<RegistryPair> RegistryBuffer = new ArrayList<>();


    static void registerRecipeType(RecipeRegistryKey id, RecipeHandler handler, RecipeTemplate recipes){
        if(initComplete){
            FFLog.error("Someone has attempted to register a recipe type after the recipe registry phase was complete, this WILL cause issues!");
            return;
        }

        RegistryBuffer.add(new RegistryPair(id, new RecipeType(handler, recipes)));
    }

    static void sealRegistry(){
        for (RegistryPair register: RegistryBuffer) {
            REGISTEREDRECIPES.put(register.id, register.type);
        }

        initComplete = true;
    }

    private static class RegistryPair{
        RecipeRegistryKey id;
        RecipeType type;
        public RegistryPair(RecipeRegistryKey id, RecipeType type){
            this.id = id;
            this.type = type;
        }
    }

    public static class RecipeType{
        private RecipeHandler handler;
        private RecipeTemplate recipes;

        public RecipeType(RecipeHandler handler, RecipeTemplate recipes){
            this.handler = handler;
            this.recipes = recipes;
        }

        public RecipeHandler getHandler() {
            return handler;
        }

        public RecipeTemplate getRecipes() {
            return recipes;
        }
    }
}
