package azzy.fabric.circumstable.util.controller;

import azzy.fabric.circumstable.Circumstable;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.NinePatch;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class BaseController extends SyncedGuiDescription {
    protected WPlainPanel root = new WPlainPanel();
    protected int slotY, slotX, sizeY, sizeX, spacing, alignment;
    protected String name;
    protected WItemSlot itemSlot;

    BaseController(ScreenHandlerType recipeType, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(recipeType, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        assembleGridSize();
        setRootPanel(root);
        root.setSize(sizeX, sizeY);
        assembleInventory(blockInventory.size(), spacing, spacing);
        root.add(new WLabel(name, 16776693), alignment, 0);
        build();
    }

    //Slot sprite size is 18 pixels
    //Inventories should aim to be 162 pixels wide, unless they are not displaying the player's inventory

    protected void assembleGridSize() {
        slotY = 1;
        slotX = 1;
        spacing = 1;
        name = "null";
        sizeY = slotY * spacing + spacing;
        sizeX = slotX * spacing;
        alignment = 1;
    }

    protected void assembleInventory(int slots, int gapX, int gapY) {
        int s = 0;
        for (int i = 0; i < slotY; i++) {
            for (int j = 0; j < slotX; j++) {
                itemSlot = WItemSlot.of(blockInventory, s);
                root.add(itemSlot, j * gapX + 2, 13 + i * gapY);
                s++;
            }
        }
    }

    @Override
    public void addPainters() {
        NinePatch paint = BackgroundPainter.createNinePatch(new Identifier(Circumstable.MOD_ID, "textures/gui/background/ninepatch_basic.png"));
        paint.setPadding(8);
        paint.setMode(NinePatch.Mode.TILING);
        root.setBackgroundPainter(paint);
    }

    protected void build() {
        root.add(this.createPlayerInventoryPanel(), 1, sizeY + 12);
        root.validate(this);
    }
}
