package azzy.fabric.forgottenfruits.render.blockentity;

import azzy.fabric.forgottenfruits.registry.FluidRegistry;
import azzy.fabric.forgottenfruits.staticentities.blockentity.PressEntity;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import java.util.Objects;
import java.util.function.Function;

public class PressEntityRenderer extends BlockEntityRenderer<PressEntity> {

    private static final Identifier LAVA_STILL = new Identifier("block/lava_still");

    public PressEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public boolean rendersOutsideBoundingBox(PressEntity blockEntity) {
        return true;
    }

    @Override
    public void render(PressEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int color = blockEntity.fluidInv.getTank(0).get().isEmpty() ? -1 : FluidRenderHandlerRegistry.INSTANCE.get(blockEntity.fluidInv.getInvFluid(0).getRawFluid()).getFluidColor(null, null, null);
        int r = color >> 16 & 0xFF;
        int b = color >> 8 & 0xFF;
        int g = color & 0xFF;

        double height = (double) blockEntity.fluidInv.getTank(0).get().getAmount_F().asInt(1) / blockEntity.fluidInv.getMaxAmount_F(0).asInt(1) * 0.13 + 0.06;

        matrices.push();
        matrices.translate(0.1, height, 0.1);
        matrices.scale(0.8f, 0.8f, 0.8f);
        MatrixStack.Entry matrix = matrices.peek();

        VertexConsumer consumer;

        Fluid emissive = blockEntity.fluidInv.getInvFluid(0).getRawFluid();

        MinecraftClient mc = MinecraftClient.getInstance();
        Function<Identifier, Sprite> spriteAtlas = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        mc.getTextureManager().bindTexture(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        Sprite sprite;

        if (emissive == Fluids.LAVA) {
            sprite = spriteAtlas.apply(LAVA_STILL);
            consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        } else {
            sprite = spriteAtlas.apply(WitchCauldronEntityRenderer.WATER_STILL);
            consumer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());
        }

        if (blockEntity.hasWorld()) {
            int toplight = emissive != Fluids.LAVA && emissive != FluidRegistry.CINDERMOTE ? WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(blockEntity.getWorld()), blockEntity.getPos().up()) : 14680160;

            consumer.vertex(matrix.getModel(), 0, 0, 1).color(r, g, b, 200).texture(sprite.getMinU(), sprite.getMaxV()).light(toplight).normal(matrix.getNormal(), 1, 1, 1).next();
            consumer.vertex(matrix.getModel(), 1, 0, 1).color(r, g, b, 200).texture(sprite.getMaxU(), sprite.getMaxV()).light(toplight).normal(matrix.getNormal(), 1, 1, 1).next();
            consumer.vertex(matrix.getModel(), 1, 0, 0).color(r, g, b, 200).texture(sprite.getMaxU(), sprite.getMinV()).light(toplight).normal(matrix.getNormal(), 1, 1, 1).next();
            consumer.vertex(matrix.getModel(), 0, 0, 0).color(r, g, b, 200).texture(sprite.getMinU(), sprite.getMinV()).light(toplight).normal(matrix.getNormal(), 1, 1, 1).next();
        }

        matrices.pop();
    }
}
