package azzy.fabric.azzyfruits.recipetypes;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.tileentities.blockentity.PressEntity;
import azzy.fabric.azzyfruits.util.recipe.RecipeBase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public class PressRecipe extends RecipeBase {

    public static final Identifier ID = new Identifier(MODID, "press_recipe");

    public final Ingredient input;
    public final int amount;
    public final ItemStack byproduct;
    public final FluidVolume output;
    public final Identifier id;

    public PressRecipe(Identifier id, Ingredient input, int amount, ItemStack byproduct, FluidVolume output){
        this.input = input;
        this.amount = amount;
        this.byproduct = byproduct;
        this.output = output;
        this.id = id;
    }

    public static class PressRecipeType implements RecipeType<PressRecipe> {
        private PressRecipeType(){

        }

        public static final PressRecipeType INSTANCE = new PressRecipeType();
    }

    @Override
    public boolean canCraft(BlockEntity entity, World world) {
        PressEntity press = (PressEntity) entity;
        ItemStack in = press.getInvStack(0);
        ItemStack by = press.getInvStack(1);
        FluidVolume out = press.fluidInv.getInvFluid(0);
        return input.test(in) && in.getCount() >= amount && (by.isEmpty() || (by.getCount() <= 64 && by.getItem() == byproduct.getItem())) && (out.isEmpty() || (out.getRawFluid() == output.getRawFluid() && out.getAmount_F().asInt(1) + FluidAmount.ofWhole(1).asInt(1) <= press.fluidInv.getMaxAmount_F(0).asInt(1)));
    }

    @Override
    public void completeCraft(BlockEntity entity) {
        PressEntity press = (PressEntity) entity;

        press.inventory.get(0).decrement(amount);
        if(press.inventory.get(1).isEmpty())
            press.inventory.set(1, byproduct);
        else
            press.inventory.get(1).increment(amount);
        if(press.fluidInv.getTank(0).get().isEmpty())
            press.fluidInv.getTank(0).set(output);
        else
            press.fluidInv.getTank(0).insert(output);
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PressRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return PressRecipeType.INSTANCE;
    }

    @Override
    public void getAttributes(BlockEntity entity) {
    }

    public static class PressRecipeSerializer implements RecipeSerializer<PressRecipe> {

        private PressRecipeSerializer(){
        }

        public static final PressRecipeSerializer INSTANCE = new PressRecipeSerializer();

        @Override
        public PressRecipe read(Identifier id, JsonObject json) {
            JsonFormat recipeJson = new Gson().fromJson(json, JsonFormat.class);

            if(recipeJson.input == null || recipeJson.output == null) {
                if (recipeJson.name != null)
                    throw new JsonSyntaxException("Press recipe json " + recipeJson.name + " is missing critical attributes!");
                else
                    throw new JsonSyntaxException("A press recipe json is missing critical attributes, including a name you twat.");
            }

            Ingredient in = Ingredient.fromJson(recipeJson.input);
            FluidVolume out = FluidVolume.fromJson(recipeJson.output);
            ItemStack by = new ItemStack(Registry.ITEM.getOrEmpty(new Identifier(recipeJson.byproduct)).orElse(null), recipeJson.byproductAmount);
            return new PressRecipe(id, in, recipeJson.amount, by, out);
        }

        @Override
        public PressRecipe read(Identifier id, PacketByteBuf buf) {

            Ingredient in = Ingredient.fromPacket(buf);
            int amount = buf.readInt();
            ItemStack by = buf.readItemStack();

            Fluid temp = Registry.FLUID.get(new Identifier(buf.readString(0)));
            int temp2 = buf.readInt();

            FluidVolume out = FluidVolume.create(temp, temp2);

            return new PressRecipe(id, in, amount, by, out);
        }

        @Override
        public void write(PacketByteBuf buf, PressRecipe recipe) {
            recipe.input.write(buf);
            buf.writeInt(recipe.amount);
            buf.writeItemStack(recipe.byproduct);
            buf.writeString(Registry.FLUID.getId(recipe.output.getRawFluid()).toString(), 0);
            buf.writeInt(recipe.output.getAmount_F().as1620());
        }
    }

    class JsonFormat{
        String name;
        JsonObject input;
        JsonObject output;
        int amount;
        String byproduct;
        int byproductAmount;
    }
}
