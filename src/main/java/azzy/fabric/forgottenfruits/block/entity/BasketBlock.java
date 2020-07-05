package azzy.fabric.forgottenfruits.block.entity;

import azzy.fabric.forgottenfruits.block.BaseMachine;
import azzy.fabric.forgottenfruits.registry.BlockEntityRegistry;
import azzy.fabric.forgottenfruits.staticentities.blockentity.BasketEntity;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static azzy.fabric.forgottenfruits.ForgottenFruits.MOD_ID;
import static azzy.fabric.forgottenfruits.registry.ItemRegistry.BASKET_ITEM;

public class BasketBlock extends BaseMachine {

    public static final Identifier GID = new Identifier(MOD_ID, "basket_gui");

    public BasketBlock(Settings settings, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(material, sound, glow, bounds, effects);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BasketEntity && !player.isInSneakingPose()) {
                ContainerProviderRegistry.INSTANCE.openContainer(GID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
            } else
                world.breakBlock(pos, true, player);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        ItemStack block = new ItemStack(BASKET_ITEM);
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BasketEntity) {
                block.getOrCreateTag();
                Inventories.toTag(block.getOrCreateTag(), ((BasketEntity) blockEntity).getItems());
                dropStack(world, pos, block);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BasketEntity) {
                ((BasketEntity) blockEntity).setFromItem(itemStack.getOrCreateTag());
            }
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return BlockEntityRegistry.BASKET.instantiate();
    }
}
