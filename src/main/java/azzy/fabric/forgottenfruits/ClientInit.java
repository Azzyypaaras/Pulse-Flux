package azzy.fabric.forgottenfruits;

import azzy.fabric.forgottenfruits.registry.*;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;


import java.util.ArrayList;

import static azzy.fabric.forgottenfruits.registry.FluidRegistry.juiceRenderRegistry;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

    private static volatile int cachedX, cachedY;

    public static ConfigBuilder builder;

    @Override
    public void onInitializeClient() {
        BlockRegistry.initTransparency();
        initTransparency(BlockRegistry.registryTrans);
        BlockRegistry.initPartialblocks();
        initPartialblocks(BlockRegistry.registryPartial);
        FluidRegistry.initTransparency();
        initFluidTransparency(FluidRegistry.registryFluidTrans);
        GuiRegistry.init();
        RenderRegistry.init();
        ColorRegistry.init();


        for (int i = 0; i < juiceRenderRegistry.size(); i++) {
            FluidRegistry.FluidPair temp = juiceRenderRegistry.get(i);
            FluidRenderRegistry.setupFluidRendering(temp.getStillState(), temp.getFlowState(), new Identifier("minecraft", "water"), temp.getColor());
        }
    }

    public static void setCachedLook(int x, int y){
        cachedX = x;
        cachedY = y;
    }

    public static int getCachedX() {
        return cachedX;
    }

    public static int getCachedY() {
        return cachedY;
    }

    public static void initTransparency(ArrayList<Block> transparentblocks){
        for(Block item : transparentblocks)
            BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getTranslucent());
    }

    public static void initFluidTransparency(ArrayList<Fluid> transparentfluids){
        for(Fluid item : transparentfluids)
            BlockRenderLayerMap.INSTANCE.putFluid(item, RenderLayer.getTranslucent());
    }

    public static void initPartialblocks(ArrayList<Block> partialblocks){
        for(Block item : partialblocks)
            BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getCutoutMipped());
    }
}
