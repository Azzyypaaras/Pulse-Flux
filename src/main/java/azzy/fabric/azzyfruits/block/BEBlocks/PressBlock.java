package azzy.fabric.azzyfruits.block.BEBlocks;

import alexiil.mc.lib.attributes.AttributeList;
import azzy.fabric.azzyfruits.tileentities.blockentity.BasketEntity;
import azzy.fabric.azzyfruits.tileentities.blockentity.PressEntity;
import azzy.fabric.azzyfruits.block.BaseMachine;
import azzy.fabric.azzyfruits.util.interaction.OnClick;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public class PressBlock extends BaseMachine implements OnClick {

    public static final Identifier GID = new Identifier(MODID, "press_gui");

    public PressBlock(Settings settings, String identifier, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(settings, identifier, material, sound, glow, bounds, effects);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            Item item = player.getStackInHand(hand).getItem();
            if(item instanceof BucketItem){
                if(item != Items.BUCKET)
                    OnClick.fillFromBucket(blockEntity, player, hand);
            }
            else if (blockEntity instanceof PressEntity && !player.isInSneakingPose() && player.getStackInHand(player.getActiveHand()).getItem() != Items.BUCKET) {
                ContainerProviderRegistry.INSTANCE.openContainer(GID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PressEntity) {
                ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)((PressEntity)blockEntity));
                // update comparators
                world.updateHorizontalAdjacent(pos, this);
            }
            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }

    @Override
    public void addAllAttributes(World world, BlockPos pos, BlockState state, AttributeList<?> to) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof PressEntity) {
            PressEntity tank = (PressEntity) be;
            to.offer(tank.fluidInv, Block.createCuboidShape(0,0,0,16,16,16));
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new PressEntity();
    }

}
