package azzy.fabric.azzyfruits.config.programatic;

import azzy.fabric.azzyfruits.util.recipe.RecipeTemplate;
import azzy.fabric.azzyfruits.util.recipe.templates.FFFermentingOutput;

public class BarrelRecipes extends RecipeTemplate {
    public BarrelRecipes(){

    }
    public void Inject(String id, String fluidIn, String out, int baseColor, int quality, int idealHeight, int idealLight, double idealTemp, double agingSpeed){
        RECIPES.put(fluidIn, new FFFermentingOutput())
    }
}
