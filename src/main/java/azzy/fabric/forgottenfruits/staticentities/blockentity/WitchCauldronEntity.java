package azzy.fabric.forgottenfruits.staticentities.blockentity;

import azzy.fabric.forgottenfruits.registry.ItemRegistry;
import azzy.fabric.forgottenfruits.registry.ParticleRegistry;
import azzy.fabric.forgottenfruits.util.interaction.HeatHolder;
import azzy.fabric.forgottenfruits.util.interaction.HeatTransferHelper;
import azzy.fabric.forgottenfruits.util.tracker.BrewMetadata;
import azzy.fabric.forgottenfruits.util.tracker.BrewingTracker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

import static azzy.fabric.forgottenfruits.ForgottenFruits.FFLog;
import static azzy.fabric.forgottenfruits.ForgottenFruits.config;
import static azzy.fabric.forgottenfruits.block.BEBlocks.WitchCauldronBlock.CHAOTIC;
import static azzy.fabric.forgottenfruits.block.BEBlocks.WitchCauldronBlock.EFFULGENT;
import static azzy.fabric.forgottenfruits.registry.BlockEntityRegistry.WITCH_CAULDRON_ENTITY;

public class WitchCauldronEntity extends MachineEntity implements HeatHolder {

    private BrewMetadata metadata;
    private boolean hasMetadata;
    private String cachedBrew;
    private int cachedColor;
    private double heat;
    private BrewingTracker tracker;
    //Yeah so, you can't get world on init :p
    private boolean heatInit = true;

    public WitchCauldronEntity() {
        super(WITCH_CAULDRON_ENTITY);
        inventory = DefaultedList.ofSize(512, ItemStack.EMPTY);
        hasMetadata  = false;
    }

