package azzy.fabric.azzyfruits.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.dimension.DimensionType;

import java.util.Iterator;
import java.util.Random;

public class NetherFarmland extends FarmlandBlock {
    public NetherFarmland(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(MOISTURE, 0));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? Blocks.OBSIDIAN.getDefaultState() : super.getPlacementState(ctx);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = (Integer)state.get(MOISTURE);
        world.spawnParticles(ParticleTypes.SMOKE, (double) pos.getX()+Math.random(), (double) pos.getY()+0.5, (double) pos.getZ()+Math.random(), (int) (Math.random()*8)+4, 0.2D, 0.2, 0.2D, 0D);
        if (!isWaterNearby(world, pos) || world.hasRain(pos.up())) {
            if (i > 0) {
                world.setBlockState(pos, (BlockState)state.with(MOISTURE, i - 1), 2);
            }
        } else if (i < 7) {
            world.setBlockState(pos, (BlockState)state.with(MOISTURE, 7), 2);
        }
        if(i == 7 && world.getDimension().getType() == DimensionType.THE_NETHER){
            world.getBlockState(pos.add(0, 1, 0)).scheduledTick(world, pos.add(0, 1, 0), random);
            world.spawnParticles(ParticleTypes.FLAME, (double) pos.getX()+Math.random(), (double) pos.getY()+0.5, (double) pos.getZ()+Math.random(), (int) (Math.random()*4)+2, 0.1, 0.75, 0.1, 0);
        }
        else if (i == 7){
            world.spawnParticles(ParticleTypes.LARGE_SMOKE, (double) pos.getX()+Math.random(), (double) pos.getY()+0.5, (double) pos.getZ()+Math.random(), (int) (Math.random()*4)+2, 0.1, 0.75, 0.1, 0);
        }
    }

    @Override
    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        int i = (Integer)world.getBlockState(pos).get(MOISTURE);
        if (!world.isClient && world.random.nextFloat() < distance - 0.5F && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F) {
            if(i < 7)
                world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
            else
                world.setBlockState(pos, Blocks.LAVA.getDefaultState());
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        int i = (Integer)state.get(MOISTURE);
        if(i > 6 && world.getDimension().getType() == DimensionType.THE_NETHER){
            if(!entity.isInSneakingPose() && entity.getType() != EntityType.ITEM) {
                entity.setOnFireFor(10);
                entity.damage(DamageSource.LAVA, 4f);
            }
            else if(entity.getType() != EntityType.ITEM){
                entity.damage(DamageSource.LAVA, 1f);
            }
        }
        else if (i > 6 && !entity.isInSneakingPose() && entity.getType() != EntityType.ITEM) {
            entity.setOnFireFor(5);
            entity.damage(DamageSource.LAVA, 1f);
        }

    }

    protected static boolean isWaterNearby(WorldView worldView, BlockPos pos) {
        Iterator var2;
        if(worldView.getDimension().getType() == DimensionType.THE_NETHER)
            var2 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 0, 4)).iterator();
        else
            var2 = BlockPos.iterate(pos.add(-2, 0, -2), pos.add(2, 0, 2)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos = (BlockPos)var2.next();
        } while(!worldView.getFluidState(blockPos).matches(FluidTags.LAVA));

        return true;
    }
}
