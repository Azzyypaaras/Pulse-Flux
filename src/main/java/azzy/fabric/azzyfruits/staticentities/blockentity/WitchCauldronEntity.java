package azzy.fabric.azzyfruits.staticentities.blockentity;

import azzy.fabric.azzyfruits.registry.ItemRegistry;
import azzy.fabric.azzyfruits.registry.ParticleRegistry;
import azzy.fabric.azzyfruits.render.util.HexColorTranslator;
import azzy.fabric.azzyfruits.util.interaction.HeatHolder;
import azzy.fabric.azzyfruits.util.interaction.HeatTransferHelper;
import azzy.fabric.azzyfruits.util.tracker.BrewMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Stream;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;
import static azzy.fabric.azzyfruits.ForgottenFruits.config;
import static azzy.fabric.azzyfruits.block.BEBlocks.WitchCauldronBlock.CHAOTIC;
import static azzy.fabric.azzyfruits.block.BEBlocks.WitchCauldronBlock.EFFULGENT;
import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.WITCH_CAULDRON_ENTITY;

public class WitchCauldronEntity extends MachineEntity implements HeatHolder {

    private BrewMetadata metadata;
    private boolean hasMetadata;
    private String cachedBrew;
    private int cachedColor;
    private double heat;
    //Yeah so, you can't get world on init :p
    private boolean heatInit = true;

    public WitchCauldronEntity() {
        super(WITCH_CAULDRON_ENTITY);
        inventory = DefaultedList.ofSize(512, ItemStack.EMPTY);
        hasMetadata  = false;
    }

    @Override
    public void tick() {
        if(metadata == null){
            hasMetadata = false;
        }
        else if(cachedColor == 	0xFFFFFF || cachedColor == 	0){
            cachedColor = metadata.getColor();
        }
        if(heatInit){
            heat = HeatTransferHelper.translateBiomeHeat(this.world.getBiome(this.pos));
            heatInit = false;
        }
        if(world.getTime() % 5 == 0 && hasMetadata) {
            fetchDroppedItems();
        }
        if(world.getTime() % 20 == 0){
            if (!world.isClient() && heat >= 100) {
                //((ServerWorld) world).spawnParticles(ParticleTypes.BUBBLE, pos.getX()+0.125, pos.getY()+0.69, pos.getZ()+0.125, 3 + world.random.nextInt(4), 0.75, 0, 0.75, 0);
            }
            if (world.isClient() && heat >= 100 && hasMetadata){
                Particle particle;
                int variation = world.getRandom().nextInt(4);
                double positionx;
                double positionz;
                for(int i = 0; i < 3 + variation; i++) {
                    positionx = world.getRandom().nextInt(5)/10.0;
                    positionz = world.getRandom().nextInt(5)/10.0;
                    particle = MinecraftClient.getInstance().particleManager.addParticle(ParticleRegistry.CAULDRON_BUBBLES, pos.getX() + 0.2+positionx, pos.getY() + 0.6875, pos.getZ() + 0.2+positionz, 0, 0.2, 0);
                    int[] rgb = HexColorTranslator.translate(metadata.getColor());
                    particle.setColor(rgb[0]/255f, rgb[1]/255f, rgb[2]/255f);
                    MinecraftClient.getInstance().particleManager.addParticle(particle);
                    particle.setMaxAge(5+world.getRandom().nextInt(15));
                }
            }
            if(config.isDebugOn())
                FFLog.error("Cauldron Temperature: "+heat);
            Block source = world.getBlockState(pos.down()).getBlock();
            BlockEntity entity = world.getBlockEntity(pos.down());
            if(entity instanceof HeatHolder){
                HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, this, (HeatHolder) entity);
            }
            else if(HeatTransferHelper.isHeatSource(source)){
                HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, this, source);
            }
            HeatTransferHelper.simulateAmbientHeat(this, world.getBiome(pos));
        }

        super.tick();
    }

    public void fetchDroppedItems(){
        BlockState cauldron = this.getCachedState();
        World world = this.getWorld();
        List<ItemEntity> items = world.getNonSpectatingEntities(ItemEntity.class, new Box(this.pos.add(0, 0.6875, 0), this.pos.add(1, 1, 1)));
        items.stream().forEach(e -> {
            for (int i = 1; i < inventory.size(); i++) {
                ItemStack stack = inventory.get(i);
                ItemStack drops = e.getStack();
                if(stack.isEmpty()) {
                    inventory.set(i, drops);
                    e.kill();
                    playDropEffects();
                    return;
                }
                else if(drops.getItem() == stack.getItem() && stack.getCount() < 64){
                    if(drops.getCount() + stack.getCount() > 64)
                        return;
                    else{
                        inventory.get(i).increment(drops.getCount());
                        e.kill();
                        playDropEffects();
                        return;
                    }
                }
            }
        });
    }

    public void setHasMetadata(boolean bool){
        hasMetadata = bool;
    }

    public boolean hasMetadata(){
        return hasMetadata;
    }

    public BrewMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(BrewMetadata metadata, Identifier brew) {
        this.metadata = metadata;
        this.cachedBrew = brew.toString();
    }

    public int getCachedColor() {
        return cachedColor;
    }

    public void playDropEffects(){
        if(!world.isClient()){
            ((ServerWorld) world).spawnParticles(ParticleTypes.SPLASH, this.pos.getX()+0.5, this.pos.getY()+0.69, this.pos.getZ()+0.5, world.random.nextInt(5)+2, 0, 0.0, 0, 0);
        }
        world.playSound(this.pos.getX()+0.5, this.pos.getY()+0.7, this.pos.getZ()+0.5, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.9f, 1.1f, false);
    }

    public void updateGem(){
        this.world.setBlockState(this.pos, this.getCachedState().with(EFFULGENT, inventory.get(0).getItem() == ItemRegistry.ATTUNED_EFFULGENT).with(CHAOTIC, inventory.get(0).getItem() == ItemRegistry.ATTUNED_CHAOTIC));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(hasMetadata)
            tag.put("metadata", metadata.toTag());
        tag.putDouble("heat", heat);
        tag.putBoolean("init", heatInit);
        tag.putBoolean("hasmetadata", hasMetadata);
        tag.putInt("color", cachedColor);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        metadata = BrewMetadata.fromTag(tag.getCompound("metadata"));
        heat = tag.getDouble("heat");
        heatInit = tag.getBoolean("init");
        hasMetadata = tag.getBoolean("hasmetadata");
        cachedColor = tag.getInt("color");
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
