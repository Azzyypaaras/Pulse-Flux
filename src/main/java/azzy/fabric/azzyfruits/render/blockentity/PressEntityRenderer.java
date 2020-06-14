package azzy.fabric.azzyfruits.render.blockentity;

import azzy.fabric.azzyfruits.staticentities.blockentity.PressEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class PressEntityRenderer extends BlockEntityRenderer<PressEntity> {

    private static ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

    public PressEntityRenderer(BlockEntityRenderDispatcher dispatcher){
        super(dispatcher);
    }

    @Override
    public void render(PressEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    }

}
