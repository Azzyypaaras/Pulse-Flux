package azzy.fabric.forgottenfruits.staticentities.blockentity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.forgottenfruits.util.InventoryWrapper;
import azzy.fabric.forgottenfruits.util.recipe.FFRecipe;
import azzy.fabric.forgottenfruits.util.recipe.RecipeHandler;
import azzy.fabric.forgottenfruits.util.recipe.RecipeRegistryKey;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import static azzy.fabric.forgottenfruits.ForgottenFruits.REGISTERED_RECIPES;

public class MachineEntity extends BlockEntity implements Tickable, InventoryWrapper, SidedInventory, PropertyDelegateHolder, BlockEntityClientSerializable, InventoryProvider, NamedScreenHandlerFactory {

    //DEFAULT VALUES, DO NOT FORGET TO OVERRIDE THESE

    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(0, ItemStack.EMPTY);
    protected String identity = "VOID";
    public SimpleFixedFluidInv fluidInv;
    protected boolean isActive = false;
    protected int progress = 0;
    protected String technicalState = "default";

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

    public MachineEntity(BlockEntityType<? extends MachineEntity> entityType) {
        super(entityType);
        fluidInv = new SimpleFixedFluidInv(0, new FluidAmount(0));
    }

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
        if (!world.isClient)
            sync();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {

        //Inventory nbt
        Inventories.fromTag(tag, inventory);

        //Fluid nbt
        fluidInv.fromTag(tag.getCompound("fluid"));


        //State nbt
        progress = tag.getInt("progress");
        isActive = tag.getBoolean("active");
        technicalState = tag.getString("state");
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
        super.toTag(tag);

        //Inventory nbt
        Inventories.toTag(tag, inventory);

        //Fluid nbt
        tag.put("fluid", fluidInv.toTag());

        //State nbt
        if (isActive || !technicalState.equals("default")) {
            tag.putInt("progress", progress);
            tag.putBoolean("active", isActive);
            tag.putString("state", technicalState);
        }
        return tag;
    }

    @SuppressWarnings("unchecked")
    public <T extends FFRecipe> RecipeHandler<T, ?> getRecipeHandler(RecipeRegistryKey id) {
        return REGISTERED_RECIPES.containsKey(id) ? (RecipeHandler<T, ?>) REGISTERED_RECIPES.get(id).getHandler() : null;
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
        fluidInv.fromTag(compoundTag.getCompound("fluid"));
        progress = compoundTag.getInt("progress");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.put("fluid", fluidInv.toTag());
        compoundTag.putInt("progress", progress);
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
}
