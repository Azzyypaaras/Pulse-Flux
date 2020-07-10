package azzy.fabric.forgottenfruits.block;

import azzy.fabric.forgottenfruits.registry.CropRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class VompollolowmWildBase extends WildPlantBase {
    public VompollolowmWildBase(String type, Material material, BlockSoundGroup sound, ParticleEffect effects, double flight, int count, float donotusethis, int dispersion, StatusEffect... touchEffects) {
        super(type, material, sound, effects, flight, count, donotusethis, dispersion, touchEffects);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1, 0.5625);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        world.getBlockTickScheduler().schedule(pos.up(), world.getBlockState(pos.up()).getBlock(), 1);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState ceil = world.getBlockState(pos.up());
        if(ceil == CropRegistry.VOMPOLLOLOWM_WILD_STALK.getDefaultState())
            return;
        onBroken(world, pos, state);
        world.breakBlock(pos, true);
        super.scheduledTick(state, world, pos, random);
    }

}
