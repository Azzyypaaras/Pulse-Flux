package azzy.fabric.azzyfruits.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "forgottenfruits")
public class ConfigGen implements ConfigData {

    @ConfigEntry.Category(value = "config.azzyfruits.general")
    boolean DEBUG = false;
    
    public boolean isDebugOn() {
        return DEBUG;
    }
}
