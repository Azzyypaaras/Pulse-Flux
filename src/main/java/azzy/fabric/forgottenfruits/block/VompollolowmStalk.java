package azzy.fabric.forgottenfruits.block;

import azzy.fabric.forgottenfruits.registry.CropRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class VompollolowmStalk extends PlantBase {

    private final VoxelShape shape;

    public VompollolowmStalk(int stages, Material material, BlockSoundGroup sound, ItemConvertible seeds, int minLight, int maxLight, ParticleEffect effects, double flight, int count, float donotusethis, int dispersion, VoxelShape shape) {
        super(stages, material, sound, seeds, minLight, maxLight, effects, flight, count, donotusethis, dispersion, shape);
        this.shape = shape;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor == this.withAge(1) || floor == CropRegistry.VOMPOLLOLOWM_CROP_BASE.getDefaultState().with(AGE, 3) || floor.isAir();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState floor = world.getBlockState(pos.down());
        BlockState ceil = world.getBlockState(pos.up());
        if((state.get(AGE) == 0 && !floor.isAir()) || (ceil == CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState().with(AGE, 1) && floor == CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState().with(AGE, 1)))
            return;
        onBroken(world, pos, state);
        world.breakBlock(pos, false);
        super.scheduledTick(state, world, pos, random);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        world.getBlockTickScheduler().schedule(pos.down(), world.getBlockState(pos.down()).getBlock(), 1);
        world.getBlockTickScheduler().schedule(pos.up(), world.getBlockState(pos.up()).getBlock(), 1);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState minHeight = world.getBlockState(pos.down(7));
        if (world.getRandom().nextInt(3) == 0 && world.isAir(pos.up()) && this.isMature(state))
                world.setBlockState(pos.up(), CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState());

        else if(!this.isMature(state) && world.isAir(pos.up())) {
            if ((minHeight == CropRegistry.VOMPOLLOLOWM_CROP_BASE.getDefaultState().with(AGE, 3) || minHeight == CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState().with(AGE, 1)) && random.nextInt(15) == 0) {
                world.setBlockState(pos, CropRegistry.VOMPOLLOLOWM_CROP_FRUIT.getDefaultState());
                return;
            }
            world.setBlockState(pos, this.withAge(this.getAge(state) + 1), 2);
            world.setBlockState(pos.up(), CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState());

        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }
}
