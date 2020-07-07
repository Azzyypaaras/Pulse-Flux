package azzy.fabric.forgottenfruits.block;

import azzy.fabric.forgottenfruits.registry.CropRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

import static net.minecraft.block.FallingBlock.canFallThrough;

public class VompollolowmFruit extends PlantBase{

    private final VoxelShape shape;

    public VompollolowmFruit(int stages, Material material, BlockSoundGroup sound, ItemConvertible seeds, int minLight, int maxLight, ParticleEffect effects, double flight, int count, float donotusethis, int dispersion, VoxelShape shape) {
        super(stages, material, sound, seeds, minLight, maxLight, effects, flight, count, donotusethis, dispersion, shape);
        this.shape = shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0) {
            FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, world.getBlockState(pos));
            world.spawnEntity(fallingBlockEntity);
        }
    }


    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor == CropRegistry.VOMPOLLOLOWM_CROP_STALK.getDefaultState().with(AGE, 1) || floor.isAir();
    }
}
