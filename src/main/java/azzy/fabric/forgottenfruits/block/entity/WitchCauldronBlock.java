package azzy.fabric.forgottenfruits.block.entity;

import azzy.fabric.forgottenfruits.block.BaseMachine;
import azzy.fabric.forgottenfruits.item.AttunedAttunedStone;
import azzy.fabric.forgottenfruits.item.LiquorBottle;
import azzy.fabric.forgottenfruits.staticentities.blockentity.WitchCauldronEntity;
import azzy.fabric.forgottenfruits.util.tracker.BrewMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class WitchCauldronBlock extends BaseMachine {

    public static final BooleanProperty EFFULGENT = BooleanProperty.of("effulgent");
    public static final BooleanProperty CHAOTIC = BooleanProperty.of("chaotic");

    public WitchCauldronBlock(Settings settings, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(material, sound, glow, bounds, effects);
        setDefaultState(this.getStateManager().getDefaultState().with(EFFULGENT, false).with(CHAOTIC, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        Item item = stack.getItem();
        WitchCauldronEntity cauldron = (WitchCauldronEntity) world.getBlockEntity(pos);
        if (cauldron == null) return ActionResult.FAIL;
        if (item instanceof AttunedAttunedStone && cauldron.inventory.get(0).isEmpty() && !player.handSwinging) {
            cauldron.inventory.set(0, new ItemStack(item));
            world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.BLOCKS, 0.5f, 2.0f, true);
            stack.decrement(1);
            cauldron.updateGem();
            return ActionResult.SUCCESS;
        } else if (item instanceof LiquorBottle && cauldron.getMetadata() == null) {
            CompoundTag tag = stack.getSubTag("brewmetadata");
            cauldron.setMetadata(tag == null ? new BrewMetadata(true, false, 1, 1, 1) : BrewMetadata.fromTag(tag), item);
            if (!player.isCreative())
                stack.decrement(1);
            player.swingHand(hand);
            world.playSound(pos.getX() + 0.5, pos.getY() + 0.68, pos.getZ() + 0.5, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 0.7f, 1.15f + (world.random.nextInt(2) / 10.0f), true);

            return ActionResult.SUCCESS;
        } else if (player.getStackInHand(hand).isEmpty() && player.isInSneakingPose() && !cauldron.inventory.get(0).isEmpty() && !player.handSwinging) {
            player.setStackInHand(hand, cauldron.inventory.get(0));
            cauldron.inventory.set(0, ItemStack.EMPTY);
            world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 0.5f, 2.0f, true);
            cauldron.updateGem();
            return ActionResult.SUCCESS;
        } else if (player.isInSneakingPose())
            return ActionResult.PASS;

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WitchCauldronEntity) {
                ItemScatterer.spawn(world, pos, (WitchCauldronEntity) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING, EFFULGENT, CHAOTIC);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new WitchCauldronEntity();
    }
}
