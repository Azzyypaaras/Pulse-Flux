package azzy.fabric.azzyfruits;

import azzy.fabric.azzyfruits.registry.BlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;


import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRegistry.initTransparency();
        this.initTransparency(BlockRegistry.registryTrans);
        BlockRegistry.initPartialblocks();
        this.initPartialblocks(BlockRegistry.registryPartial);
    }

    public static void initTransparency(ArrayList<Block> transparentblocks){
        for(Block item : transparentblocks)
            BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getTranslucent());
    }

    public static void initPartialblocks(ArrayList<Block> partialblocks){
        for(Block item : partialblocks)
            BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getCutoutMipped());
    }
}
