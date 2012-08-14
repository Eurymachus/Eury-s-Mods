package net.minecraft.src.PaintingChooser;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiButtonPic extends GuiButton {
	protected int p1;
	protected int p2;

	public GuiButtonPic(int var1, int var2, int var3, int var4, int var5,
			int var6, int var7) {
		super(var1, var2, var3, var6, var7, "");
		this.width = var6;
		this.height = var7;
		this.p1 = var4;
		this.p2 = var5;
	}

	/**
	 * Draws this button to the screen.
	 */
	public void drawButton(Minecraft var1, int var2, int var3) {
		if (this.drawButton) {
			FontRenderer var4 = var1.fontRenderer;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,
					var1.renderEngine.getTexture("/art/kz.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean var5 = var2 >= this.xPosition && var3 >= this.yPosition
					&& var2 < this.xPosition + this.width
					&& var3 < this.yPosition + this.height;
			this.getHoverState(var5);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, this.p1,
					this.p2, this.width, this.height);
			this.mouseDragged(var1, var2, var3);
		}
	}
}
