package azzy.fabric.azzyfruits.util.recipe.templates;

import azzy.fabric.azzyfruits.util.recipe.FFRecipe;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FFFermentingOutput extends FFRecipe {

    final public Fluid fluidIn;
    final public String out;
    final public int baseColor;
    final public int quality, idealHeight, idealLight;
    final public double idealTemp, agingSpeed;


    public FFFermentingOutput(String type, String id, String fluidIn, String out, int baseColor, int quality, int idealHeight, int idealLight, double idealTemp, double agingSpeed) {
        super(type, id);
        this.fluidIn = Registry.FLUID.get(new Identifier(fluidIn));
        this.out = out;
        this.baseColor = baseColor;
        this.quality = quality;
        this.idealHeight = idealHeight;
        this.idealLight = idealLight;
        this.idealTemp = idealTemp;
        this.agingSpeed = agingSpeed;
    }
}
