package azzy.fabric.azzyfruits.config.programatic;

import azzy.fabric.azzyfruits.util.recipe.JanksonRecipeParser;
import azzy.fabric.azzyfruits.util.recipe.RecipeRegistryKey;
import azzy.fabric.azzyfruits.util.recipe.RecipeTemplate;
import azzy.fabric.azzyfruits.util.recipe.templates.FFFermentingOutput;

import java.util.Iterator;
import java.util.Queue;

public class BarrelRecipes extends RecipeTemplate {
    public BarrelRecipes(){
        Queue<Iterator<String>> recipes = JanksonRecipeParser.getRecipeQueue(RecipeRegistryKey.BARREL);
        while (recipes.peek() != null) {
            Iterator<String> recipeBits = recipes.poll();
            inject(recipeBits.next(), recipeBits.next(), recipeBits.next(), Integer.parseInt(recipeBits.next().replace("0x", ""), 16), Double.parseDouble(recipeBits.next()), Integer.parseInt(recipeBits.next()), Integer.parseInt(recipeBits.next()), Double.parseDouble(recipeBits.next()), Double.parseDouble(recipeBits.next()));
        }
    }
    public void inject(String id, String out, String fluidIn, int baseColor, double quality, int idealHeight, int idealLight, double idealTemp, double alcoholContent){
        RECIPES.put(fluidIn, new FFFermentingOutput(RecipeRegistryKey.BARREL, id, out, fluidIn, baseColor, quality, idealHeight, idealLight, idealTemp, alcoholContent));
    }
}
