package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumArt;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.mod_PaintingChooser;
import net.minecraft.src.EurysMods.core.IProxy;
import net.minecraft.src.PaintingChooser.network.PacketUpdatePainting;

public class EntityPaintings extends EntityPainting {
	private int tickCounter1;
	private boolean firstTick = false;
	protected String owner;

	public EntityPaintings(World world) {
		super(world);
	}

	public EntityPaintings(World world, EntityPlayer entityplayer, int x,
			int y, int z, int facing) {
		this(world);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		ArrayList artList = new ArrayList();
		EnumArt[] enumart = EnumArt.values();
		int enumartlength = enumart.length;

		for (int var9 = 0; var9 < enumartlength; ++var9) {
			EnumArt newArt = enumart[var9];
			this.art = newArt;
			this.setDirection(facing);

			if (this.onValidSurface()) {
				artList.add(newArt);
			}
		}

		if (artList.size() > 0) {
			this.art = (EnumArt) artList.get(this.rand.nextInt(artList.size()));
		}

		this.setDirection(facing);
		this.owner = entityplayer.username;
		if (artList.size() > 0) {
			PaintingChooser.openGui(this.worldObj, entityplayer, this, artList);
		}
	}

	public EntityPaintings(World world, EntityPlayer entityplayer, int x,
			int y, int z, int facing, String title) {
		this(world);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		if (title.equals("")) {
			this.art = null;
		} else {
			EnumArt[] var7 = EnumArt.values();
			int var8 = var7.length;

			for (int var9 = 0; var9 < var8; ++var9) {
				EnumArt var10 = var7[var9];

				if (var10.title.equals(title)) {
					this.art = var10;
					break;
				}
			}
		}

		this.setDirection(facing);
	}

	public void setPainting(EnumArt enumart) {
		this.art = enumart;
		this.setDirection(this.direction);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (firstTick == false) {
			this.updatePainting();
			firstTick = true;
		}
		if (this.tickCounter1++ == 100 && !this.worldObj.isRemote) {
			this.tickCounter1 = 0;
			if (!this.isDead && !this.onValidSurface()) {
				this.setDead();
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj,
						this.posX, this.posY, this.posZ, new ItemStack(
								PChooserCore.itemPaintings)));
			} else {
				this.updatePainting();
			}
		}
	}

	public void updatePainting() {
		PaintingChooser.PChooser.getProxy();
		if (!IProxy.isClient) {
			if (this != null && this.art != null) {
				PacketUpdatePainting paintingPacket = new PacketUpdatePainting(
						this);
				if (!firstTick) {
					paintingPacket.setCommand("FIRSTPAINTING");
					paintingPacket.setArtTitle("");
				} else {
					paintingPacket.setCommand("UPDATEPAINTING");
					paintingPacket.setArtTitle(this.art.title);
				}
				PaintingChooser.PChooser.getProxy().sendPacketToAll(
						paintingPacket.getPacket(), this.xPosition,
						this.yPosition, this.zPosition, 16,
						mod_PaintingChooser.instance);
			}
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (!this.isDead && !this.worldObj.isRemote) {
			this.setDead();
			this.setBeenAttacked();
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj,
					this.posX, this.posY, this.posZ, new ItemStack(
							PChooserCore.itemPaintings)));
		}

		return true;
	}

	/**
	 * Tries to moves the entity by the passed in displacement. Args: x, y, z
	 */
	@Override
	public void moveEntity(double par1, double par3, double par5) {
		if (!this.worldObj.isRemote && !this.isDead
				&& par1 * par1 + par3 * par3 + par5 * par5 > 0.0D) {
			this.setDead();
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj,
					this.posX, this.posY, this.posZ, new ItemStack(
							PChooserCore.itemPaintings)));
		}
	}

	/**
	 * Adds to the current velocity of the entity. Args: x, y, z
	 */
	@Override
	public void addVelocity(double par1, double par3, double par5) {
		if (!this.worldObj.isRemote && !this.isDead
				&& par1 * par1 + par3 * par3 + par5 * par5 > 0.0D) {
			this.setDead();
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj,
					this.posX, this.posY, this.posZ, new ItemStack(
							PChooserCore.itemPaintings)));
		}
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	/*
	 * @Override public boolean interact(EntityPlayer entityplayer) { if
	 * (!this.worldObj.isRemote) { if (entityplayer.username.equals(this.owner))
	 * { EnumArt currentArt = this.art; int direction = this.direction;
	 * ArrayList artList = new ArrayList(); EnumArt[] var7 = EnumArt.values();
	 * int var8 = var7.length;
	 * 
	 * for (int var9 = 0; var9 < var8; ++var9) { EnumArt var10 = var7[var9];
	 * this.art = var10; this.setDirection(direction);
	 * 
	 * if (this.onValidSurface()) { artList.add(var10); } } this.art =
	 * currentArt; this.setDirection(direction);
	 * 
	 * if (artList.size() > 0) { PaintingChooser.openGui(this.worldObj,
	 * entityplayer, this, artList); } return true; } } return false; }
	 */

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setString("owner", this.owner);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		this.owner = par1NBTTagCompound.getString("owner");
	}
}
