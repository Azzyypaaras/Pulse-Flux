package azzy.fabric.azzyfruits.config.programatic;

import azzy.fabric.azzyfruits.util.recipe.RecipeTemplate;
import azzy.fabric.azzyfruits.util.recipe.templates.FFFermentingOutput;

public class BarrelRecipes extends RecipeTemplate {
    public BarrelRecipes(){
        inject("cloudbrew", "still_cloudberry", "cloudbrew", );
    }
    public void inject(String id, String fluidIn, String out, int baseColor, int quality, int idealHeight, int idealLight, double idealTemp, double agingSpeed){
        RECIPES.put(fluidIn, new FFFermentingOutput("BARREL", id, fluidIn, out, baseColor, quality, idealHeight, idealLight, idealTemp, agingSpeed));
    }
}
