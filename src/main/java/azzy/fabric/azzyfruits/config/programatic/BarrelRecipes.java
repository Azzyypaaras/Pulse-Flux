package azzy.fabric.azzyfruits.config.programatic;

import azzy.fabric.azzyfruits.util.recipe.RecipeTemplate;
import azzy.fabric.azzyfruits.util.recipe.templates.FFFermentingOutput;

public class BarrelRecipes extends RecipeTemplate {
    public BarrelRecipes(){
        inject("cloudbrew", "azzyfruits:drinkcloudberry", "still_cloudberry", 0xffc982, 0.6, -1, -1, 0.8, 0.03, 0.055);
    }
    public void inject(String id, String out, String fluidIn, int baseColor, double quality, int idealHeight, int idealLight, double idealTemp, double agingSpeed, double alcoholContent){
        RECIPES.put(fluidIn, new FFFermentingOutput("BARREL", id, out, fluidIn, baseColor, quality, idealHeight, idealLight, idealTemp, agingSpeed, alcoholContent));
    }
}
