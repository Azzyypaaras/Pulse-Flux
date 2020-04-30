package azzy.fabric.azzyfruits.block.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import static azzy.fabric.azzyfruits.registry.FluidRegistry.*;

public abstract class JuiceCloudberry extends GenericFluid{
    @Override
    public Fluid getStill()
    {
        return STILL_CLOUDJUICE;
    }

    @Override
    public Fluid getFlowing()
    {
        return FLOWING_CLOUDJUICE;
    }

    @Override
    public Item getBucketItem()
    {
        return BUCKET_CLOUDJUICE;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState)
    {
        // method_15741 converts the LEVEL_1_8 of the fluid state to the LEVEL_15 the fluid block uses
        return CLOUDJUICE.getDefaultState().with(Properties.LEVEL_15, method_15741(fluidState));
    }

    public static class Flowing extends JuiceCloudberry
    {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder)
        {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState)
        {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState)
        {
            return false;
        }
    }

    public static class Still extends JuiceCloudberry
    {
        @Override
        public int getLevel(FluidState fluidState)
        {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState)
        {
            return true;
        }
    }

}
