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
    private static final Text BOOK_BIG = new LiteralText("Book must have 1 enchantment!");
    private static final Text TOO_LOW = new LiteralText("Book enchantment level is too low!");
    private static final Text BAD_ENCHANT = new LiteralText("Book enchantment cannot be imbued!");
    private static final Text BAD_ITEM = new LiteralText("Item cannot have this enchantment!");
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
        if (ImbuingTableScreenHandler.bigBook && !ImbuingTableScreenHandler.lowEnchant && !ImbuingTableScreenHandler.badEnchant) {
            Text text;
            text = BOOK_BIG;
            this.drawTexture(matrices, 226, 81, 176, 0, 36,23);
            this.textRenderer.drawWithShadow(matrices, text, 165, 40, 0xFF6060);
        }
        if (ImbuingTableScreenHandler.badEnchant && !ImbuingTableScreenHandler.bigBook && !ImbuingTableScreenHandler.lowEnchant) {
            Text text;
            text = BAD_ENCHANT;
            this.drawTexture(matrices, 226, 81, 176, 0, 36,23);
            this.textRenderer.drawWithShadow(matrices, text, 151, 40, 0xFF6060);
        }
        if (ImbuingTableScreenHandler.lowEnchant && !ImbuingTableScreenHandler.bigBook && !ImbuingTableScreenHandler.badEnchant) {
            Text text;
            text = TOO_LOW;
            this.drawTexture(matrices, 226, 81, 176, 0, 36,23);
            this.textRenderer.drawWithShadow(matrices, text, 155, 40, 0xFF6060);
        }
        if (ImbuingTableScreenHandler.badItem && !ImbuingTableScreenHandler.badEnchant && !ImbuingTableScreenHandler.bigBook && !ImbuingTableScreenHandler.lowEnchant) {
            Text text;
            text = BAD_ITEM;
            this.drawTexture(matrices, 226, 81, 176, 0, 36,23);
            this.textRenderer.drawWithShadow(matrices, text, 155, 40, 0xFF6060);
        }
    }
}
