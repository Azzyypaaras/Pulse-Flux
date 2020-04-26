package azzy.fabric.azzyfruits.util.controller;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;

public class BaseController extends CottonCraftingController {
    protected WGridPanel root = new WGridPanel();;
    protected int slotY = 2, slotX = 9;

    public BaseController(RecipeType<?> recipeType, int syncId, PlayerInventory playerInventory, BlockContext context) {
        super(recipeType, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        setRootPanel(root);
        root.setSize(20, 16);
        assembleInventory(blockInventory.getInvSize(), 1, 1);
        root.add(new WLabel("Basket"), 0, 0);
        build();
    }

    protected void assembleInventory(int slots, int gapX, int gapY){
        WItemSlot itemSlot;
        int s = 0;
        for (int i = 0; i < slotY; i++) {
            for (int j = 0; j < slotX; j++) {
            itemSlot = WItemSlot.of(blockInventory, s);
            root.add(itemSlot, j*gapX, 1+i*gapY);
            s++;
            }
        }
    }

    protected void build(){
        root.add(this.createPlayerInventoryPanel(), 0, slotY+2);
        root.add(new WLabel("Inventory"), 0, slotY+1);
        root.validate(this);
    }
}
