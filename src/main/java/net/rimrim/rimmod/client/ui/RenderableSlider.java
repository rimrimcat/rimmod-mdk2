package net.rimrim.rimmod.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class RenderableSlider extends AbstractWidget {
    private final float minValue;                  // Minimum slider value
    private final float maxValue;                  // Maximum slider value
    private float currentValue;                    // Current slider value
    private final ValueChangeListener listener;    // Listener to notify the value change

    public RenderableSlider(int x, int y, int width, int height, float initialValue, float minValue, float maxValue, ValueChangeListener listener) {
        super(x, y, width, height, Component.literal(""));
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.listener = listener;
        this.setValue(initialValue);
    }

    /**
     * Sets the slider's value (clamped between min and max).
     */
    public void setValue(float value) {
        this.currentValue = Mth.clamp(value, minValue, maxValue);
        this.updateMessage();
        this.listener.onValueChanged(this.currentValue);
    }

    /**
     * Gets the current value of the slider.
     */
    public float getValue() {
        return this.currentValue;
    }

    /**
     * Updates the display message to include the current value.
     */
    private void updateMessage() {
        this.setMessage(Component.literal(String.format("%.4f", this.currentValue)));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Draw the background
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0xFFAAAAAA); // Light grey background

        // Draw the slider handle
        int handleWidth = 8; // Width of the slider handle
        int handleX = (int) (this.getX() + (this.currentValue - this.minValue) / (this.maxValue - this.minValue) * (this.width - handleWidth));
        guiGraphics.fill(handleX, this.getY(), handleX + handleWidth, this.getY() + this.height, 0xFFFFFFFF); // White handle

        // Draw the slider text
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, this.getFGColor());
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        this.updateHandlePosition(mouseX); // Update the slider position on click
    }

    @Override
    public void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        this.updateHandlePosition(mouseX); // Update the slider as you drag it
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        // Check if the mouse is within the bounds of this widget
        if (mouseX >= this.getX() && mouseX <= this.getX() + this.width &&
                mouseY >= this.getY() && mouseY <= this.getY() + this.height) {

            // Determine the scroll direction and adjust the slider value
            float increment = (this.maxValue - this.minValue) / 1000.0f; // Define how much to increment/decrement
            if (scrollY > 0) {
                this.setValue(this.getValue() + increment);
            } else if (scrollY < 0) {
                this.setValue(this.getValue() - increment);
            }

            return true; // Indicate the scroll was handled
        }
        return false; // Scroll event not handled
    }



    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    /**
     * Updates the slider's position based on mouse input.
     */
    private void updateHandlePosition(double mouseX) {
        float sliderPosition = (float) ((mouseX - this.getX()) / (this.width - 8)); // Calculate relative position in [0, 1]
        sliderPosition = Mth.clamp(sliderPosition, 0.0F, 1.0F); // Clamp to the slider's range
        this.setValue(this.minValue + sliderPosition * (this.maxValue - this.minValue)); // Map to actual value
    }

    /**
     * Listener interface to handle value changes.
     */
    @FunctionalInterface
    public interface ValueChangeListener {
        void onValueChanged(float newValue);
    }


}