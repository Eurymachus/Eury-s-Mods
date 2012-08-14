package net.minecraft.src.PaintingChooser;

import java.util.Random;

import net.minecraft.src.Entity;
import net.minecraft.src.EnumArt;
import net.minecraft.src.MathHelper;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderPaintings extends Render {
	/** RNG. */
	private Random rand = new Random();

	public void func_158_a(EntityPaintings par1EntityPaintings, double par2,
			double par4, double par6, float par8, float par9) {
		EnumArt var10 = par1EntityPaintings.art;
		if (var10 != null) {
			this.rand.setSeed(187L);
			GL11.glPushMatrix();
			GL11.glTranslatef((float) par2, (float) par4, (float) par6);
			GL11.glRotatef(par8, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			this.loadTexture("/art/kz.png");
			float var11 = 0.0625F;
			GL11.glScalef(var11, var11, var11);
			this.func_159_a(par1EntityPaintings, var10.sizeX, var10.sizeY,
					var10.offsetX, var10.offsetY);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	private void func_159_a(EntityPaintings par1EntityPainting, int par2,
			int par3, int par4, int par5) {
		float var6 = (-par2) / 2.0F;
		float var7 = (-par3) / 2.0F;
		float var8 = -0.5F;
		float var9 = 0.5F;

		for (int var10 = 0; var10 < par2 / 16; ++var10) {
			for (int var11 = 0; var11 < par3 / 16; ++var11) {
				float var12 = var6 + (var10 + 1) * 16;
				float var13 = var6 + var10 * 16;
				float var14 = var7 + (var11 + 1) * 16;
				float var15 = var7 + var11 * 16;
				this.func_160_a(par1EntityPainting, (var12 + var13) / 2.0F,
						(var14 + var15) / 2.0F);
				float var16 = (par4 + par2 - var10 * 16) / 256.0F;
				float var17 = (par4 + par2 - (var10 + 1) * 16) / 256.0F;
				float var18 = (par5 + par3 - var11 * 16) / 256.0F;
				float var19 = (par5 + par3 - (var11 + 1) * 16) / 256.0F;
				float var20 = 0.75F;
				float var21 = 0.8125F;
				float var22 = 0.0F;
				float var23 = 0.0625F;
				float var24 = 0.75F;
				float var25 = 0.8125F;
				float var26 = 0.001953125F;
				float var27 = 0.001953125F;
				float var28 = 0.7519531F;
				float var29 = 0.7519531F;
				float var30 = 0.0F;
				float var31 = 0.0625F;
				Tessellator var32 = Tessellator.instance;
				var32.startDrawingQuads();
				var32.setNormal(0.0F, 0.0F, -1.0F);
				var32.addVertexWithUV(var12, var15,
						var8, var17, var18);
				var32.addVertexWithUV(var13, var15,
						var8, var16, var18);
				var32.addVertexWithUV(var13, var14,
						var8, var16, var19);
				var32.addVertexWithUV(var12, var14,
						var8, var17, var19);
				var32.setNormal(0.0F, 0.0F, 1.0F);
				var32.addVertexWithUV(var12, var14,
						var9, var20, var22);
				var32.addVertexWithUV(var13, var14,
						var9, var21, var22);
				var32.addVertexWithUV(var13, var15,
						var9, var21, var23);
				var32.addVertexWithUV(var12, var15,
						var9, var20, var23);
				var32.setNormal(0.0F, 1.0F, 0.0F);
				var32.addVertexWithUV(var12, var14,
						var8, var24, var26);
				var32.addVertexWithUV(var13, var14,
						var8, var25, var26);
				var32.addVertexWithUV(var13, var14,
						var9, var25, var27);
				var32.addVertexWithUV(var12, var14,
						var9, var24, var27);
				var32.setNormal(0.0F, -1.0F, 0.0F);
				var32.addVertexWithUV(var12, var15,
						var9, var24, var26);
				var32.addVertexWithUV(var13, var15,
						var9, var25, var26);
				var32.addVertexWithUV(var13, var15,
						var8, var25, var27);
				var32.addVertexWithUV(var12, var15,
						var8, var24, var27);
				var32.setNormal(-1.0F, 0.0F, 0.0F);
				var32.addVertexWithUV(var12, var14,
						var9, var29, var30);
				var32.addVertexWithUV(var12, var15,
						var9, var29, var31);
				var32.addVertexWithUV(var12, var15,
						var8, var28, var31);
				var32.addVertexWithUV(var12, var14,
						var8, var28, var30);
				var32.setNormal(1.0F, 0.0F, 0.0F);
				var32.addVertexWithUV(var13, var14,
						var8, var29, var30);
				var32.addVertexWithUV(var13, var15,
						var8, var29, var31);
				var32.addVertexWithUV(var13, var15,
						var9, var28, var31);
				var32.addVertexWithUV(var13, var14,
						var9, var28, var30);
				var32.draw();
			}
		}
	}

	private void func_160_a(EntityPaintings par1EntityPainting, float par2,
			float par3) {
		int var4 = MathHelper.floor_double(par1EntityPainting.posX);
		int var5 = MathHelper.floor_double(par1EntityPainting.posY
				+ par3 / 16.0F);
		int var6 = MathHelper.floor_double(par1EntityPainting.posZ);

		if (par1EntityPainting.direction == 0) {
			var4 = MathHelper.floor_double(par1EntityPainting.posX
					+ par2 / 16.0F);
		}

		if (par1EntityPainting.direction == 1) {
			var6 = MathHelper.floor_double(par1EntityPainting.posZ
					- par2 / 16.0F);
		}

		if (par1EntityPainting.direction == 2) {
			var4 = MathHelper.floor_double(par1EntityPainting.posX
					- par2 / 16.0F);
		}

		if (par1EntityPainting.direction == 3) {
			var6 = MathHelper.floor_double(par1EntityPainting.posZ
					+ par2 / 16.0F);
		}

		int var7 = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(
				var4, var5, var6, 0);
		int var8 = var7 % 65536;
		int var9 = var7 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
				var8, var9);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		this.func_158_a((EntityPaintings) par1Entity, par2, par4, par6, par8,
				par9);
	}
}
