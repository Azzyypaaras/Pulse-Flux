package azzy.fabric.azzyfruits.util.tracker;

import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;

public class UnprotectedFluidBlock extends FluidBlock {

    protected UnprotectedFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }
}
