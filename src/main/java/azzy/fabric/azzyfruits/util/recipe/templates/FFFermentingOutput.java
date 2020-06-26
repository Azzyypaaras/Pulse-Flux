package azzy.fabric.azzyfruits.util.recipe.templates;

import azzy.fabric.azzyfruits.util.recipe.FFRecipe;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FFFermentingOutput extends FFRecipe {

    final public Fluid fluidIn;
    final public String out;
    final public int baseColor;
    final public int idealHeight, idealLight;
    final public double idealTemp, agingSpeed, alcoholContent, quality;


    public FFFermentingOutput(String type, String id, String out, String fluidIn, int baseColor, double quality, int idealHeight, int idealLight, double idealTemp, double agingSpeed, double alcoholContent) {
        super(type, id);
        this.fluidIn = Registry.FLUID.get(new Identifier(fluidIn));
        this.out = out;
        this.baseColor = baseColor;
        this.quality = quality;
        this.idealHeight = idealHeight;
        this.idealLight = idealLight;
        this.idealTemp = idealTemp;
        this.agingSpeed = agingSpeed;
        this.alcoholContent = alcoholContent;
    }
}
