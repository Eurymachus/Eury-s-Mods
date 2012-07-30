package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Elevators.ElevatorsCore;
import net.minecraft.src.Elevators.GuiElevator;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiElevatorList {
	private final Minecraft mc;
	private final int width;
	protected final int fullHeight;
	protected final int minHeight;
	private int height;
	protected final int top;
	protected int bottom;
	private final int right;
	private final int left;
	protected final int slotHeight;
	private int scrollUpButtonID;
	private int scrollDownButtonID;
	protected int mouseX;
	protected int mouseY;
	private float initialClickY = -2.0F;
	private float scrollMultiplier;
	private float amountScrolled;
	public int selectedElement = -1;
	private long lastClicked = 0L;
	private final GuiElevator parentScreen;
	public final List itemList = new ArrayList();
	public boolean extended = false;
	public final int guiID;

	public GuiElevatorList(GuiElevator var1, int var2, int var3, int var4,
			int var5, int var6, int var7, int var8, Set var9) {
		this.parentScreen = var1;
		this.mc = GuiElevator.minecraftInstance;
		this.width = var5;
		this.height = var6;
		this.fullHeight = var8;
		this.minHeight = var6;
		this.top = var4;
		this.bottom = var4 + var6;
		this.slotHeight = var7;
		this.left = var3;
		this.right = var3 + var5;

		if (var9 != null) {
			this.itemList.addAll(var9);
		}

		this.guiID = var2;
	}

	public int getSize() {
		return this.itemList.size();
	}

	protected void elementClicked(int var1, boolean var2) {
		this.selectedElement = var1;

		if (var2) {
			this.parentScreen.doubleClickedListItem(this.guiID, var1);
		}
	}

	protected boolean isSelected(int var1) {
		return this.selectedElement == var1;
	}

	protected void drawBackground() {
	}

	protected void drawSlot(int var1, int var2, int var3, int var4,
			Tessellator var5) {
		int var6 = var1 + 1;
		String var7 = "Floor "
				+ ElevatorsCore.getAbbreviatedFloorName(var6,
						this.parentScreen.floorOne);

		if (!ElevatorsCore.isNamed(var6, this.parentScreen.floors)) {
			var7 = var7 + " [Unnamed]";
		}

		this.parentScreen.drawString(this.parentScreen.fontRenderer, var6
				+ ": " + var7, var2 + 2, var3 + 1, 16777215);
	}

	protected int getContentHeight() {
		return this.getSize() * this.slotHeight;
	}

	public void registerScrollButtons(List var1, int var2, int var3) {
		this.scrollUpButtonID = var2;
		this.scrollDownButtonID = var3;
	}

	private void bindAmountScrolled() {
		int var1 = this.getContentHeight() - (this.bottom - this.top - 4);

		if (var1 < 0) {
			var1 /= 2;
		}

		if (this.amountScrolled < 0.0F) {
			this.amountScrolled = 0.0F;
		}

		if (this.amountScrolled > var1) {
			this.amountScrolled = var1;
		}
	}

	public void actionPerformed(GuiButton var1) {
		if (var1.enabled) {
			if (var1.id == this.scrollUpButtonID) {
				this.amountScrolled -= this.slotHeight;
				this.initialClickY = -2.0F;
				this.bindAmountScrolled();
			} else if (var1.id == this.scrollDownButtonID) {
				this.amountScrolled += this.slotHeight;
				this.initialClickY = -2.0F;
				this.bindAmountScrolled();
			}
		}
	}

	public void minimize() {
		this.extended = false;
		this.height = this.minHeight;
		this.bottom = this.top + this.height;
	}

	public void maximize() {
		this.extended = true;
		this.height = this.fullHeight;
		this.bottom = this.top + this.height;
	}

	public boolean mousePressed(int var1, int var2) {
		return var1 >= this.left && var2 >= this.top && var1 < this.right + 10
				&& var2 < this.bottom;
	}

	public void setAmountScrolled() {
		this.amountScrolled = (this.slotHeight * this.selectedElement);
		this.bindAmountScrolled();
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.mouseX = var1;
		this.mouseY = var2;
		this.drawBackground();
		int var4 = this.getSize();
		int var5 = this.right;
		int var6 = var5 + 10;
		int var7 = this.left;
		int var8 = this.right;
		int var10;
		int var12;
		int var13;

		if (Mouse.isButtonDown(0)) {
			if (this.initialClickY == -1.0F) {
				boolean var14 = true;

				if (this.mousePressed(var1, var2)) {
					var10 = var2 - this.top + (int) this.amountScrolled - 4;
					int var11 = var10 / this.slotHeight;

					if (!this.extended) {
						this.maximize();
					}

					if (var1 >= var7 && var1 <= var8 && var11 >= 0
							&& var10 >= 0 && var11 < var4) {
						boolean var15 = System.currentTimeMillis()
								- this.lastClicked < 500L;

						if (var15) {
							this.minimize();
							this.selectedElement = var11;
							this.amountScrolled = (this.slotHeight * this.selectedElement);
						}

						this.lastClicked = System.currentTimeMillis();
					} else if (var1 >= var7 && var1 <= var8 && var10 < 0) {
						var14 = false;
					}

					if (var1 >= var5 && var1 <= var6) {
						this.scrollMultiplier = -1.0F;
						var13 = this.getContentHeight()
								- (this.bottom - this.top - 4);

						if (var13 < 1) {
							var13 = 1;
						}

						var12 = (int) ((float) ((this.bottom - this.top) * (this.bottom - this.top)) / (float) this
								.getContentHeight());

						if (var12 < 32) {
							var12 = 32;
						}

						if (var12 > this.bottom - this.top - 8) {
							var12 = this.bottom - this.top - 8;
						}

						this.scrollMultiplier /= (float) (this.bottom
								- this.top - var12)
								/ (float) var13;

						if (this.extended) {
							this.scrollMultiplier *= 2.0F;
						}
					} else {
						this.scrollMultiplier = 1.0F;
					}

					if (var14) {
						this.initialClickY = var2;
					} else {
						this.initialClickY = -2.0F;
					}
				} else {
					this.initialClickY = -2.0F;
				}
			} else if (this.initialClickY >= 0.0F) {
				this.amountScrolled -= (MathHelper
						.floor_float((var2 - this.initialClickY)
								* this.scrollMultiplier
								/ this.slotHeight) * this.slotHeight);
				this.initialClickY = var2;
			}
		} else {
			while (Mouse.next()) {
				int var22 = Mouse.getEventDWheel();

				if (var22 != 0) {
					if (var22 > 0) {
						var22 = -1;
					} else if (var22 < 0) {
						var22 = 1;
					}

					this.amountScrolled += (var22 * this.slotHeight);
				}
			}

			this.initialClickY = -1.0F;
		}

		this.bindAmountScrolled();

		if (!this.extended) {
			this.selectedElement = MathHelper.floor_float(this.amountScrolled
					/ this.slotHeight + 0.5F);
		}

		Tessellator var24 = Tessellator.instance;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,
				this.mc.renderEngine.getTexture("/gui/elevatorbg.png"));
		float var23 = 32.0F;
		var24.startDrawingQuads();
		var24.setColorOpaque_I(2105376);
		var24.addVertexWithUV(
				this.left,
				this.bottom,
				0.0D,
				(this.left / var23),
				((this.bottom + (int) this.amountScrolled) / var23));
		var24.addVertexWithUV(
				this.right,
				this.bottom,
				0.0D,
				(this.right / var23),
				((this.bottom + (int) this.amountScrolled) / var23));
		var24.addVertexWithUV(
				this.right,
				this.top,
				0.0D,
				(this.right / var23),
				((this.top + (int) this.amountScrolled) / var23));
		var24.addVertexWithUV(
				this.left,
				this.top,
				0.0D,
				(this.left / var23),
				((this.top + (int) this.amountScrolled) / var23));
		var24.draw();
		int var9 = this.width / 2 - 92 - 16;
		var10 = this.top + 4 - (int) this.amountScrolled;

		for (int var17 = 0; var17 < var4; ++var17) {
			int var18 = var10 + var17 * this.slotHeight;
			int var19 = this.slotHeight - 4;

			if (var18 <= this.bottom - 8 && var18 + var19 >= this.top + 8) {
				if (this.isSelected(var17)) {
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					var24.startDrawingQuads();
					var24.setColorOpaque_I(8421504);
					var24.addVertexWithUV(var7, (var18
							+ var19 + 2), 0.0D, 0.0D, 1.0D);
					var24.addVertexWithUV(var8, (var18
							+ var19 + 2), 0.0D, 1.0D, 1.0D);
					var24.addVertexWithUV(var8, (var18 - 2),
							0.0D, 1.0D, 0.0D);
					var24.addVertexWithUV(var7, (var18 - 2),
							0.0D, 0.0D, 0.0D);
					var24.setColorOpaque_I(0);
					var24.addVertexWithUV((var7 + 1), (var18
							+ var19 + 1), 0.0D, 0.0D, 1.0D);
					var24.addVertexWithUV((var8 - 1), (var18
							+ var19 + 1), 0.0D, 1.0D, 1.0D);
					var24.addVertexWithUV((var8 - 1),
							(var18 - 1), 0.0D, 1.0D, 0.0D);
					var24.addVertexWithUV((var7 + 1),
							(var18 - 1), 0.0D, 0.0D, 0.0D);
					var24.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}

				this.drawSlot(var17, var7, var18, var19, var24);
			}
		}

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		byte var25 = 4;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		var24.startDrawingQuads();
		var24.setColorRGBA_I(0, 0);
		var24.addVertexWithUV(this.left, (this.top + var25),
				0.0D, 0.0D, 1.0D);
		var24.addVertexWithUV(this.right, (this.top + var25),
				0.0D, 1.0D, 1.0D);
		var24.setColorRGBA_I(0, 255);
		var24.addVertexWithUV(this.right, this.top, 0.0D,
				1.0D, 0.0D);
		var24.addVertexWithUV(this.left, this.top, 0.0D,
				0.0D, 0.0D);
		var24.draw();
		var24.startDrawingQuads();
		var24.setColorRGBA_I(0, 255);
		var24.addVertexWithUV(this.left, this.bottom, 0.0D,
				0.0D, 1.0D);
		var24.addVertexWithUV(this.right, this.bottom, 0.0D,
				1.0D, 1.0D);
		var24.setColorRGBA_I(0, 0);
		var24.addVertexWithUV(this.right,
				(this.bottom - var25), 0.0D, 1.0D, 0.0D);
		var24.addVertexWithUV(this.left,
				(this.bottom - var25), 0.0D, 0.0D, 0.0D);
		var24.draw();
		var13 = this.getContentHeight() - (this.bottom - this.top - 4);

		if (var13 > 0) {
			var12 = (this.bottom - this.top) * (this.bottom - this.top)
					/ this.getContentHeight();

			if (var12 < 32) {
				var12 = 32;
			}

			if (var12 > this.bottom - this.top - 8) {
				var12 = this.bottom - this.top - 8;
			}

			int var16 = (int) this.amountScrolled
					* (this.bottom - this.top - var12) / var13 + this.top;

			if (var16 < this.top) {
				var16 = this.top;
			}

			var24.startDrawingQuads();
			var24.setColorRGBA_I(0, 255);
			var24.addVertexWithUV(var5, this.bottom, 0.0D,
					0.0D, 1.0D);
			var24.addVertexWithUV(var6, this.bottom, 0.0D,
					1.0D, 1.0D);
			var24.addVertexWithUV(var6, this.top, 0.0D, 1.0D,
					0.0D);
			var24.addVertexWithUV(var5, this.top, 0.0D, 0.0D,
					0.0D);
			var24.draw();
			var24.startDrawingQuads();
			var24.setColorRGBA_I(8421504, 255);
			var24.addVertexWithUV(var5, (var16 + var12),
					0.0D, 0.0D, 1.0D);
			var24.addVertexWithUV(var6, (var16 + var12),
					0.0D, 1.0D, 1.0D);
			var24.addVertexWithUV(var6, var16, 0.0D, 1.0D,
					0.0D);
			var24.addVertexWithUV(var5, var16, 0.0D, 0.0D,
					0.0D);
			var24.draw();
			var24.startDrawingQuads();
			var24.setColorRGBA_I(12632256, 255);
			var24.addVertexWithUV(var5, (var16 + var12 - 1),
					0.0D, 0.0D, 1.0D);
			var24.addVertexWithUV((var6 - 1),
					(var16 + var12 - 1), 0.0D, 1.0D, 1.0D);
			var24.addVertexWithUV((var6 - 1), var16, 0.0D,
					1.0D, 0.0D);
			var24.addVertexWithUV(var5, var16, 0.0D, 0.0D,
					0.0D);
			var24.draw();
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private void overlayBackground(int var1, int var2, int var3, int var4) {
		Tessellator var5 = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,
				this.mc.renderEngine.getTexture("/gui/background.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var6 = 32.0F;
		var5.startDrawingQuads();
		var5.setColorRGBA_I(4210752, var4);
		var5.addVertexWithUV(0.0D, var2, 0.0D, 0.0D,
				(var2 / var6));
		var5.addVertexWithUV(this.width, var2, 0.0D,
				(this.width / var6),
				(var2 / var6));
		var5.setColorRGBA_I(4210752, var3);
		var5.addVertexWithUV(this.width, var1, 0.0D,
				(this.width / var6),
				(var1 / var6));
		var5.addVertexWithUV(0.0D, var1, 0.0D, 0.0D,
				(var1 / var6));
		var5.draw();
	}
}
