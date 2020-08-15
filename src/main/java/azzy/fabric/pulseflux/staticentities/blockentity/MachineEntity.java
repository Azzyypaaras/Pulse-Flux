package azzy.fabric.pulseflux.staticentities.blockentity;

import azzy.fabric.pulseflux.util.InventoryWrapper;
import azzy.fabric.pulseflux.util.interaction.HeatHolder;
import azzy.fabric.pulseflux.util.interaction.HeatTransferHelper;
import blue.endless.jankson.annotation.Nullable;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;


public abstract class MachineEntity extends BlockEntity implements Tickable, InventoryWrapper, SidedInventory, PropertyDelegateHolder, BlockEntityClientSerializable, InventoryProvider, NamedScreenHandlerFactory, HeatHolder {

    //DEFAULT VALUES, DO NOT FORGET TO OVERRIDE THESE

    protected final HeatTransferHelper.HeatMaterial material;

    public DefaultedList<ItemStack> inventory;
    //public SimpleFixedFluidInv fluidInv;
    protected boolean isActive = false;
    protected int progress = 0;
    protected double heat;
    private boolean heatInit = true;

    final PropertyDelegate tankHolder = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return 0;
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int size() {
            return 0;
        }
    };

    //ALSO OVERRIDE THIS

    public MachineEntity(BlockEntityType<? extends MachineEntity> entityType, HeatTransferHelper.HeatMaterial material) {
        super(entityType);
        initBlockEntity();
        this.material = material;
        //fluidInv = new SimpleFixedFluidInv(0, new FluidAmount(0));
    }

    protected abstract void initBlockEntity();

    @Override
    public int[] getAvailableSlots(Direction var1) {
        int[] result = new int[size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public void tick() {
        if(heatInit) {
            heat = HeatTransferHelper.translateBiomeHeat(this.getWorld().getBiome(pos));
            heatInit = false;
        }

        for(int i = 0; i < 4; i++)
            calcHeat();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        //Inventory nbt
        Inventories.fromTag(tag, this.inventory);

        //Fluid nbt
        //fluidInv.fromTag(tag.getCompound("fluid"));


        //State nbt
        this.progress = tag.getInt("progress");
        this.isActive = tag.getBoolean("active");
        this.heat = tag.getDouble("heat");
        this.heatInit = tag.getBoolean("heatinit");
        super.fromTag(state, tag);

    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.UP;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, this.inventory);

        //Fluid nbt
        //tag.put("fluid", fluidInv.toTag());

        tag.putInt("progress", this.progress);
        tag.putBoolean("active", this.isActive);

        tag.putDouble("heat", this.heat);
        tag.putBoolean("heatinit", this.heatInit);

        return super.toTag(tag);
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return tankHolder;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
    //    fluidInv.fromTag(compoundTag.getCompound("fluid"));
        this.progress = compoundTag.getInt("progress");
        this.heat = compoundTag.getDouble("heat");
        this.heatInit = compoundTag.getBoolean("heatinit");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
    //    compoundTag.put("fluid", fluidInv.toTag());
        compoundTag.putInt("progress", this.progress);
        compoundTag.putDouble("heat", this.heat);
        compoundTag.putBoolean("heatinit", this.heatInit);
        return compoundTag;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return this;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }

    public void calcHeat(){
        HeatTransferHelper.simulateAmbientHeat(this, this.world.getBiome(pos));
        simulateSurroundingHeat(pos, this);
    }

    public static <T extends MachineEntity & HeatHolder> void simulateSurroundingHeat(BlockPos pos, T bodyA){
        BlockPos bodyB;
        World world = bodyA.getWorld();

        if(world == null)
            return;

        for(Direction direction : Direction.values()){
            bodyB = pos.offset(direction);

            if((world.getBlockEntity(bodyB) instanceof  HeatHolder)){
                if(((MachineEntity) world.getBlockEntity(bodyB)).getMaterial() != null)
                    HeatTransferHelper.simulateHeat(((MachineEntity) world.getBlockEntity(bodyB)).getMaterial(), (HeatHolder) world.getBlockEntity(bodyB), bodyA);
                else if(bodyA.getMaterial() != null)
                    HeatTransferHelper.simulateHeat(bodyA.getMaterial(), (HeatHolder) world.getBlockEntity(bodyB), bodyA);
                else
                    HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, (HeatHolder) world.getBlockEntity(bodyB), bodyA);
            }
            else if(HeatTransferHelper.isHeatSource(world.getBlockState(bodyB).getBlock())){
                HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, bodyA, world.getBlockState(bodyB).getBlock());
            }
        }
    }

    protected void meltDown(boolean cold, @Nullable BlockState state){
        if(cold && state != null){
            world.setBlockState(pos, state);
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f, false);
            if(!world.isClient()){
                ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, pos.getX()+0.25, pos.up().getY(), pos.getZ()+0.25, 11, 0.25, 0, 0.25, 0);
            }
        }
        else{
            world.setBlockState(pos, Blocks.LAVA.getDefaultState());
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.2f, 0.8f, false);
            if(!world.isClient()){
                ((ServerWorld) world).spawnParticles(ParticleTypes.LARGE_SMOKE, pos.getX()+0.25, pos.up().getY(), pos.getZ()+0.25, 11, 0.25, 0, 0.25, 0);
            }
        }
    }

    @Override
    public double getHeat() {
        return heat;
    }

    @Override
    public void moveHeat(double change) {
        heat += change;
    }

    @Override
    public double getArea() {
        return 1;
    }

    @Override
    public HeatTransferHelper.HeatMaterial getMaterial() {
        return material;
    }
}
