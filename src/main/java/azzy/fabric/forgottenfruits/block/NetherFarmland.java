package azzy.fabric.forgottenfruits.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;

import java.util.Iterator;
import java.util.Random;

public class NetherFarmland extends Block {

    public static final IntProperty MOISTURE;
    protected static final VoxelShape SHAPE;

    static {
        MOISTURE = Properties.MOISTURE;
        SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? Blocks.OBSIDIAN.getDefaultState() : super.getPlacementState(ctx);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public NetherFarmland(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(MOISTURE, 0));
    }

    protected static boolean noWaterNearby(WorldView worldView, BlockPos pos) {
        int r = ((World) worldView).getRegistryKey() == World.NETHER ? 4 : 2;
        Iterator<BlockPos> var2 = BlockPos.iterate(pos.add(-r, 0, -r), pos.add(r, 0, r)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return true;
            }

            blockPos = var2.next();
        } while (!worldView.getFluidState(blockPos).isIn(FluidTags.LAVA));

        return false;
    }

    public void setToDirt(ServerWorld world, BlockPos pos) {
        world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(MOISTURE);
        world.spawnParticles(ParticleTypes.SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.5, (double) pos.getZ() + Math.random(), (int) (Math.random() * 8) + 4, 0.2D, 0.2, 0.2D, 0D);
        if ((i == 0 && random.nextInt(3) == 0) && noWaterNearby(world, pos)) {
            setToDirt(world, pos);
            return;
        }
        if (noWaterNearby(world, pos) || world.hasRain(pos.up())) {
            if (i > 0) {
                world.setBlockState(pos, state.with(MOISTURE, i - 1), 2);
            }
        } else if (i < 7) {
            world.setBlockState(pos, state.with(MOISTURE, 7), 2);
        }
        if (i == 7 && world.getRegistryKey() == World.NETHER) {
            world.getBlockState(pos.add(0, 1, 0)).scheduledTick(world, pos.add(0, 1, 0), random);
            world.spawnParticles(ParticleTypes.FLAME, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.5, (double) pos.getZ() + Math.random(), (int) (Math.random() * 4) + 2, 0.1, 0.75, 0.1, 0);
        } else if (i == 7) {
            world.spawnParticles(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + Math.random(), (double) pos.getY() + 0.5, (double) pos.getZ() + Math.random(), (int) (Math.random() * 4) + 2, 0.1, 0.75, 0.1, 0);
        }
    }

    @Override
    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        int i = world.getBlockState(pos).get(MOISTURE);
        if (!world.isClient && world.random.nextFloat() < distance - 0.5F && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F) {
            if (i < 7)
                world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
            else
                world.setBlockState(pos, Blocks.LAVA.getDefaultState());
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        int i = state.get(MOISTURE);
        if (i > 6 && world.getRegistryKey() == World.NETHER) {
            if (!entity.isInSneakingPose() && entity.getType() != EntityType.ITEM) {
                entity.setOnFireFor(10);
                entity.damage(DamageSource.LAVA, 4f);
            } else if (entity.getType() != EntityType.ITEM) {
                entity.damage(DamageSource.LAVA, 1f);
            }
        } else if (i > 6 && !entity.isInSneakingPose() && entity.getType() != EntityType.ITEM) {
            entity.setOnFireFor(5);
            entity.damage(DamageSource.LAVA, 1f);
        }

    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }
}
