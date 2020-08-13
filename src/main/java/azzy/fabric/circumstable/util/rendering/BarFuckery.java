package azzy.fabric.circumstable.util.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WBar;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;

public class BarFuckery extends WBar {

    private final int color;
    private final Identifier bar;

    public BarFuckery(Identifier bg, int color, int field, int maxfield, WBar.Direction dir, BarType type, Identifier special) {
        super(bg, new Identifier("thonk"), field, maxfield, dir);
        this.color = color;
        if (type == BarType.FRUIT)
            this.bar = new Identifier(MOD_ID, "textures/gui/bars/juicebase.png");
        else if (type == BarType.GENERIC)
            this.bar = special;
        else
            this.bar = null;
    }

    @Environment(EnvType.CLIENT)
    public static void drawShit(int x, int y, int width, int height, Identifier texture, float u1, float v1, float u2, float v2, int color) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
        if (width <= 0) {
            width = 1;
        }

        if (height <= 0) {
            height = 1;
        }

        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        buffer.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
        buffer.vertex(x, y + height, 0.0D).color(r, g, b, 1.0F).texture(u1, v2).next();
        buffer.vertex(x + width, y + height, 0.0D).color(r, g, b, 1.0F).texture(u2, v2).next();
        buffer.vertex(x + width, y, 0.0D).color(r, g, b, 1.0F).texture(u2, v1).next();
        buffer.vertex(x, y, 0.0D).color(r, g, b, 1.0F).texture(u1, v1).next();
        tessellator.draw();
        RenderSystem.disableBlend();
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        if (this.bg != null) {
            ScreenDrawing.texturedRect(x, y, this.getWidth(), this.getHeight(), this.bg, -1);
        } else {
            ScreenDrawing.coloredRect(x, y, this.getWidth(), this.getHeight(), ScreenDrawing.colorAtOpacity(0, 0.25F));
        }

        float percent = (float) this.properties.get(this.field) / (float) this.properties.get(this.max);
        if (percent < 0.0F) {
            percent = 0.0F;
        }

        if (percent > 1.0F) {
            percent = 1.0F;
        }

        int barMax = this.getWidth();
        if (this.direction == WBar.Direction.DOWN || this.direction == WBar.Direction.UP) {
            barMax = this.getHeight();
        }

        percent = (float) ((int) (percent * (float) barMax)) / (float) barMax;
        int barSize = (int) ((float) barMax * percent);
        if (barSize > 0) {
            switch (this.direction) {
                case UP:
                    int top = y + this.getHeight();
                    top -= barSize;
                    if (this.bar != null) {
                        drawShit(x, top, this.getWidth(), barSize, this.bar, 0.0F, 1.0F - percent, 1.0F, 1.0F, color);
                    } else {
                        ScreenDrawing.coloredRect(x, top, this.getWidth(), barSize, ScreenDrawing.colorAtOpacity(16777215, 0.5F));
                    }
                    break;
                case RIGHT:
                    if (this.bar != null) {
                        drawShit(x, y, barSize, this.getHeight(), this.bar, 0.0F, 0.0F, percent, 1.0F, color);
                    } else {
                        ScreenDrawing.coloredRect(x, y, barSize, this.getHeight(), ScreenDrawing.colorAtOpacity(16777215, 0.5F));
                    }
                    break;
                case DOWN:
                    if (this.bar != null) {
                        drawShit(x, y, this.getWidth(), barSize, this.bar, 0.0F, 0.0F, 1.0F, percent, color);
                    } else {
                        ScreenDrawing.coloredRect(x, y, this.getWidth(), barSize, ScreenDrawing.colorAtOpacity(16777215, 0.5F));
                    }
                    break;
                case LEFT:
                    int left = x + this.getWidth();
                    left -= barSize;
                    if (this.bar != null) {
                        drawShit(left, y, barSize, this.getHeight(), this.bar, 1.0F - percent, 0.0F, 1.0F, 1.0F, color);
                    } else {
                        ScreenDrawing.coloredRect(left, y, barSize, this.getHeight(), ScreenDrawing.colorAtOpacity(16777215, 0.5F));
                    }
            }

        }
    }

    public enum BarType {
        FRUIT,
        GENERIC,
        INCANDESCENT,
        ESSENCE,
        SPECIAL
    }
}
