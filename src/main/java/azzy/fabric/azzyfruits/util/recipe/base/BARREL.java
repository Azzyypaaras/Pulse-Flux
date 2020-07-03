package azzy.fabric.azzyfruits.util.recipe.base;

public class BARREL implements RecipeIntermediary{

        public static String[] recipes = new String[]{
                "cloudbrew; azzyfruits:drinkcloudberry; azzyfruits:still_cloudberry; 0xffc982; 0.5; -1; -1; 0.8; 0.055",
                "cinderbrew; azzyfruits:drinkcindermote; azzyfruits:still_cinder; 0xf5cb00; 0.4; -1; -1; 1.2; 0.12"
        };

        @Override
        public String getInfo() {
                return "Recipe format - Id, Item output, Fluid input, Color, Quality, Ideal height, Ideal light, Ideal temperature, Alcohol content. Color must be hexadecimal. Quality and Alcohol must be between 0 and 1. Refer to biome temperatures for sensible temperature values. Ideal height and Ideal light may be set to negative one to disable them.";
        }

        @Override
        public String[] getRecipes() {
                return recipes;
        }
}
