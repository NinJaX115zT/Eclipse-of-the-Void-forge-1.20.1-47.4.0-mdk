package net.lucarioninja.eclipseofthevoid.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EtherealHiveScreen extends AbstractContainerScreen<EtherealHiveMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EclipseOfTheVoid.MOD_ID,
            "textures/gui/ethereal_hive_gui.png");

    public EtherealHiveScreen(EtherealHiveMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        renderProductionBar(guiGraphics, x, y);
        renderCombProgressBar(guiGraphics, x, y);
        renderFloatingBee(guiGraphics, x, y);
        renderText(guiGraphics, x, y);
    }

    private void renderProductionBar(GuiGraphics guiGraphics, int x, int y) {
        int bees = menu.getBeeCount();
        int productionWidth = (int)(bees / 16.0 * 66); // full width at max bees

        if (productionWidth > 0) {
            guiGraphics.blit(TEXTURE, x + 68, y + 33, 1, 166, productionWidth, 11); // adjusted UV for production bar
        }
    }

    private void renderCombProgressBar(GuiGraphics guiGraphics, int x, int y) {
        int progress = menu.getProgress(); // 0 to 100
        int barWidth = (int)(progress * 43 / 100.0F); // scale to 43px

        if (barWidth > 0) {
            guiGraphics.blit(TEXTURE, x + 45, y + 56, 1, 166, barWidth, 11); // adjusted UV for comb progress bar
        }
    }

    private void renderFloatingBee(GuiGraphics guiGraphics, int x, int y) {
        if (this.menu.getBeeCount() > 0) {
            int floatOffset = (System.currentTimeMillis() / 500 % 2 == 0) ? 0 : 1;
            guiGraphics.blit(TEXTURE, x + 43, y + 31 + floatOffset, 176, 1, 18, 16); // ensure bee fits 18x16
        }
    }

    private void renderText(GuiGraphics guiGraphics, int x, int y) {
        int beeCount = this.menu.getBeeCount();
        int production = (int)(beeCount / 16.0 * 100);
        int combProgress = this.menu.getProgress();

        String beeDisplay = beeCount == 32 ? "MAX" : String.valueOf(beeCount);
        guiGraphics.drawString(this.font, "Bee Count: " + beeDisplay, x + 60, y + 17, 0xE0B0FF, false);
        guiGraphics.drawString(this.font, "Prod LVL: " + production + "%", x + 69, y + 35, 0xE0B0FF, false);
        guiGraphics.drawString(this.font, "Progress: " + combProgress + "%", x + 40, y + 69, 0xE0B0FF, false);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Skipping default label rendering
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(guiGraphics, pMouseX, pMouseY);
    }
}
