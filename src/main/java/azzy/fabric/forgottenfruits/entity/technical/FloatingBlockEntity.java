package azzy.fabric.forgottenfruits.entity.technical;

import azzy.fabric.forgottenfruits.registry.EntityRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;
import java.util.stream.DoubleStream;

public class FloatingBlockEntity extends FallingBlockEntity {

    //Don't ask
    private FloatingBlockEntity(World world){
        super(EntityRegistry.FLOATING_BLOCK, world);
    }

    private BlockState block;
    private boolean destroyedOnLanding;
    private boolean hurtEntities;
    private int fallHurtMax;
    private float fallHurtAmount;
    protected static final TrackedData<BlockPos> BLOCK_POS;
    private float bouyancy;

    private FloatingBlockEntity(EntityType<? extends FloatingBlockEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
        this.dropItem = true;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0F;
        double velX, velZ, velY;

        Random random = world.getRandom();
        if(random.nextInt(1) == 0)
            velX = random.nextDouble();
        else
            velX = random.nextDouble() * -1.0;

        if(random.nextInt(1) == 0)
            velY = Math.max(random.nextDouble() / 5, 0.05);
        else
            velY = Math.max(random.nextDouble() / 5, 0.05) * -1.0;

        if(random.nextInt(1) == 0)
            velZ = random.nextDouble();
        else
            velZ = random.nextDouble() * -1.0;

        this.setVelocity(velX / 2.0, velY, velZ / 2.0);
    }

    @Override
    public void tick() {
        if (this.block.isAir() || this.getPos().y > 256) {
            this.remove();
        }
        else{
            Block block = this.block.getBlock();
            BlockPos blockPos2;
            if (this.timeFalling++ == 0) {
                blockPos2 = this.getBlockPos();
                if (this.world.getBlockState(blockPos2).isOf(block)) {
                    this.world.removeBlock(blockPos2, false);
                }
            }
            this.setVelocity(this.getVelocity().x, calcSpeedChange(), this.getVelocity().z);
        }
    }

    public FloatingBlockEntity(World world, double x, double y, double z, BlockState block, float bouyancy) {
        this(EntityRegistry.FLOATING_BLOCK, world);
        this.block = block;
        this.inanimate = true;
        this.updatePosition(x, y + (double)((1.0F - this.getHeight()) / 2.0F), z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.setFallingBlockPos(this.getBlockPos());
        this.bouyancy = bouyancy;
    }

    @Override
    public void setFallingBlockPos(BlockPos pos) {
        this.dataTracker.set(BLOCK_POS, pos);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public BlockPos getFallingBlockPos() {
        return (BlockPos)this.dataTracker.get(BLOCK_POS);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(BLOCK_POS, BlockPos.ORIGIN);
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.put("BlockState", NbtHelper.fromBlockState(this.block));
        tag.putInt("Time", this.timeFalling);
        tag.putBoolean("DropItem", this.dropItem);
        tag.putBoolean("HurtEntities", this.hurtEntities);
        tag.putFloat("FallHurtAmount", this.fallHurtAmount);
        tag.putInt("FallHurtMax", this.fallHurtMax);
        if (this.blockEntityData != null) {
            tag.put("TileEntityData", this.blockEntityData);
        }

    }

    private double calcSpeedChange(){
        double speed = this.getVelocity().y;
        double fest = Math.pow(speed, bouyancy + 1);
        return Math.min(fest, 9.0);
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        this.block = NbtHelper.toBlockState(tag.getCompound("BlockState"));
        this.timeFalling = tag.getInt("Time");
        if (tag.contains("HurtEntities", 99)) {
            this.hurtEntities = tag.getBoolean("HurtEntities");
            this.fallHurtAmount = tag.getFloat("FallHurtAmount");
            this.fallHurtMax = tag.getInt("FallHurtMax");
        } else if (this.block.isIn(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }

        if (tag.contains("DropItem", 99)) {
            this.dropItem = tag.getBoolean("DropItem");
        }

        if (tag.contains("TileEntityData", 10)) {
            this.blockEntityData = tag.getCompound("TileEntityData");
        }

        if (this.block.isAir()) {
            this.block = Blocks.SAND.getDefaultState();
        }

    }

    @Override
    public BlockState getBlockState() {
        return this.block;
    }

    static {
        BLOCK_POS = DataTracker.registerData(FloatingBlockEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    }

}
