package azzy.fabric.circumstable.render.util;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

public abstract class FFRenderLayers extends RenderLayer {

    public static final RenderLayer IRIDESCENT = RenderLayer.of("iridescent", VertexFormats.POSITION_COLOR_TEXTURE, 7, 262144, false, true, RenderLayer.MultiPhaseParameters.builder().texture(MIPMAP_BLOCK_ATLAS_TEXTURE).lightmap(RenderPhase.DISABLE_LIGHTMAP).diffuseLighting(DISABLE_DIFFUSE_LIGHTING).transparency(TRANSLUCENT_TRANSPARENCY).target(TRANSLUCENT_TARGET).build(true));

    public FFRenderLayers(String name, VertexFormat vertexFormat, int drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }
}
