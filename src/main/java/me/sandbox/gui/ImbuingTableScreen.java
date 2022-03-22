package me.sandbox.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import me.sandbox.IllagerExpansion;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class ImbuingTableScreen
        extends HandledScreen<ImbuingTableScreenHandler> {
    private static final Text AT_MAX = new LiteralText("Book is at max level!");
    private static final Text BOOK_BIG = new LiteralText("Must have 1 enchantment!");
    private static final Text TOO_LOW = new LiteralText("Enchantment level is too low!");
    private static final Text BAD_ENCHANT = new LiteralText("Enchantment cannot be imbued!");
    private static final Identifier TEXTURE = new Identifier(IllagerExpansion.MOD_ID,"textures/gui/imbue_table.png");

    public ImbuingTableScreen(ImbuingTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.drawBackground(matrices, delta, mouseX, mouseY);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if ((((ImbuingTableScreenHandler)this.handler).getSlot(0).hasStack() || ((ImbuingTableScreenHandler)this.handler).getSlot(1).hasStack()) && !((ImbuingTableScreenHandler)this.handler).getSlot(2).hasStack()) {
        }
        if (handler.atMax) {
            Text text;
            text = AT_MAX;
            this.drawTexture(matrices, i + 99, j+ 44, 176, 0, 28, 36);
            this.textRenderer.drawWithShadow(matrices, text, 190.0f, 80.0f, 0xFF5555);
        }
        if (handler.bigBook) {
            Text text;
            text = BOOK_BIG;
            this.drawTexture(matrices, i + 99, j+ 44, 176, 0, 28, 36);
            this.textRenderer.drawWithShadow(matrices, text, 175.0f, 80.0f, 0xFF5555);
        }
        if (handler.tooLow) {
            Text text;
            text = TOO_LOW;
            this.drawTexture(matrices, i + 99, j+ 44, 176, 0, 28, 36);
            this.textRenderer.drawWithShadow(matrices, text, 170.0f, 80.0f, 0xFF5555);
        }
        if (handler.badEnchant) {
            Text text;
            text = BAD_ENCHANT;
            this.drawTexture(matrices, i + 99, j+ 44, 176, 0, 28, 36);
            this.textRenderer.drawWithShadow(matrices, text, 165.0f, 80.0f, 0xFF5555);
        }
        if (handler.badItems && !handler.tooLow && !handler.bigBook && !handler.atMax && !handler.badEnchant) {
            this.drawTexture(matrices, i + 99, j+ 44, 176, 0, 28, 36);
        }
    }
}
