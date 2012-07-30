package net.minecraft.src.Elevators;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.MathHelper;

import org.lwjgl.opengl.GL11;

public class GuiElevatorSlider extends GuiButton {
	int width;
	int height;
	public float sliderValue;
	private float sliderPosition;
	public float maximumValue;
	public boolean dragging;
	private boolean discrete;
	String message;

	public GuiElevatorSlider(int var1, int var2, int var3, float var4,
			float var5, boolean var6, String var7) {
		super(var1, var2, var3, 150, 20, "");
		this.sliderValue = 1.0F;
		this.sliderPosition = 1.0F;
		this.maximumValue = 1.0F;
		this.dragging = false;
		this.discrete = false;
		this.message = "";
		this.sliderValue = var4;
		this.maximumValue = var5;
		this.sliderPosition = (this.sliderValue - 1.0F) / this.maximumValue;
		this.discrete = var6;
		this.message = var7;
		this.nameString();
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over
	 * this button and 2 if it IS hovering over this button.
	 */
	protected int getHoverState(boolean var1) {
		return 0;
	}

	private void nameString() {
		if (this.discrete) {
			this.displayString = this.message
					+ String.valueOf((int) this.sliderValue);
		} else {
			this.displayString = this.message
					+ String.valueOf(this.sliderValue);
		}

		if (this.sliderValue == this.maximumValue + 1.0F) {
			this.displayString = "All Below Ground";
		} else if (this.sliderValue == 1.0F) {
			this.displayString = "All Above Ground";
		}
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of
	 * MouseListener.mouseDragged(MouseEvent e).
	 */
	protected void mouseDragged(Minecraft var1, int var2, int var3) {
		if (this.drawButton) {
			if (this.dragging) {
				this.sliderPosition = (float) (var2 - (this.xPosition + 4))
						/ (float) (this.width - 8);

				if (this.discrete) {
					this.sliderPosition = MathHelper
							.floor_float(this.sliderPosition
									* this.maximumValue + 0.5F)
							/ this.maximumValue;
				}

				if (this.sliderPosition < 0.0F) {
					this.sliderPosition = 0.0F;
				}

				if (this.sliderPosition > 1.0F) {
					this.sliderPosition = 1.0F;
				}

				this.sliderValue = 1.0F + this.sliderPosition
						* this.maximumValue;
				this.nameString();
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(this.xPosition
					+ (int) (this.sliderPosition * (this.width - 8)),
					this.yPosition, 0, 66, 4, 20);
			this.drawTexturedModalRect(this.xPosition
					+ (int) (this.sliderPosition * (this.width - 8))
					+ 4, this.yPosition, 196, 66, 4, 20);
		}
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of
	 * MouseListener.mousePressed(MouseEvent e).
	 */
	public boolean mousePressed(Minecraft var1, int var2, int var3) {
		if (super.mousePressed(var1, var2, var3)) {
			if (this.dragging) {
				this.dragging = false;
				return true;
			} else {
				this.sliderPosition = (float) (var2 - (this.xPosition + 4))
						/ (float) (this.width - 8);

				if (this.discrete) {
					this.sliderPosition = MathHelper
							.floor_float(this.sliderPosition
									* this.maximumValue + 0.5F)
							/ this.maximumValue;
				}

				if (this.sliderPosition < 0.0F) {
					this.sliderPosition = 0.0F;
				}

				if (this.sliderPosition > 1.0F) {
					this.sliderPosition = 1.0F;
				}

				this.sliderValue = 1.0F + this.sliderPosition
						* this.maximumValue;
				this.nameString();
				this.dragging = true;
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(int var1, int var2) {
		this.dragging = false;
	}
}
