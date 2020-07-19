package azzy.fabric.forgottenfruits.block.Plant;

import azzy.fabric.forgottenfruits.block.NetherFarmland;
import azzy.fabric.forgottenfruits.registry.BlockRegistry;
import azzy.fabric.forgottenfruits.registry.CropRegistry;
import azzy.fabric.forgottenfruits.util.context.PlantPackage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.explosion.Explosion;

import static azzy.fabric.forgottenfruits.registry.CropRegistry.VOMPOLLOLOWM_CROP_BASE;
import static net.minecraft.block.CropBlock.AGE;

public class PlantReferences {

    public static void cindermoteCollision(PlantPackage plantPackage){
        World world = plantPackage.world;
        BlockPos pos = plantPackage.pos;
        Entity entity = plantPackage.entity;

        if (!world.isClient)
            if ((entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) || entity.isSprinting()) {
                world.createExplosion(null, DamageSource.IN_FIRE, null, pos.getX(), pos.getY(), pos.getZ(), 6f, false, Explosion.DestructionType.NONE);
            } else if (!entity.isInSneakingPose() && entity.getType() != EntityType.ITEM) {
                entity.setOnFireFor(20);
                entity.damage(DamageSource.LAVA, 2f);
            }
    }

    public static void cindermoteParticles(PlantPackage plantPackage){
        ServerWorld world = (ServerWorld) plantPackage.world;
        BlockPos pos = plantPackage.pos;
        world.spawnParticles(ParticleTypes.FLAME, pos.getX() - 10, pos.getY() - 5, pos.getZ() - 10, 8 + world.random.nextInt(8), 20, 30, 20, 0.05);
    }

    public static void vompollolowmBaseTick(PlantPackage plantPackage){
        World world = plantPackage.world;
        BlockPos pos = plantPackage.pos;
        BlockState state = plantPackage.state;
        BlockState ceil = world.getBlockState(pos.up());

        if(ceil.getBlock() != CropRegistry.VOMPOLLOLOWM_CROP_STALK)
            world.setBlockState(pos, state.with(AGE, 2));
    }

    public static void vompollolowmBreakTick(PlantPackage plantPackage){
        World world = plantPackage.world;
        BlockPos pos = plantPackage.pos;

        world.getBlockTickScheduler().schedule(pos.down(), world.getBlockState(pos.down()).getBlock(), 1);
        world.getBlockTickScheduler().schedule(pos.up(), world.getBlockState(pos.up()).getBlock(), 1);
        world.breakBlock(pos, true);
    }

    public static void vompollolowmBaseGrowth(PlantPackage plantPackage){
        BlockState state = plantPackage.state;
        World world = plantPackage.world;
        BlockPos pos = plantPackage.pos;

        if(state.get(AGE) == 3) {
            if (world.getRandom().nextInt(3) == 0 && world.isAir(pos.up())) {
                world.setBlockState(pos.up(), CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState());
            }
        }
        else{
            int i = state.get(AGE);
            if (i < 3) {
                float f = getAvailableMoisture(state.getBlock(), world, pos);
                if (world.random.nextInt((int) (25.0F / f) + 1) == 0) {
                    world.setBlockState(pos, VOMPOLLOLOWM_CROP_BASE.getDefaultState().with(AGE, i + 1), 2);
                }
            }
        }
    }

    public static void vompollolowmStalkGrowth(PlantPackage plantPackage){
        BlockState state = plantPackage.state;
        World world = plantPackage.world;
        BlockPos pos = plantPackage.pos;

        BlockState minHeight = world.getBlockState(pos.down(7));
        if (world.getRandom().nextInt(3) == 0 && world.isAir(pos.up()) && state.get(AGE) == 1)
            world.setBlockState(pos.up(), CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState());

        else if(!(state.get(AGE) == 1) && world.isAir(pos.up())) {
            if ((minHeight == CropRegistry.VOMPOLLOLOWM_CROP_BASE.getDefaultState().with(AGE, 3) || minHeight == CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState().with(AGE, 1)) && world.getRandom().nextInt(15) == 0) {
                world.setBlockState(pos, CropRegistry.VOMPOLLOLOWM_CROP_FRUIT.getDefaultState());
                return;
            }
            world.setBlockState(pos, CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState().with(AGE, 1), 3);
            world.setBlockState(pos.up(), CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState());

        }
    }

    public static void vompollolowmScheduler(PlantPackage plantPackage){
        WorldAccess world = plantPackage.world;
        BlockPos pos = plantPackage.pos;
        world.getBlockTickScheduler().schedule(pos.down(), world.getBlockState(pos.down()).getBlock(), 1);
        world.getBlockTickScheduler().schedule(pos.up(), world.getBlockState(pos.up()).getBlock(), 1);
    }

    private static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockPos = pos.down();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float g = 0.0F;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.isOf(Blocks.FARMLAND)) {
                    g = 1.0F;
                    if (blockState.get(FarmlandBlock.MOISTURE) > 0) {
                        g = 3.0F;
                    }
                } else if (blockState.isOf(BlockRegistry.NETHER_FARMLAND)) {
                    g = 1.0F;
                    if (blockState.get(NetherFarmland.MOISTURE) > 0) {
                        g = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    g /= 4.0F;
                }

                f += g;
            }
        }

        BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = block == world.getBlockState(blockPos4).getBlock() || block == world.getBlockState(blockPos5).getBlock();
        boolean bl2 = block == world.getBlockState(blockPos2).getBlock() || block == world.getBlockState(blockPos3).getBlock();
        if (bl && bl2) {
            f /= 2.0F;
        } else {
            boolean bl3 = block == world.getBlockState(blockPos4.north()).getBlock() || block == world.getBlockState(blockPos5.north()).getBlock() || block == world.getBlockState(blockPos5.south()).getBlock() || block == world.getBlockState(blockPos4.south()).getBlock();
            if (bl3) {
                f /= 2.0F;
            }
        }

        return f;
    }

}
