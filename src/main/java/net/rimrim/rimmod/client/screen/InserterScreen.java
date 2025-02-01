package net.rimrim.rimmod.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.menu.InserterMenu;

public class InserterScreen extends AbstractContainerScreen<InserterMenu> {
    private final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(
            RimMod.MODID,
            "textures/gui/container/inserter.png");

    private final Component label = Component.translatable(RimMod.MODID + ".container.inserter");

    public static final int imageWidth = 176;
    public static final int imageHeight = 166;

    public InserterScreen(InserterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        /*
         * Renders the background texture to the screen. 'leftPos' and
         * 'topPos' should already represent the top left corner of where
         * the texture should be rendered as it was precomputed from the
         * 'imageWidth' and 'imageHeight'. The two zeros represent the
         * integer u/v coordinates inside the PNG file, whose size is
         * represented by the last two integers (typically 256 x 256).
         */
        guiGraphics.blit(
                RenderType::guiTextured,
                BACKGROUND_LOCATION,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256, 256);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);

        // Assume we have some Component 'label'
        // 'label' is drawn at 'labelX' and 'labelY'
        // The color is an ARGB value, if the alpha is less than 4, than the alpha is set to 255
        // The final boolean renders the drop shadow when true
        // graphics.drawString(this.font, this.label, this.labelX, this.labelY, 0x404040, false);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBg(graphics, partialTick, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTick);

        /*
         * This method is added by the container screen to render
         * the tooltip of the hovered slot.
         */
        this.renderTooltip(graphics, mouseX, mouseY);
    }


    @Override
    protected void containerTick() {
        super.containerTick();

        // Tick things here
    }
}
