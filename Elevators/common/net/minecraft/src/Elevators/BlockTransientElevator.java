package net.minecraft.src.Elevators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockTransientElevator extends Block {
	public BlockTransientElevator(int var1) {
		super(var1, Material.snow);
		setBlockUnbreakable();
		this.setTickRandomly(true);
	}

	/**
	 * Adds to the supplied array any colliding bounding boxes with the passed
	 * in bounding box. Args: world, x, y, z, axisAlignedBB, arrayList
	 */
	public void getCollidingBoundingBoxes(World var1, int var2, int var3,
			int var4, AxisAlignedBB var5, ArrayList var6) {
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2,
			int var3, int var4) {
		return null;
	}

	/**
	 * How many world ticks before ticking
	 */
	public int tickRate() {
		return 2;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	public boolean canProvidePower() {
		return true;
	}

	/**
	 * Is this block indirectly powering the block on the specified side
	 */
	public boolean isIndirectlyPoweringTo(World var1, int var2, int var3,
			int var4, int var5) {
		return this.isPoweringTo(var1, var2, var3, var4, var5);
	}

	/**
	 * Is this block powering the block on the specified side
	 */
	public boolean isPoweringTo(IBlockAccess var1, int var2, int var3,
			int var4, int var5) {
		return var5 != 0;
	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return true;
	}

	/**
	 * Returns if this block is collidable (only used by Fire). Args: x, y, z
	 */
	public boolean isCollidable() {
		return false;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random var1) {
		return 0;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int var1, Random var2, int var3) {
		return 0;
	}

	public void notifyExtendedNeighbors(World var1, int var2, int var3,
			int var4, int var5) {
		var1.notifyBlocksOfNeighborChange(var2, var3, var4, var5);
		var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, var5);
		var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, var5);
		var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, var5);
		var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, var5);
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		if (this.checkForEntity(var1, var2, var3, var4)) {
			var1.scheduleBlockUpdate(var2, var3, var4, this.blockID,
					this.tickRate());
			this.notifyExtendedNeighbors(var1, var2, var3, var4, this.blockID);
		}
	}

	/**
	 * Called whenever the block is removed.
	 */
	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		this.notifyExtendedNeighbors(var1, var2, var3, var4, this.blockID);
	}

	private boolean checkForEntity(World var1, int var2, int var3, int var4) {
		AxisAlignedBB var5 = AxisAlignedBB.getBoundingBoxFromPool(var2, var3,
				var4, (var2 + 1), (var3 + 1), (var4 + 1));
		var5.expand(0.0D, -0.25D, 0.0D);
		List var6 = var1.getEntitiesWithinAABBExcludingEntity((Entity) null,
				var5);
		Iterator var7 = var6.iterator();
		boolean var8;

		for (var8 = false; var7.hasNext() && !var8; var8 = var7.next() instanceof EntityElevator) {
			;
		}

		if (!var8) {
			var1.setBlockWithNotify(var2, var3, var4, 0);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		if (this.checkForEntity(var1, var2, var3, var4)) {
			var1.scheduleBlockUpdate(var2, var3, var4, this.blockID,
					this.tickRate());
		}
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3,
			int var4, int var5) {
		return false;
	}
}
