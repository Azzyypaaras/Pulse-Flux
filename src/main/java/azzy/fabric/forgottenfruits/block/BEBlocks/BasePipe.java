package azzy.fabric.forgottenfruits.block.BEBlocks;

import azzy.fabric.forgottenfruits.block.BaseMachine;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
public class BasePipe extends BaseMachine implements Waterloggable {

    private BooleanProperty north = BooleanProperty.of("north");
    private BooleanProperty south = BooleanProperty.of("south");
    private BooleanProperty east = BooleanProperty.of("east");
    private BooleanProperty west = BooleanProperty.of("west");
    private BooleanProperty up = BooleanProperty.of("up");
    private BooleanProperty down= BooleanProperty.of("down");
    private BooleanProperty inline_NS = BooleanProperty.of("inline-north");
    private BooleanProperty inline_EW = BooleanProperty.of("inline-west");
    private BooleanProperty inline_UD = BooleanProperty.of("inline-vertical");

    private BooleanProperty pump = BooleanProperty.of("pump");
    private BooleanProperty itemPump = BooleanProperty.of("item-pump");
    private BooleanProperty gate = BooleanProperty.of("gate");
    private BooleanProperty filter = BooleanProperty.of("filter");
    private BooleanProperty itemFilter = BooleanProperty.of("item-filter");
    private BooleanProperty valve = BooleanProperty.of("valve");
    private BooleanProperty closed = BooleanProperty.of("closed");
    private BooleanProperty shield = BooleanProperty.of("shield");

    private BooleanProperty core = BooleanProperty.of("core");

    private boolean connectionNorth, connectionSouth, connectionEast, connectionWest, connectionUp, connectionDown, isInline, isCore, isCoreHidden, hasPump, hasItemPump, hasFilter, hasItemFilter, hasValve, hasGate, isClosed, hasBlastShield;

    public BooleanProperty[] connections = new BooleanProperty[9];
    public BooleanProperty[] components = new BooleanProperty[8];

    public BasePipe(Settings settings, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(settings, material, sound, glow, bounds, effects);
        connections[0] = north;
        connections[1] = south;
        connections[2] = east;
        connections[3] = west;
        connections[4] = up;
        connections[5] = down;
        connections[6] = inline_NS;
        connections[7] = inline_EW;
        connections[8] = inline_UD;
    }


    //Make it so this drops all internal components
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return null;
    }
}
