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

public class VompollolowmWildFruit extends WildPlantBase {

    private final VoxelShape shape;

    public VompollolowmWildFruit(String type, Material material, BlockSoundGroup sound, ParticleEffect effects, double flight, int count, float donotusethis, int dispersion, VoxelShape shape, StatusEffect... touchEffects) {
        super(type, material, sound, effects, flight, count, donotusethis, dispersion, touchEffects);
        this.shape = shape;
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
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //    if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0) {
        //        FloatingBlockEntity fruit = new FloatingBlockEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, world.getBlockState(pos), 1);
        //        world.spawnEntity(fruit);
        //
        //    }
        world.breakBlock(pos, true);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor == CropRegistry.VOMPOLLOLOWM_WILD_STALK.getDefaultState() || floor.isAir();
    }

}
