package azzy.fabric.circumstable;

import azzy.fabric.circumstable.config.ConfigGen;
import azzy.fabric.circumstable.registry.*;
import azzy.fabric.circumstable.util.interaction.HeatTransferHelper;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Circumstable implements ModInitializer {
    public static final String MOD_ID = "circumstable";

    public static final Logger CLog = LogManager.getLogger(MOD_ID);
    public static volatile ConfigGen config;

    @Override
    public void onInitialize() {

        AutoConfig.register(ConfigGen.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ConfigGen.class).getConfig();

        //Registries
        ItemRegistry.init();
        PotionRegistry.init();
        BlockEntityRegistry.init();
        ContainerRegistry.init();
        FluidRegistry.init();
        HeatTransferHelper.init();
    }
}
