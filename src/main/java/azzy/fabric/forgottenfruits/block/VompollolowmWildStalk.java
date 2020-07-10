package azzy.fabric.forgottenfruits.block;

import azzy.fabric.forgottenfruits.registry.CropRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class VompollolowmWildStalk extends WildPlantBase {

    private final VoxelShape shape;

    public VompollolowmWildStalk(String type, Material material, BlockSoundGroup sound, ParticleEffect effects, double flight, int count, float donotusethis, int dispersion, VoxelShape shape, StatusEffect... touchEffects) {
        super(type, material, sound, effects, flight, count, donotusethis, dispersion, touchEffects);
        this.shape = shape;
    }


    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        world.getBlockTickScheduler().schedule(pos.down(), world.getBlockState(pos.down()).getBlock(), 1);
        world.getBlockTickScheduler().schedule(pos.up(), world.getBlockState(pos.up()).getBlock(), 1);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
        return shape;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor == this.getDefaultState() || floor == CropRegistry.VOMPOLLOLOWM_WILD_BASE.getDefaultState() || floor.isAir();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState floor = world.getBlockState(pos.down());
        BlockState ceil = world.getBlockState(pos.up());
        if((floor == CropRegistry.VOMPOLLOLOWM_WILD_BASE.getDefaultState() && !ceil.isAir()) || (ceil == CropRegistry.VOMPOLLOLOWM_WILD_STALK.getDefaultState() && floor == CropRegistry.VOMPOLLOLOWM_WILD_STALK.getDefaultState()))
            return;
        onBroken(world, pos, state);
        world.breakBlock(pos, false);
        super.scheduledTick(state, world, pos, random);
    }
}
