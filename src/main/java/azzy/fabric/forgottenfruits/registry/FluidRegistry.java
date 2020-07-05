package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.block.fluid.JuiceCinder;
import azzy.fabric.forgottenfruits.block.fluid.JuiceCloudberry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static azzy.fabric.forgottenfruits.ForgottenFruits.MOD_ID;

public class FluidRegistry {

    public static final List<FluidPair> JUICE_RENDER = new ArrayList<>();
    @Environment(EnvType.CLIENT)
    public static final List<Fluid> FLUID_TRANS = new ArrayList<>();
    //Cloudberry
    public static FlowableFluid CLOUD_BERRY = registerStill("cloud_berry", new JuiceCloudberry.Still());
    public static FlowableFluid CLOUD_BERRY_FLOWING = registerFlowing("cloud_berry_flowing", new JuiceCloudberry.Flowing());
    private static final FluidPair cloudberryJuice = new FluidPair(CLOUD_BERRY, CLOUD_BERRY_FLOWING, 0xee9b2f);
    //Cinder
    public static FlowableFluid CINDER_MOTE = registerStill("cinder_mote", new JuiceCinder.Still());
    public static FlowableFluid CINDER_MOTE_FLOWING = registerFlowing("cinder_mote_flowing", new JuiceCinder.Flowing());
    private static final FluidPair cindermoteJuice = new FluidPair(CINDER_MOTE, CINDER_MOTE_FLOWING, 0xe37b00);

    @Environment(EnvType.CLIENT)
    public static void initTransparency() {
        FLUID_TRANS.add(CLOUD_BERRY);
        FLUID_TRANS.add(CLOUD_BERRY_FLOWING);
        FLUID_TRANS.add(CINDER_MOTE);
        FLUID_TRANS.add(CINDER_MOTE_FLOWING);
    }

    public static FlowableFluid registerStill(String name, FlowableFluid item) {
        Registry.register(Registry.FLUID, new Identifier(MOD_ID, name), item);
        return item;
    }

    public static FlowableFluid registerFlowing(String name, FlowableFluid item) {
        Registry.register(Registry.FLUID, new Identifier(MOD_ID, name), item);
        return item;
    }

    public static void init() {
        JUICE_RENDER.add(cloudberryJuice);
        JUICE_RENDER.add(cindermoteJuice);
    }

    public static class FluidPair {

        private final FlowableFluid stillState;
        private final FlowableFluid flowState;
        private final int color;

        private FluidPair(final FlowableFluid stillState, final FlowableFluid flowState, final int color) {
            this.stillState = stillState;
            this.flowState = flowState;
            this.color = color;
        }

        public FlowableFluid getStillState() {
            return stillState;
        }

        public FlowableFluid getFlowState() {
            return flowState;
        }

        public int getColor() {
            return color;
        }
    }
}
