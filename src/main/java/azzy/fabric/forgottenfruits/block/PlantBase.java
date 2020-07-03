package azzy.fabric.forgottenfruits.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.Random;

public class PlantBase extends CropBlock {
    private final int maxAge;
    private int minLight;
    private int maxLight;
    private ItemConvertible seeds;
    private ParticleEffect effects;
    private double flight;
    private int count;
    private String type;
    private float donotusethis;
    private int dispersion;

    public PlantBase(String type, int stages, Material material, BlockSoundGroup sound, ItemConvertible seeds, int minLight, int maxLight, ParticleEffect effects, double flight, int count, float donotusethis, int dispersion){
        super(FabricBlockSettings.of(material).sounds(sound).breakInstantly().ticksRandomly().build().noCollision());
        maxAge = stages-1;
        this.setDefaultState((this.getStateManager().getDefaultState()).with(this.getAgeProperty(), 0));
        this.minLight = minLight;
        this.maxLight = maxLight;
        this.effects = effects;
        this.seeds = seeds;
        this.flight = flight;
        this.count = count;
        this.type = type;
        this.donotusethis = donotusethis;
        this.dispersion = dispersion;
    }

    @Override
    public int getMaxAge(){
        return maxAge;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
        return VoxelShapes.cuboid(0.2f, 0f, 0.2f, 0.9f, 0.8f, 0.8f);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return seeds;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }

    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        int currentLight = world.getBaseLightLevel(pos, 0);
        if (currentLight >= minLight && currentLight <=maxLight) {
            if(effects != null)
                world.spawnParticles(effects, (double) pos.getX()+Math.random(), (double) pos.getY()+(Math.random()/2), (double) pos.getZ()+Math.random(), (int) (Math.random()*5)+1+count, dispersion, flight, dispersion, donotusethis);
            //This will probably break shit
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(i + 1), 2);
                }
            }
        }
    }
}
