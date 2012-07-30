package net.minecraft.src.Elevators;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

import org.lwjgl.opengl.GL11;

public class RenderElevator extends Render {
	private RenderBlocks renderBlocks = new RenderBlocks();

	public RenderElevator() {
		this.shadowSize = 0.5F;
	}

	public void renderElevatorEntity(Block var1, World var2, int var3,
			int var4, int var5, int[] var6) {
		float var7 = 0.5F;
		float var8 = 1.0F;
		float var9 = 0.8F;
		float var10 = 0.6F;
		Tessellator var11 = Tessellator.instance;
		var11.startDrawingQuads();
		var11.setBrightness(var1.getMixedBrightnessForBlock(var2, var3, var4,
				var5));
		float var12 = 1.0F;
		float var13 = 1.0F;

		if (var13 < var12) {
			var13 = var12;
		}

		var11.setColorOpaque_F(var7 * var13, var7 * var13, var7 * var13);
		this.renderBlocks.renderBottomFace(var1, -0.5D, -0.5D, -0.5D, var6[0]);
		var13 = 1.0F;

		if (var13 < var12) {
			var13 = var12;
		}

		var11.setColorOpaque_F(var8 * var13, var8 * var13, var8 * var13);
		this.renderBlocks.renderTopFace(var1, -0.5D, -0.5D, -0.5D, var6[1]);
		var13 = 1.0F;

		if (var13 < var12) {
			var13 = var12;
		}

		var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
		this.renderBlocks.renderEastFace(var1, -0.5D, -0.5D, -0.5D, var6[2]);
		var13 = 1.0F;

		if (var13 < var12) {
			var13 = var12;
		}

		var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
		this.renderBlocks.renderWestFace(var1, -0.5D, -0.5D, -0.5D, var6[2]);
		var13 = 1.0F;

		if (var13 < var12) {
			var13 = var12;
		}

		var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
		this.renderBlocks.renderNorthFace(var1, -0.5D, -0.5D, -0.5D, var6[2]);
		var13 = 1.0F;

		if (var13 < var12) {
			var13 = var12;
		}

		var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
		this.renderBlocks.renderSouthFace(var1, -0.5D, -0.5D, -0.5D, var6[2]);
		var11.draw();
	}

	public void doRenderElevator(EntityElevator var1, double var2, double var4,
			double var6, float var8, float var9) {
		GL11.glPushMatrix();
		Block var10 = ElevatorsCore.Elevator;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float) var2, (float) var4, (float) var6);
		this.loadTexture("/terrain.png");
		int[] var11 = new int[] { ElevatorsCore.sideTexture,
				ElevatorsCore.sideTexture, ElevatorsCore.sideTexture };
		var11[0] = var1.isCeiling() ? ElevatorsCore.topTexture
				: ElevatorsCore.sideTexture;
		var11[1] = var1.isCeiling() ? ElevatorsCore.sideTexture
				: ElevatorsCore.topTexture;
		var11[2] = ElevatorsCore.sideTexture;
		this.renderElevatorEntity(var10, var1.getWorld(),
				MathHelper.floor_double(var1.posX),
				MathHelper.floor_double(var1.posY),
				MathHelper.floor_double(var1.posZ), var11);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity var1, double var2, double var4, double var6,
			float var8, float var9) {
		this.doRenderElevator((EntityElevator) var1, var2, var4, var6, var8,
				var9);
	}
}
