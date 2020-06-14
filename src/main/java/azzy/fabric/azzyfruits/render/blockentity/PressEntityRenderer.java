package azzy.fabric.azzyfruits.render.blockentity;

import azzy.fabric.azzyfruits.registry.FluidRegistry;
import azzy.fabric.azzyfruits.tileentities.blockentity.PressEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Random;

public class PressEntityRenderer extends BlockEntityRenderer<PressEntity> {

    private static ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

    public PressEntityRenderer(BlockEntityRenderDispatcher dispatcher){
        super(dispatcher);
    }

    @Override
    public void render(PressEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    }

}
