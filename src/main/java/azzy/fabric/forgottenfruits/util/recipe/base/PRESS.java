package azzy.fabric.forgottenfruits.util.recipe.base;

public class PRESS implements RecipeIntermediary{

        public static String[] recipes = new String[]{
                "forgottenfruits:cloudberry_fruit; 4; forgottenfruits:mulch; forgottenfruits:still_cloudberry; cloudberry",
                "forgottenfruits:cindermote_fruit; 4; forgottenfruits:mulch; forgottenfruits:still_cinder; cindermote"
        };

        @Override
        public String getInfo() {
                return "Recipe format - Item input, amount, Item byproduct, Fluid output, Recipe id";
        }

        @Override
        public String[] getRecipes() {
                return recipes;
        }
}
