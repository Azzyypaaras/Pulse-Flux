package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.fluid.GenericFluid;
import azzy.fabric.azzyfruits.block.fluid.JuiceCloudberry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public class FluidRegistry {

    public static HashMap<Item, FluidPair> FLUIDCOLORREGISTRY = new HashMap<>();

    //Cloudberry
    public static FlowableFluid STILL_CLOUDJUICE = registerStill("still_cloudberry", new JuiceCloudberry.Still());
    public static FlowableFluid FLOWING_CLOUDJUICE = registerFlowing("flowing_cloudberry", new JuiceCloudberry.Flowing());
    public static Block CLOUDJUICE = registerFluidBlock("cloudberry_juice", STILL_CLOUDJUICE, Blocks.WATER);
    public static Item BUCKET_CLOUDJUICE = registerBucket("bucket_cloudberry", STILL_CLOUDJUICE);


    static FluidPair cloudberryJuice = new FluidPair(STILL_CLOUDJUICE, FLOWING_CLOUDJUICE, 0xee9b2f);

    public static ArrayList<FluidPair> juiceRenderRegistry = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static ArrayList<Fluid> registryFluidTrans = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void initTransparency(){
        registryFluidTrans.add(STILL_CLOUDJUICE);
        registryFluidTrans.add(FLOWING_CLOUDJUICE);
    }

    public static FlowableFluid registerStill(String name, FlowableFluid item){
        Registry.register(Registry.FLUID, new Identifier(MODID, name), item);
        return item;
    }

    public static FlowableFluid registerFlowing(String name, FlowableFluid item){
        Registry.register(Registry.FLUID, new Identifier(MODID, name), item);
        return item;
    }

    public static Item registerBucket(String name, FlowableFluid item){
        BucketItem temp = new BucketItem(item, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroup.MISC));
        Registry.register(Registry.ITEM, new Identifier(MODID, name), temp);
        return temp;
    }

    public static Block registerFluidBlock(String name, FlowableFluid item, Block base){
        Block temp = new FluidBlock(item, FabricBlockSettings.copy(base).build()){};
        Registry.register(Registry.BLOCK, new Identifier(MODID, name), temp);
        return temp;
    }

    public static class FluidPair {

        private FlowableFluid stillState;
        private FlowableFluid flowState;
        private int color;

        public FluidPair(final FlowableFluid stillState, final FlowableFluid flowState, final int color) {
            this.stillState = stillState;
            this.flowState = flowState;
            this.color = color;
        }

        public FlowableFluid getStillState() {
            return stillState;
        }

        public FlowableFluid getFlowState() {
            return flowState;
        }

        public int getColor() {
            return color;
        }
    }
        public static void init() {
            juiceRenderRegistry.add(cloudberryJuice);
            FLUIDCOLORREGISTRY.put(BUCKET_CLOUDJUICE, cloudberryJuice);
        }
}
