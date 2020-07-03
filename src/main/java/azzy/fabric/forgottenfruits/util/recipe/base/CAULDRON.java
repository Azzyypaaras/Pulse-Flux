package azzy.fabric.forgottenfruits.util.recipe.base;

public class CAULDRON implements RecipeIntermediary {

    @Override
    public String getInfo() {
        return "Recipe format - Id, Brew item, Output item, Output amount, Input items (split by ~, not commas";
    }

    @Override
    public String[] getRecipes() {
        return new String[]{
                "mutandis; forgottenfruits:drinkcloudberry; forgottenfruits:mutandis; 4; minecraft:glowstone_dust ~ minecraft:kelp ~ minecraft:egg"
        };
    }
}
