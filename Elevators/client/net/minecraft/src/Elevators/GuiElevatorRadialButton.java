package net.minecraft.src.Elevators;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiElevatorRadialButton extends GuiButton {
	int width;
	int height;

	public GuiElevatorRadialButton(int var1, int var2, int var3, String var4) {
		super(var1, var2, var3, 35, 16, var4);
	}

	/**
	 * Draws this button to the screen.
	 */
	public void drawButton(Minecraft var1, int var2, int var3) {
		if (this.drawButton) {
			FontRenderer var4 = var1.fontRenderer;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,
					var1.renderEngine.getTexture("/gui/elevatorgui.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int var5 = this.enabled ? 0 : 1;
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 215,
					42 + var5 * 13, 13, 13);
			int var6 = this.enabled ? 0 : 4013373;
			var4.drawString(this.displayString, this.xPosition + 13 + 1,
					this.yPosition + this.height / 2 - 4, var6);
		}
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of
	 * MouseListener.mouseDragged(MouseEvent e).
	 */
	protected void mouseDragged(Minecraft var1, int var2, int var3) {
	}

	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(int var1, int var2) {
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of
	 * MouseListener.mousePressed(MouseEvent e).
	 */
	public boolean mousePressed(Minecraft var1, int var2, int var3) {
		if (var2 >= this.xPosition && var3 >= this.yPosition
				&& var2 < this.xPosition + this.width
				&& var3 < this.yPosition + this.height) {
			this.enabled = !this.enabled;
			return true;
		} else {
			return false;
		}
	}
}
