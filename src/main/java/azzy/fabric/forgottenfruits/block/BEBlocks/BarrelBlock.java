package azzy.fabric.forgottenfruits.block.BEBlocks;

import alexiil.mc.lib.attributes.AttributeList;
import azzy.fabric.forgottenfruits.block.BaseMachine;
import azzy.fabric.forgottenfruits.staticentities.blockentity.BarrelEntity;
import azzy.fabric.forgottenfruits.util.interaction.BucketHandler;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static azzy.fabric.forgottenfruits.ForgottenFruits.MODID;

public class BarrelBlock extends BaseMachine {

    public static final Identifier GID = new Identifier(MODID, "barrel_gui");

    public BarrelBlock(Settings settings, String identifier, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(settings, identifier, material, sound, glow, bounds, effects);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
       if(!world.isClient()){

           BarrelEntity blockEntity = (BarrelEntity) world.getBlockEntity(pos);
           Item item = player.getStackInHand(hand).getItem();

           if(item instanceof BucketItem && blockEntity != null) {

               if(blockEntity.getTracker().canOutput())
                   return ActionResult.PASS;

               boolean success = BucketHandler.toTank(item, blockEntity.fluidInv.getTank(0));
               if(success) {
                   if(!player.isCreative())
                       player.setStackInHand(hand, new ItemStack(Items.BUCKET, 1));

                   world.playSound((PlayerEntity) null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 1.0f, world.random.nextFloat() * 0.05f + 0.95f);
               }
               else if(Registry.ITEM.getId(item).toString().equals("minecraft:bucket")){
                   Item bucket = BucketHandler.toBucket(item, blockEntity.fluidInv.getTank(0));
                   if(bucket != null){
                       if(!player.isCreative()) {
                           if(player.getStackInHand(hand).getCount() > 1){
                               world.spawnEntity(new ItemEntity(world, pos.getX()+0.5, pos.getY()+1.25, pos.getZ()+0.5, new ItemStack(bucket)));
                               player.getStackInHand(hand).decrement(1);
                           }
                           else
                               player.setStackInHand(hand, new ItemStack(bucket, 1));
                       }
                       world.playSound((PlayerEntity) null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 1.0f, world.random.nextFloat() * 0.05f + 0.95f);
                   }
               }
           }
           else if (blockEntity != null && !player.isInSneakingPose() && player.getStackInHand(player.getActiveHand()).getItem() != Items.BUCKET) {
               ContainerProviderRegistry.INSTANCE.openContainer(GID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
           }
       }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BarrelEntity) {
            ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)((BarrelEntity)blockEntity));
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void addAllAttributes(World world, BlockPos pos, BlockState state, AttributeList<?> to) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof BarrelEntity) {
            BarrelEntity tank = (BarrelEntity) be;
            tank.fluidInv.offerSelfAsAttribute(to, null, null);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BarrelEntity();
    }
}
