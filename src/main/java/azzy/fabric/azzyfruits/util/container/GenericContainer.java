package azzy.fabric.azzyfruits.util.container;

import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class GenericContainer extends Container {
    protected Inventory inventory;
    protected int inventorySize = 21;
    protected int inventorySizeX = 7;
    protected int inventorySizeY = 3;

    public GenericContainer(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(null, syncId);
        this.inventory = inventory;
        checkContainerSize(inventory, inventorySize);
        inventory.onInvOpen(playerInventory.player);

        //Assemble the inventory
        this.assemble();

        //Assemble the player's inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, i * 9 + j + 9, 8 + j * 18, 18 + i * 18 + 103 + 18));
            }
        }
        for (int j = 0; j < 9; j++) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 18 + 161 + 18));
        }
    }

    protected int getDimension(Boolean x){
        if(x)
            return inventorySizeX;
        return inventorySizeY;
    }

    //Override this to make fancy inventories
    protected void assemble(){
        for (int i = 0; i < inventorySizeY; i++) {
            for (int j = 0; j < inventorySizeX; j++) {
                this.addSlot(new Slot(inventory, i * j + j, 32 + j * 21, 64 + i * 21));
            }
        }

    }

    // Shift transfer logic, probably should be overridden when dealing with machines
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.getInvSize()) {
                if (!this.insertItem(originalStack, this.inventory.getInvSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.getInvSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse (PlayerEntity player){
        return false;
    }
}
