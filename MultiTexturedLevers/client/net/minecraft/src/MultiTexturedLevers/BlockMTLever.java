package net.minecraft.src.MultiTexturedLevers;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StepSound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockMTLever extends BlockContainer {
	private static Minecraft mc = ModLoader.getMinecraftInstance();
	Class mtLeverEntityClass;

	public BlockMTLever(int par1, Class leverClass, float hardness,
			StepSound sound, boolean disableStats, boolean requiresSelfNotify) {
		super(par1, Material.circuits);
		mtLeverEntityClass = leverClass;
		blockIndexInTexture = 96;
		setHardness(hardness);
		setStepSound(sound);
		if (disableStats) {
			disableStats();
		}
		if (requiresSelfNotify) {
			setRequiresSelfNotify();
		}
		this.setTickRandomly(true);
	}

	public static int getDamageValue(IBlockAccess blockaccess, int x, int y,
			int z) {
		TileEntity tileentity = blockaccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTLever) {
			TileEntityMTLever tileentitymtlever = (TileEntityMTLever) tileentity;
			return tileentitymtlever.getMetaValue();
		}
		return 0;
	}

	public static int getMouseOver() {
		if (mc.objectMouseOver != null) {
			int xPosition = mc.objectMouseOver.blockX;
			int yPosition = mc.objectMouseOver.blockY;
			int zPosition = mc.objectMouseOver.blockZ;
			return getDamageValue(mc.theWorld, xPosition, yPosition, zPosition);
		}
		return 0;
	}

	public static int getBelowPlayer(EntityPlayer player) {
		int playerX = (int) player.posX;
		int playerY = (int) player.posY;
		int playerZ = (int) player.posZ;
		return getDamageValue(mc.theWorld, playerX, playerY - 1, playerZ);
	}

	public static int getAtPlayer(EntityPlayer player) {
		int playerX = (int) player.posX;
		int playerY = (int) player.posY;
		int playerZ = (int) player.posZ;
		return getDamageValue(mc.theWorld, playerX, playerY, playerZ);
	}

	@Override
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2,
			int par3, int par4, int par5) {
		switch (getDamageValue(par1IBlockAccess, par2, par3, par4)) {
		case 0:
			return 22;
		case 1:
			return 23;
		case 2:
			return 24;
			// case 3:
			// return 1;
		}
		return 22;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		int texture = -1;
		EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
		if (player.onGround) {
			texture = getMouseOver();
		}
		if (texture == -1 && player.isAirBorne) {
			texture = getMouseOver();
		}
		if (texture == -1 && player.isAirBorne) {
			texture = getBelowPlayer(player);
		}
		if (texture == -1 && player.isAirBorne) {
			texture = getAtPlayer(player);
		}
		switch (texture) {
		case 0:
			return 22;
		case 1:
			return 23;
		case 2:
			return 24;
		}
		return 22;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return null;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return MultiTexturedLevers.mtLeverBlockRenderID;
	}

	/**
	 * checks to see if you can place this block can be placed on that side of a
	 * block: BlockLever overrides
	 */
	@Override
	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3,
			int par4, int par5) {
		return (par5 == 1 && par1World.isBlockSolidOnSide(par2, par3 - 1, par4,
				1))
				|| (par5 == 2 && par1World.isBlockSolidOnSide(par2, par3,
						par4 + 1, 2))
				|| (par5 == 3 && par1World.isBlockSolidOnSide(par2, par3,
						par4 - 1, 3))
				|| (par5 == 4 && par1World.isBlockSolidOnSide(par2 + 1, par3,
						par4, 4))
				|| (par5 == 5 && par1World.isBlockSolidOnSide(par2 - 1, par3,
						par4, 5));
	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return par1World.isBlockSolidOnSide(par2 - 1, par3, par4, 5)
				|| par1World.isBlockSolidOnSide(par2 + 1, par3, par4, 4)
				|| par1World.isBlockSolidOnSide(par2, par3, par4 - 1, 3)
				|| par1World.isBlockSolidOnSide(par2, par3, par4 + 1, 2)
				|| par1World.isBlockSolidOnSide(par2, par3 - 1, par4, 1);
	}

	/**
	 * Called when a block is placed using an item. Used often for taking the
	 * facing and figuring out how to position the item. Args: x, y, z, facing
	 */
	@Override
	public void onBlockPlaced(World par1World, int par2, int par3, int par4,
			int par5) {
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		int var7 = var6 & 8;
		var6 &= 7;
		var6 = -1;

		if (par5 == 1 && par1World.isBlockSolidOnSide(par2, par3 - 1, par4, 1)) {
			var6 = 5 + par1World.rand.nextInt(2);
		}

		if (par5 == 2 && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, 2)) {
			var6 = 4;
		}

		if (par5 == 3 && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, 3)) {
			var6 = 3;
		}

		if (par5 == 4 && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, 4)) {
			var6 = 2;
		}

		if (par5 == 5 && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, 5)) {
			var6 = 1;
		}

		if (var6 == -1) {
			this.dropBlockAsItem(par1World, par2, par3, par4,
					par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		} else {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 + var7);
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
		if (this.checkIfAttachedToBlock(par1World, par2, par3, par4)) {
			int var6 = par1World.getBlockMetadata(par2, par3, par4) & 7;
			boolean var7 = false;

			if (!par1World.isBlockSolidOnSide(par2 - 1, par3, par4, 1)
					&& var6 == 1) {
				var7 = true;
			}

			if (!par1World.isBlockSolidOnSide(par2 + 1, par3, par4, 2)
					&& var6 == 2) {
				var7 = true;
			}

			if (!par1World.isBlockSolidOnSide(par2, par3, par4 - 1, 3)
					&& var6 == 3) {
				var7 = true;
			}

			if (!par1World.isBlockSolidOnSide(par2, par3, par4 + 1, 4)
					&& var6 == 4) {
				var7 = true;
			}

			if (!par1World.isBlockSolidOnSide(par2, par3 - 1, par4, 5)
					&& var6 == 5) {
				var7 = true;
			}

			if (!par1World.isBlockSolidOnSide(par2, par3 - 1, par4, 6)
					&& var6 == 6) {
				var7 = true;
			}

			if (var7) {
				this.dropBlockAsItem(par1World, par2, par3, par4,
						par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockWithNotify(par2, par3, par4, 0);
			}
		}
	}

	/**
	 * Checks if the block is attached to another block. If it is not, it
	 * returns false and drops the block as an item. If it is it returns true.
	 */
	private boolean checkIfAttachedToBlock(World par1World, int par2, int par3,
			int par4) {
		if (!this.canPlaceBlockAt(par1World, par2, par3, par4)) {
			this.dropBlockAsItem(par1World, par2, par3, par4,
					par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
		float var6 = 0.1875F;

		if (var5 == 1) {
			this.setBlockBounds(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F,
					0.5F + var6);
		} else if (var5 == 2) {
			this.setBlockBounds(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F,
					0.8F, 0.5F + var6);
		} else if (var5 == 3) {
			this.setBlockBounds(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F,
					var6 * 2.0F);
		} else if (var5 == 4) {
			this.setBlockBounds(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F,
					0.5F + var6, 0.8F, 1.0F);
		} else {
			var6 = 0.25F;
			this.setBlockBounds(0.5F - var6, 0.0F, 0.5F - var6, 0.5F + var6,
					0.6F, 0.5F + var6);
		}
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4,
			EntityPlayer par5EntityPlayer) {
		this.blockActivated(par1World, par2, par3, par4, par5EntityPlayer);
	}

	/**
	 * Called upon block activation (left or right click on the block.). The
	 * three integers represent x,y,z of the block.
	 */
	@Override
	public boolean blockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer) {
		if (par1World.isRemote) {
			return true;
		} else {
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			int var7 = var6 & 7;
			int var8 = 8 - (var6 & 8);
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 + var8);
			par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
			par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D,
					"random.click", 0.3F, var8 > 0 ? 0.6F : 0.5F);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4,
					this.blockID);

			if (var7 == 1) {
				par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4,
						this.blockID);
			} else if (var7 == 2) {
				par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4,
						this.blockID);
			} else if (var7 == 3) {
				par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1,
						this.blockID);
			} else if (var7 == 4) {
				par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1,
						this.blockID);
			} else {
				par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4,
						this.blockID);
			}

			return true;
		}
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		int var5 = par1World.getBlockMetadata(par2, par3, par4);

		if ((var5 & 8) > 0) {
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4,
					this.blockID);
			int var6 = var5 & 7;

			if (var6 == 1) {
				par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4,
						this.blockID);
			} else if (var6 == 2) {
				par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4,
						this.blockID);
			} else if (var6 == 3) {
				par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1,
						this.blockID);
			} else if (var6 == 4) {
				par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1,
						this.blockID);
			} else {
				par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4,
						this.blockID);
			}
		}
		ItemStack itemstack = new ItemStack(MTLCore.mtLeverItem, 1,
				getDamageValue(par1World, par2, par3, par4));
		EntityItem entityitem = new EntityItem(par1World, par2, par3, par4,
				new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
		par1World.spawnEntityInWorld(entityitem);
		super.onBlockRemoval(par1World, par2, par3, par4);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	/**
	 * Is this block powering the block on the specified side
	 */
	@Override
	public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2,
			int par3, int par4, int par5) {
		return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0;
	}

	/**
	 * Is this block indirectly powering the block on the specified side
	 */
	@Override
	public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3,
			int par4, int par5) {
		int var6 = par1World.getBlockMetadata(par2, par3, par4);

		if ((var6 & 8) == 0) {
			return false;
		} else {
			int var7 = var6 & 7;
			return var7 == 6 && par5 == 1 ? true
					: (var7 == 5 && par5 == 1 ? true
							: (var7 == 4 && par5 == 2 ? true : (var7 == 3
									&& par5 == 3 ? true : (var7 == 2
									&& par5 == 4 ? true : var7 == 1
									&& par5 == 5))));
		}
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	@Override
	public boolean canProvidePower() {
		return true;
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		float var1 = 0.1875F;
		float var2 = 0.125F;
		float var3 = 0.125F;
		this.setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1,
				0.5F + var2, 0.5F + var3);
	}

	@Override
	public TileEntity getBlockEntity() {
		try {
			return (TileEntity) mtLeverEntityClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
}
