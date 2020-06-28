package azzy.fabric.azzyfruits.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Optional;
import java.util.function.Supplier;

import static azzy.fabric.azzyfruits.ForgottenFruits.*;

@Environment(EnvType.CLIENT)
public class FFConfig implements ModMenuApi {

    @Override
    public String getModId() {
        return MODID;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(new TranslatableText("config.azzyfruits.title"));
            builder.getOrCreateCategory(new TranslatableText("config.azzyfruits.recipes"));
            builder.getOrCreateCategory(new TranslatableText("config.azzyfruits.recipes"));
            builder.getOrCreateCategory(new TranslatableText("config.azzyfruits.difficulty"));
            builder.getOrCreateCategory(new TranslatableText("config.azzyfruits.dev"));
            return builder.build();
        };
    }
}
