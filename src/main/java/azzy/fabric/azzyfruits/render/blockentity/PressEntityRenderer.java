package azzy.fabric.azzyfruits.render.blockentity;

import azzy.fabric.azzyfruits.tileentities.blockentity.PressEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class PressEntityRenderer extends BlockEntityRenderer<PressEntity> {

    private static ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

    public PressEntityRenderer(BlockEntityRenderDispatcher dispatcher){
        super(dispatcher);
    }

    @Override
    public void render(PressEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        matrices.push();
        float offset = (float) (Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 4.0);
        matrices.translate(0.5, 1.25 + offset, 0.5);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
        int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
        MinecraftClient.getInstance().textRenderer.draw("OWO", 4000, 2000, 0xffffff);
        matrices.pop();
    }

}
