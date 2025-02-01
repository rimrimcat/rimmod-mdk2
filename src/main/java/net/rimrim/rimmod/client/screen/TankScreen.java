package net.rimrim.rimmod.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.menu.TankMenu;

public class TankScreen extends AbstractContainerScreen<TankMenu> {
    private final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(
            RimMod.MODID,
            "textures/gui/container/tank.png");
//    private final ResourceLocation STILL_TEXTURE = ResourceLocation.withDefaultNamespace(
//            "textures/block/water_still.png"
//    );

    public static final int imageWidth = 176;
    public static final int imageHeight = 166;

    public TankScreen(TankMenu menu, Inventory playerInventory, Component title) {
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


    private static int getFluidHeight(IFluidTank tank) {
        return (int) (48 * ((float) tank.getFluidAmount() / tank.getCapacity()));
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

        FluidTank fluidTank = menu.getBlockEntity().getFluidTank();

        if (!isHovering(125, 20,
                16, 48,
                mouseX, mouseY)) return;

        Component comp = Component.literal("%s/%s mB %s".formatted(
                fluidTank.getFluidAmount(),
                fluidTank.getCapacity(),
                fluidTank.getFluid().getHoverName().getString()
        ));
        guiGraphics.renderTooltip(
                this.font,
                comp,
                mouseX,
                mouseY);


    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBg(graphics, partialTick, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTick);


        FluidTank tank = this.menu.getBlockEntity().getFluidTank();
        FluidStack fluidStack = tank.getFluid();
        if (fluidStack.isEmpty()) return;

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());

        int tintColor;
        switch (fluidStack.getHoverName().getString()) {
            case "Lava" -> tintColor = packColor(255, 102, 0, 255);
            case "Water" -> tintColor = packColor(65, 107, 223, 255);
            default -> tintColor = fluidTypeExtensions.getTintColor(fluidStack);
        }

        int fluidHeight = getFluidHeight(tank);

        graphics.fill(
                this.leftPos + 125,
                this.topPos + 20 + (48 - fluidHeight),
                this.leftPos + 125 + 16,
                this.topPos + 20 + 48,
                tintColor
        );

        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();

        // Tick things here
    }


    private static int packColor(int r, int g, int b, int a) {
        int red = r & 0xFF;
        int green = g & 0xFF;
        int blue = b & 0xFF;
        int alpha = a & 0xFF;
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

}
