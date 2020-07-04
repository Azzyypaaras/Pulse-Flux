package azzy.fabric.forgottenfruits.render.util;

import net.minecraft.client.render.*;

public abstract class FFRenderLayers extends RenderLayer {

    public static RenderLayer IRIDESCENT = RenderLayer.of("iridescent", VertexFormats.POSITION_COLOR_TEXTURE, 7, 262144, false, true, RenderLayer.MultiPhaseParameters.builder().texture(MIPMAP_BLOCK_ATLAS_TEXTURE).lightmap(RenderPhase.DISABLE_LIGHTMAP).diffuseLighting(DISABLE_DIFFUSE_LIGHTING).transparency(TRANSLUCENT_TRANSPARENCY).target(TRANSLUCENT_TARGET).build(true));

    public FFRenderLayers(String name, VertexFormat vertexFormat, int drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }
}
