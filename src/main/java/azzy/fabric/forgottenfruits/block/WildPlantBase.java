package azzy.fabric.forgottenfruits.block;

import azzy.fabric.forgottenfruits.registry.CropRegistry;
import azzy.fabric.forgottenfruits.util.context.ContextConsumer;
import azzy.fabric.forgottenfruits.util.context.ContextMap;
import azzy.fabric.forgottenfruits.util.context.PlantPackage;
import azzy.fabric.forgottenfruits.util.interaction.PlantType.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class WildPlantBase extends PlantBlock implements FluidFillable{

    private final PLANTTYPE type;
    private static final VoxelShape DEFAULT = VoxelShapes.cuboid(0.2f, 0f, 0.2f, 0.8f, 0.8f, 0.8f);;
    private final VoxelShape shape;
    private final ContextMap<PlantPackage, PlantPackage.Context> contextConsumers;
    private final boolean solid;
    private final FluidState fluid;

    public WildPlantBase(PLANTTYPE type, Material material, BlockSoundGroup sound, ContextConsumer ... consumers) {
        super(FabricBlockSettings.of(material).sounds(sound).breakInstantly().ticksRandomly().noCollision());
        this.type = type;
        this.contextConsumers = ContextMap.construct(consumers);
        this.shape = DEFAULT;
        this.solid = false;
        this.fluid = Fluids.EMPTY.getDefaultState();
    }

    public WildPlantBase(PLANTTYPE type, Material material, BlockSoundGroup sound, VoxelShape shape, boolean solid, ContextConsumer ... consumers) {
        super(FabricBlockSettings.of(material).sounds(sound).breakInstantly().ticksRandomly().noCollision());
        this.type = type;
        this.contextConsumers = ContextMap.construct(consumers);
        if(shape != null)
            this.shape = shape;
        else
            this.shape = DEFAULT;
        this.solid = solid;
        this.fluid = Fluids.EMPTY.getDefaultState();
    }

    public WildPlantBase(PLANTTYPE type, Material material, BlockSoundGroup sound, VoxelShape shape, boolean solid, FluidState fluid, ContextConsumer ... consumers) {
        super(FabricBlockSettings.of(material).sounds(sound).breakInstantly().ticksRandomly().noCollision());
        this.type = type;
        this.contextConsumers = ContextMap.construct(consumers);
        if(shape != null)
            this.shape = shape;
        else
            this.shape = DEFAULT;
        this.solid = solid;
        this.fluid = fluid;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView view, BlockPos pos) {
        Block block = floor.getBlock();
        return type.contains(block);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return solid ? shape : VoxelShapes.empty();
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        contextConsumers.execute(new PlantPackage(state, world, pos, entity), PlantPackage.Context.COLLISION);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        contextConsumers.execute(new PlantPackage(state, world, pos, null), PlantPackage.Context.DISPlAY);
    }

    @Override
    public void rainTick(World world, BlockPos pos) {
        contextConsumers.execute(new PlantPackage(null, world, pos, null), PlantPackage.Context.RAIN);
    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        contextConsumers.execute(new PlantPackage(state, world, pos, null), PlantPackage.Context.SCHEDULED);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        contextConsumers.execute(new PlantPackage(state, world, pos, player), PlantPackage.Context.BROKEN);
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        contextConsumers.execute(new PlantPackage(state, world, pos, null), PlantPackage.Context.PARTICLE);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if(fluid != Fluids.EMPTY.getDefaultState()){
            BlockState blockState = super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
            if (!blockState.isAir()) {
                world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }
            return blockState;
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return fluid;
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }
}
