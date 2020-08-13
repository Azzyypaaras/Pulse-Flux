package azzy.fabric.pulseflux;

import azzy.fabric.pulseflux.registry.*;
//import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

import java.util.List;

import static azzy.fabric.pulseflux.registry.FluidRegistry.JUICE_RENDER;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

    //public static ConfigBuilder builder;

    public static void initTransparency(List<Block> transparentblocks) {
        for (Block item : transparentblocks)
            BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getTranslucent());
    }

    public static void initFluidTransparency(List<Fluid> transparentfluids) {
        for (Fluid item : transparentfluids)
            BlockRenderLayerMap.INSTANCE.putFluid(item, RenderLayer.getTranslucent());
    }

    public static void initPartialblocks(List<Block> partialblocks) {
        for (Block item : partialblocks)
            BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getCutoutMipped());
    }

    @Override
    public void onInitializeClient() {
        BlockRegistry.initTransparency();
        initTransparency(BlockRegistry.REGISTRY_TRANS);
        BlockRegistry.initPartialblocks();
        initPartialblocks(BlockRegistry.REGISTRY_PARTIAL);
        FluidRegistry.initTransparency();
        initFluidTransparency(FluidRegistry.FLUID_TRANS);
        GuiRegistry.init();
        RenderRegistry.init();
        ColorRegistry.init();
        ParticleRegistry.initClient();

        for (FluidRegistry.FluidPair temp : JUICE_RENDER) {
            FluidRenderRegistry.setupFluidRendering(temp.getStillState(), temp.getFlowState(), new Identifier("minecraft", "water"), temp.getColor());
        }
    }
}
