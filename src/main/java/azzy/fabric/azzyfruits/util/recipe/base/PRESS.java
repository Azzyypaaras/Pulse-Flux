package azzy.fabric.azzyfruits.util.recipe.base;

public class PRESS implements RecipeIntermediary{

        public static String[] recipes = new String[]{
                "azzyfruits:cloudberry_fruit; 4; azzyfruits:mulch; azzyfruits:still_cloudberry; cloudberry",
                "azzyfruits:cindermote_fruit; 4; azzyfruits:mulch; azzyfruits:still_cinder; cindermote"
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
