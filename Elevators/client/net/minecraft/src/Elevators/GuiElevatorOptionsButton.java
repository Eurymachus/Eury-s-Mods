package net.minecraft.src.Elevators;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiElevatorOptionsButton extends GuiButton {
	int width;
	int height;

	public GuiElevatorOptionsButton(int var1, int var2, int var3) {
		super(var1, var2, var3, 16, 16, "");
	}

	/**
	 * Draws this button to the screen.
	 */
	public void drawButton(Minecraft var1, int var2, int var3) {
		FontRenderer var4 = var1.fontRenderer;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,
				var1.renderEngine.getTexture("/gui/elevatorgui.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		boolean var5 = var2 >= this.xPosition && var3 >= this.yPosition
				&& var2 < this.xPosition + this.width
				&& var3 < this.yPosition + this.height;
		int var6 = this.getHoverState(var5) - 1;

		if (var6 < 0 || var6 > 1) {
			var6 = 0;
		}

		this.drawTexturedModalRect(this.xPosition, this.yPosition, 215,
				21 * var6, 21, 21);
		this.drawString(var4, this.displayString, this.xPosition + 16 + 1,
				this.yPosition + this.height / 2 - 2, 16777120);
	}
}
