package azzy.fabric.azzyfruits.render.blockentity;

import azzy.fabric.azzyfruits.registry.FluidRegistry;
import azzy.fabric.azzyfruits.staticentities.blockentity.PressEntity;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Random;

public class PressEntityRenderer extends BlockEntityRenderer<PressEntity> {

    private static ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

    public PressEntityRenderer(BlockEntityRenderDispatcher dispatcher){
        super(dispatcher);
    }

    @Override
    public boolean rendersOutsideBoundingBox(PressEntity blockEntity) {
        return true;
    }

    @Override
    public void render(PressEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        int hex;
        Random random = new Random();

        if (!blockEntity.fluidInv.getTank(0).get().isEmpty())
            hex = FluidRenderHandlerRegistry.INSTANCE.get(blockEntity.fluidInv.getInvFluid(0).getRawFluid()).getFluidColor(null, null, null);
        else
            hex = -1;

        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = (hex & 0xFF);

        double height = (double) blockEntity.fluidInv.getTank(0).get().getAmount_F().asInt(1) / blockEntity.fluidInv.getMaxAmount_F(0).asInt(1);

        height *= 0.13;

        height += 0.06;

        matrices.push();
        matrices.translate(0.1, height, 0.1);
        matrices.scale(0.8f, 0.8f, 0.8f);
        MatrixStack.Entry matrix = matrices.peek();

        int toplight = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());

        VertexConsumer consumer;

        MinecraftClient.getInstance().getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
        Sprite sprite;

        if (blockEntity.fluidInv.getInvFluid(0).getRawFluid() == Fluids.LAVA) {
            sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("minecraft:block/lava_still"));
            consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        } else {
            sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("minecraft:block/water_still"));
            consumer = vertexConsumers.getBuffer(RenderLayer.getTranslucentNoCrumbling());
        }

        Fluid emissive = blockEntity.fluidInv.getInvFluid(0).getRawFluid();
        toplight = emissive == Fluids.LAVA || emissive == FluidRegistry.STILL_CINDERJUICE ? 14680160 : toplight;

        consumer.vertex(matrix.getModel(), 0, 0, 1).color(r, g, b, 255).texture(sprite.getMinU(), sprite.getMaxV()).light(toplight).normal(matrix.getNormal(), 0, 0, 0).next();
        consumer.vertex(matrix.getModel(), 1, 0, 1).color(r, g, b, 255).texture(sprite.getMaxU(), sprite.getMaxV()).light(toplight).normal(matrix.getNormal(), 0, 0, 0).next();
        consumer.vertex(matrix.getModel(), 1, 0, 0).color(r, g, b, 255).texture(sprite.getMaxU(), sprite.getMinV()).light(toplight).normal(matrix.getNormal(), 0, 0, 0).next();
        consumer.vertex(matrix.getModel(), 0, 0, 0).color(r, g, b, 255).texture(sprite.getMinU(), sprite.getMinV()).light(toplight).normal(matrix.getNormal(), 0, 0, 0).next();

        matrices.pop();
    }

}
