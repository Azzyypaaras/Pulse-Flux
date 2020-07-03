package azzy.fabric.forgottenfruits.render.blockentity;

import azzy.fabric.forgottenfruits.render.util.FFRenderLayers;
import azzy.fabric.forgottenfruits.render.util.HexColorTranslator;
import azzy.fabric.forgottenfruits.staticentities.blockentity.WitchCauldronEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WitchCauldronEntityRenderer extends BlockEntityRenderer<WitchCauldronEntity> {


    public WitchCauldronEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(WitchCauldronEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if(!entity.hasMetadata())
            return;

        int hex = entity.getCachedColor();

        int[] rgb = HexColorTranslator.translate(hex);
        int r = rgb[0];
        int g = rgb[1];
        int b = rgb[2];

        MinecraftClient.getInstance().getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
        Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("minecraft:block/water_still"));

        matrices.push();
        matrices.translate(0, 0.6, 0);

        MatrixStack.Entry matrix = matrices.peek();
        VertexConsumer consumer = vertexConsumers.getBuffer(FFRenderLayers.IRIDESCENT);

        consumer.vertex(matrix.getModel(), 0.1f, 0, 0.9f).color(r, g, b, 255).texture(sprite.getMinU(), sprite.getMaxV()).next();
        consumer.vertex(matrix.getModel(), 0.9f, 0, 0.9f).color(r, g, b, 255).texture(sprite.getMaxU(), sprite.getMaxV()).next();
        consumer.vertex(matrix.getModel(), 0.9f, 0, 0.1f).color(r, g, b, 255).texture(sprite.getMaxU(), sprite.getMinV()).next();
        consumer.vertex(matrix.getModel(), 0.1f, 0, 0.1f).color(r, g, b, 255).texture(sprite.getMinU(), sprite.getMinV()).next();
        matrices.pop();
    }
}
