package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.fluid.GenericFluid;
import azzy.fabric.azzyfruits.block.fluid.JuiceCloudberry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public class FluidRegistry {

    //Cloudberry
    public static BaseFluid STILL_CLOUDJUICE = registerStill("still_cloudberry", new JuiceCloudberry.Still());
    public static BaseFluid FLOWING_CLOUDJUICE = registerFlowing("flowing_cloudberry", new JuiceCloudberry.Flowing());
    public static Block CLOUDJUICE = registerFluidBlock("cloudberry_juice", STILL_CLOUDJUICE, Blocks.WATER);
    public static Item BUCKET_CLOUDJUICE = registerBucket("bucket_cloudberry", STILL_CLOUDJUICE);



    public static ArrayList<FluidPair> juiceRenderRegistry = new ArrayList<FluidPair>(){
        FluidPair cloudberryJuice = new FluidPair(STILL_CLOUDJUICE, FLOWING_CLOUDJUICE, 0x703c38);
    };

    @Environment(EnvType.CLIENT)
    public static ArrayList<Fluid> registryFluidTrans = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void initTransparency(){
        registryFluidTrans.add(STILL_CLOUDJUICE);
        registryFluidTrans.add(FLOWING_CLOUDJUICE);
    }

    public static BaseFluid registerStill(String name, BaseFluid item){
        Registry.register(Registry.FLUID, new Identifier(MODID, name), item);
        return item;
    }

    public static BaseFluid registerFlowing(String name, BaseFluid item){
        Registry.register(Registry.FLUID, new Identifier(MODID, name), item);
        return item;
    }

    public static Item registerBucket(String name, BaseFluid item){
        BucketItem temp = new BucketItem(item, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier(MODID, name), temp);
        return temp;
    }

    public static Block registerFluidBlock(String name, BaseFluid item, Block base){
        Block temp = new FluidBlock(item, FabricBlockSettings.copy(base).build()){};
        Registry.register(Registry.BLOCK, new Identifier(MODID, name), temp);
        return temp;
    }

    public static class FluidPair{

        private BaseFluid stillState;
        private BaseFluid flowState;
        private int color;

        public FluidPair(final BaseFluid stillState, final BaseFluid flowState, final int color){
            this.stillState = stillState;
            this.flowState = flowState;
            this.color = color;
        }

        public BaseFluid getStillState(){
            return stillState;
        }

        public BaseFluid getFlowState() {
            return flowState;
        }

        public int getColor() {
            return color;
        }
    }

    public static void init(){
    }
}
