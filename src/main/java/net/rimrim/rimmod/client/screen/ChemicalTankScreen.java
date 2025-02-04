package net.rimrim.rimmod.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.chem.stack.ChemicalStackHandler;
import net.rimrim.rimmod.menu.ChemicalTankMenu;

public class ChemicalTankScreen extends AbstractContainerScreen<ChemicalTankMenu> {
    private final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(
            RimMod.MODID,
            "textures/gui/container/tank.png");
    //    private final ResourceLocation STILL_TEXTURE = ResourceLocation.withDefaultNamespace(
    //            "textures/block/water_still.png"
    //    );

    public static final int imageWidth = 176;
    public static final int imageHeight = 166;

    public ChemicalTankScreen(ChemicalTankMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(
                RenderType::guiTextured,
                BACKGROUND_LOCATION,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256, 256);

        guiGraphics.fill(
                this.leftPos + 125,
                this.topPos + 20,
                this.leftPos + 125 + 16,
                this.topPos + 20 + 48,
                0xFFAAAAAA
        );

    }


    private static int getChemicalHeight(ChemicalStackHandler chemHandler) {
        return (int) (48 * (chemHandler.fillRatio()));
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);

        // Assume we have some Component 'label'
        // 'label' is drawn at 'labelX' and 'labelY'
        // The color is an ARGB value, if the alpha is less than 4, than the alpha is set to 255
        // The final boolean renders the drop shadow when true
        // graphics.drawString(this.font, this.label, this.labelX, this.labelY, 0x404040, false);

        // TEMPORARY STUFF TO SEE CONTENTS
        // graphics.drawString(this.font, Component.literal("Fluid: %s".formatted(menu.getBlockEntity().getFluidTank().getFluidAmount())), 20, 20, 0x404040, false);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        if (!isHovering(125, 20,
                16, 48,
                mouseX, mouseY)) return;

        guiGraphics.renderTooltip(
                this.font,
                this.menu.getBlockEntity().getChemHandler().getCapacityString(),
                mouseX,
                mouseY);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBg(graphics, partialTick, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTick);


        ChemicalStackHandler chemHandler = this.menu.getBlockEntity().getChemHandler();
        int fluidHeight = getChemicalHeight(chemHandler);

        graphics.fill(
                this.leftPos + 125,
                this.topPos + 20 + (48 - fluidHeight),
                this.leftPos + 125 + 16,
                this.topPos + 20 + 48,
                chemHandler.chemStack().chemical().color
        );

        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();

        // Tick things here
    }

}
