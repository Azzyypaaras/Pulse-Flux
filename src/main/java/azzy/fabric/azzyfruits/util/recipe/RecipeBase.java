package azzy.fabric.azzyfruits.util.recipe;

import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public abstract class RecipeBase implements Recipe<Inventory> {

    public abstract boolean canCraft(BlockEntity entity, World world);

    public abstract void completeCraft(BlockEntity entity);

    public abstract void getAttributes(BlockEntity entity);

    //Ok so all of this is fucking gay, and if you call it so are you

    @Override
    public boolean matches(Inventory inv, World world) {
        return false;
     }

    @Override
    public ItemStack craft(Inventory inv) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return null;
    }

    @Override
    public abstract Identifier getId();

    public abstract RecipeSerializer<?> getSerializer();

    public abstract RecipeType<?> getType();
}
