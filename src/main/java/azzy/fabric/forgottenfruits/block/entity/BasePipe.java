package azzy.fabric.forgottenfruits.block.entity;

import azzy.fabric.forgottenfruits.block.BaseMachine;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.Waterloggable;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class BasePipe extends BaseMachine implements Waterloggable {

    private static final BooleanProperty NORTH = BooleanProperty.of("north");
    private static final BooleanProperty SOUTH = BooleanProperty.of("south");
    private static final BooleanProperty EAST = BooleanProperty.of("east");
    private static final BooleanProperty WEST = BooleanProperty.of("west");
    private static final BooleanProperty UP = BooleanProperty.of("up");
    private static final BooleanProperty DOWN = BooleanProperty.of("down");

    private static final EnumProperty<Pump> PUMP = EnumProperty.of("pump", Pump.class);
    private static final BooleanProperty GATE = BooleanProperty.of("gate");
    private static final EnumProperty<Pump> FILTER = EnumProperty.of("filter", Pump.class);
    private static final BooleanProperty VALVE = BooleanProperty.of("valve");
    private static final BooleanProperty CLOSED = BooleanProperty.of("closed");
    private static final BooleanProperty SHIELD = BooleanProperty.of("shield");

    private static final BooleanProperty CORE = BooleanProperty.of("core");

    public BasePipe(Settings settings, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(material, sound, glow, bounds, effects);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    }

    public enum Pump implements StringIdentifiable {
        NONE,
        PUMP,
        ITEM;

        @Override
        public String asString() {
            return name().toLowerCase();
        }
    }
}
