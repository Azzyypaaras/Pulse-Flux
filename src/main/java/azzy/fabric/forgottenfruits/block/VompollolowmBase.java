package azzy.fabric.forgottenfruits.block;

import azzy.fabric.forgottenfruits.registry.CropRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class VompollolowmBase extends PlantBase {

    private int minLight, maxLight;

    public VompollolowmBase(int stages, Material material, BlockSoundGroup sound, ItemConvertible seeds, int minLight, int maxLight, ParticleEffect effects, double flight, int count, float donotusethis, int dispersion) {
        super(stages, material, sound, seeds, minLight, maxLight, effects, flight, count, donotusethis, dispersion);
        this.minLight = minLight;
        this.maxLight = maxLight;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(this.getAge(state) == 3)
            return VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1, 0.5625);
        return VoxelShapes.cuboid(0, 0, 0, 0, 0, 0);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState ceil = world.getBlockState(pos.up());
        if(ceil.getBlock() != CropRegistry.VOMPOLLOLOWM_CROP_STALK)
            world.setBlockState(pos, state.with(AGE, 2));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        world.getBlockTickScheduler().schedule(pos.up(), world.getBlockState(pos.up()).getBlock(), 1);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(this.isMature(state))
            if(world.getRandom().nextInt(3) == 0 && world.isAir(pos.up())) {
                world.setBlockState(pos.up(), CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState());
                return;
            }
        super.randomTick(state, world, pos, random);
    }
}
