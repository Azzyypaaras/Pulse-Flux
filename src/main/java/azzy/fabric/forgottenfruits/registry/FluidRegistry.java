package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.block.fluid.JuiceCinder;
import azzy.fabric.forgottenfruits.block.fluid.JuiceCloudberry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

import static azzy.fabric.forgottenfruits.ForgottenFruits.MODID;

public class FluidRegistry {

    private static AbstractBlock.Settings juiceSettings = FabricBlockSettings.of(Material.WATER).noCollision().strength(100f).dropsNothing();
    private static AbstractBlock.Settings glowSettings = FabricBlockSettings.of(Material.WATER).strength(100f).dropsNothing().lightLevel((state) -> {return 6;});

    //Cloudberry
    public static FlowableFluid STILL_CLOUDJUICE = registerStill("still_cloudberry", new JuiceCloudberry.Still());
    public static FlowableFluid FLOWING_CLOUDJUICE = registerFlowing("flowing_cloudberry", new JuiceCloudberry.Flowing());
    public static Block CLOUDJUICE = registerFluidBlock("cloudberry_juice", STILL_CLOUDJUICE, juiceSettings);
    public static Item BUCKET_CLOUDJUICE = registerBucket("bucket_cloudberry", STILL_CLOUDJUICE);
    public static FlowableFluid STILL_CINDERJUICE = registerStill("still_cinder", new JuiceCinder.Still());
    public static FlowableFluid FLOWING_CINDERJUICE = registerFlowing("flowing_cinder", new JuiceCinder.Flowing());
    public static Block CINDERJUICE = registerFluidBlock("cinder_juice", STILL_CINDERJUICE, glowSettings);
    public static Item BUCKET_CINDERJUICE = registerBucket("bucket_cinderjuice", STILL_CINDERJUICE);


    private static FluidPair cloudberryJuice = new FluidPair(STILL_CLOUDJUICE, FLOWING_CLOUDJUICE, 0xee9b2f);
    private static FluidPair cindermoteJuice = new FluidPair(STILL_CINDERJUICE, FLOWING_CINDERJUICE, 0xe37b00);

    public static ArrayList<FluidPair> juiceRenderRegistry = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static ArrayList<Fluid> registryFluidTrans = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void initTransparency(){
        registryFluidTrans.add(STILL_CLOUDJUICE);
        registryFluidTrans.add(FLOWING_CLOUDJUICE);
        registryFluidTrans.add(STILL_CINDERJUICE);
        registryFluidTrans.add(FLOWING_CINDERJUICE);
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

    public static Block registerFluidBlock(String name, FlowableFluid item, AbstractBlock.Settings base){
        Block temp = new FluidBlock(item, base){};
        Registry.register(Registry.BLOCK, new Identifier(MODID, name), temp);
        return temp;
    }

    public static class FluidPair {

        private FlowableFluid stillState;
        private FlowableFluid flowState;
        private int color;

        private FluidPair(final FlowableFluid stillState, final FlowableFluid flowState, final int color) {
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
            juiceRenderRegistry.add(cindermoteJuice);
        }
}
