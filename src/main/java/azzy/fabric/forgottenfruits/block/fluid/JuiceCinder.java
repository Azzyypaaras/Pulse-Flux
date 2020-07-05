package azzy.fabric.forgottenfruits.block.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

import static azzy.fabric.forgottenfruits.registry.BlockRegistry.CINDERMOTE_JUICE;
import static azzy.fabric.forgottenfruits.registry.FluidRegistry.CINDERMOTE;
import static azzy.fabric.forgottenfruits.registry.FluidRegistry.CINDERMOTE_FLOWING;
import static azzy.fabric.forgottenfruits.registry.ItemRegistry.CINDERMOTE_BUCKET;

public abstract class JuiceCinder extends GenericFluid {
    @Override
    public Fluid getStill() {
        return CINDERMOTE;
    }

    @Override
    public Fluid getFlowing() {
        return CINDERMOTE_FLOWING;
    }

    @Override
    public Item getBucketItem() {
        return CINDERMOTE_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        // method_15741 converts the LEVEL_1_8 of the fluid state to the LEVEL_15 the fluid block uses
        return CINDERMOTE_JUICE.getDefaultState().with(Properties.LEVEL_15, method_15741(fluidState));
    }

    public static class Flowing extends JuiceCinder {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends JuiceCinder {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }

}
