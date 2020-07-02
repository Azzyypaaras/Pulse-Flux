package azzy.fabric.azzyfruits.staticentities.blockentity;

import azzy.fabric.azzyfruits.registry.ItemRegistry;
import azzy.fabric.azzyfruits.util.interaction.HeatHolder;
import azzy.fabric.azzyfruits.util.interaction.HeatTransferHelper;
import azzy.fabric.azzyfruits.util.tracker.BrewMetadata;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
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
        if(heatInit){
            heat = HeatTransferHelper.translateBiomeHeat(this.world.getBiome(this.pos));
            heatInit = false;
        }
        if(world.getTime() % 5 == 0)
        fetchDroppedItems();
        if(world.getTime() % 20 == 0){
            if(config.isDebugOn())
                FFLog.error("Cauldron Temperature: "+heat);
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
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        metadata = BrewMetadata.fromTag(tag.getCompound("metadata"));
        heat = tag.getDouble("heat");
        heatInit = tag.getBoolean("init");
        hasMetadata = tag.getBoolean("hasmetadata");
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        if(hasMetadata)
            compoundTag.put("metadata", metadata.toTag());
        compoundTag.putBoolean("hasmetadata", hasMetadata);
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        metadata = BrewMetadata.fromTag(compoundTag.getCompound("metadata"));
        hasMetadata = compoundTag.getBoolean("hasmetadata");
        super.fromClientTag(compoundTag);
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
        return 0;
    }

    @Override
    public void moveHeat(double change) {

    }
}