    @Override
    public void tick() {
        if (metadata == null) {
            hasMetadata = false;
        } else if (cachedColor == 0xFFFFFF || cachedColor == 0) {
            cachedColor = metadata.getColor();
        }
        if (hasWorld()) {
            World world = Objects.requireNonNull(getWorld());

            if (heatInit) {
                heat = HeatTransferHelper.translateBiomeHeat(world.getBiome(this.pos));
                heatInit = false;
            }
            if (world.isClient() && heat >= 100 && world.getRandom().nextInt(9) == 0) {
                spawnParticles();
            }
            if (world.getTime() % 5 == 0 && hasMetadata) {
                fetchDroppedItems();

                if (world.getTime() % 20 == 0) {
                    if (config.isDebug())
                        FFLog.error("Cauldron Temperature: " + heat);
                    Block source = world.getBlockState(pos.down()).getBlock();
                    BlockEntity entity = world.getBlockEntity(pos.down());
                    if (entity instanceof HeatHolder) {
                        HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, this, (HeatHolder) entity);
                    } else if (HeatTransferHelper.isHeatSource(source)) {
                        HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, this, source);
                    }
                    HeatTransferHelper.simulateAmbientHeat(this, world.getBiome(pos));
                }
            }
        }

        super.tick();
    }

    public String getCachedBrew() {
        return cachedBrew;
    }

    public void setCachedBrew(String cachedBrew) {
        this.cachedBrew = cachedBrew;
    }

    public void spawnParticles() {
        if (hasWorld()) {
            int variation = Objects.requireNonNull(world).getRandom().nextInt(17);
            for (int i = 0; i < 1 + variation; i++) {
                double positionX = world.getRandom().nextInt(7) / 10.0;
                double positionZ = world.getRandom().nextInt(7) / 10.0;
                Particle particle = MinecraftClient.getInstance().particleManager.addParticle(ParticleRegistry.CAULDRON_BUBBLES, pos.getX() + 0.2 + positionX, pos.getY() + 0.6875, pos.getZ() + 0.2 + positionZ, 0, 0.0, 0);
                if (particle != null) {
                    int color = metadata.getColor();
                    int r = color >> 16 & 0xFF;
                    int b = color >> 8 & 0xFF;
                    int g = color & 0xFF;
                    particle.setColor(r / 255f, g / 255f, b / 255f);
                    MinecraftClient.getInstance().particleManager.addParticle(particle);
                    particle.setMaxAge(10 + world.getRandom().nextInt(40));
                }
            }
        }
    }

    public void fetchDroppedItems() {
        List<ItemEntity> items = Objects.requireNonNull(getWorld()).getNonSpectatingEntities(ItemEntity.class, new Box(this.pos.add(0, 0.6875, 0), this.pos.add(1, 1, 1)));
        items.forEach(e -> {
            for (int i = 1; i < inventory.size(); i++) {
                ItemStack stack = inventory.get(i);
                ItemStack drops = e.getStack();
                if (stack.isEmpty()) {
                    inventory.set(i, drops);
                    e.kill();
                    playDropEffects();
                    break;
                } else if (drops.getItem() == stack.getItem() && stack.getCount() < 64) {
                    if (drops.getCount() + stack.getCount() <= 64) {
                        inventory.get(i).increment(drops.getCount());
                        e.kill();
                        playDropEffects();
                    }
                    break;
                }
            }
        });
    }

    public void setHasMetadata(boolean bool){
        hasMetadata = bool;
    }

    public boolean hasMetadata() {
        return hasMetadata;
    }

    public BrewMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(BrewMetadata metadata, Identifier brew) {
        this.metadata = metadata;
        this.cachedBrew = brew.toString();
    }

    //This is unused
    public BrewingTracker getTracker() {
        return tracker;
    }

    public int getCachedColor() {
        return cachedColor;
    }

    public void playDropEffects() {
        if(world instanceof ServerWorld){
            ((ServerWorld) world).spawnParticles(ParticleTypes.SPLASH, this.pos.getX()+0.5, this.pos.getY()+0.69, this.pos.getZ()+0.5, world.random.nextInt(5)+2, 0, 0.0, 0, 0);
        } else {
            world.playSound(this.pos.getX() + 0.5, this.pos.getY() + 0.7, this.pos.getZ() + 0.5, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.9f, 1.1f, false);
        }
    }

    public void updateGem(){
        Objects.requireNonNull(this.world).setBlockState(this.pos, this.getCachedState().with(EFFULGENT, inventory.get(0).getItem() == ItemRegistry.ATTUNED_EFFULGENT).with(CHAOTIC, inventory.get(0).getItem() == ItemRegistry.ATTUNED_CHAOTIC));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(hasMetadata)
            tag.put("metadata", metadata.toTag());
        tag.putDouble("heat", heat);
        tag.putBoolean("init", heatInit);
        tag.putBoolean("hasmetadata", hasMetadata);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        metadata = BrewMetadata.fromTag(tag.getCompound("metadata"));
        heat = tag.getDouble("heat");
        heatInit = tag.getBoolean("init");
        hasMetadata = tag.getBoolean("hasmetadata");
        if(metadata != null)
            cachedColor = metadata.getColor();
        super.fromTag(state, tag);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return super.toUpdatePacket();
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        if(hasMetadata)
            compoundTag.put("metadata", metadata.toTag());
        compoundTag.putBoolean("hasmetadata", hasMetadata);
        compoundTag.putBoolean("heatinit", heatInit);
        compoundTag.putDouble("heat", heat);
        compoundTag.putInt("color", cachedColor);
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        metadata = BrewMetadata.fromTag(compoundTag.getCompound("metadata"));
        hasMetadata = compoundTag.getBoolean("hasmetadata");
        heatInit = compoundTag.getBoolean("heatinit");
        heat = compoundTag.getDouble("heat");
        cachedColor = compoundTag.getInt("color");
        super.fromClientTag(compoundTag);
    }

    @Override
    public void sync() {

    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    public double getHeat() {
        return heat;
    }

    @Override
    public void moveHeat(double change) {
        heat += change;
    }
}
