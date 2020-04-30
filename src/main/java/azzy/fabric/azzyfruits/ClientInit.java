package azzy.fabric.azzyfruits;

import azzy.fabric.azzyfruits.registry.BlockRegistry;
import azzy.fabric.azzyfruits.registry.FluidRegistry;
import azzy.fabric.azzyfruits.registry.FluidRenderRegistry;
import azzy.fabric.azzyfruits.registry.GuiRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;


import java.util.ArrayList;
import java.util.List;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;
import static azzy.fabric.azzyfruits.registry.FluidRegistry.juiceRenderRegistry;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRegistry.initTransparency();
        this.initTransparency(BlockRegistry.registryTrans);
        BlockRegistry.initPartialblocks();
        this.initPartialblocks(BlockRegistry.registryPartial);
        FluidRegistry.initTransparency();
        this.initFluidTransparency(FluidRegistry.registryFluidTrans);
        GuiRegistry.init();

        for (int i = 0; i < juiceRenderRegistry.size(); i++) {
            FluidRegistry.FluidPair temp = juiceRenderRegistry.get(i);
            FluidRenderRegistry.setupFluidRendering(temp.getStillState(), temp.getFlowState(), new Identifier("minecraft", "water"), temp.getColor());
        }
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
