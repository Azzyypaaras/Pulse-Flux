package azzy.fabric.forgottenfruits.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static azzy.fabric.forgottenfruits.ForgottenFruits.*;

@Environment(EnvType.CLIENT)
public class FFConfig implements ModMenuApi {

    @Override
    public String getModId() {
        return MOD_ID;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ConfigGen.class, parent).get();
    }
}
