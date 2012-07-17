package net.minecraft.src.MultiTexturedLevers;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.BlockLever;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StepSound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.IContainer;

public class BlockMTLever extends BlockLever implements IContainer {
	Class mtLeverEntityClass;

	public BlockMTLever(int par1, Class leverClass, float hardness,
			StepSound sound, boolean disableStats, boolean requiresSelfNotify) {
		super(par1, 96);
		this.isBlockContainer = true;
		mtLeverEntityClass = leverClass;
		setHardness(hardness);
		setStepSound(sound);
		if (disableStats) {
			disableStats();
		}
		if (requiresSelfNotify) {
			setRequiresSelfNotify();
		}
	}

	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2,
			int par3, int par4, int par5) {
		switch (MultiTexturedLevers.getDamageValue(par1IBlockAccess, par2, par3, par4)) {
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
		EntityPlayer player = MultiTexturedLevers.getPlayer();
		if (player.onGround) {
			texture = MultiTexturedLevers.getMouseOver();
		}
		if (texture == -1 && player.isAirBorne) {
			texture = MultiTexturedLevers.getMouseOver();
		}
		if (texture == -1 && player.isAirBorne) {
			texture = MultiTexturedLevers.getBelowPlayer(player);
		}
		if (texture == -1 && player.isAirBorne) {
			texture = MultiTexturedLevers.getAtPlayer(player);
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
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return MultiTexturedLevers.mtLeverBlockRenderID;
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		ItemStack itemstack = new ItemStack(MTLCore.mtLeverItem, 1,
				MultiTexturedLevers.getDamageValue(par1World, par2, par3, par4));
		EntityItem entityitem = new EntityItem(par1World, par2, par3, par4,
				new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
		par1World.spawnEntityInWorld(entityitem);
		super.onBlockRemoval(par1World, par2, par3, par4);
		par1World.removeBlockTileEntity(par2, par3, par4);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}
	
	@Override
	public TileEntity getTileEntity(int meta) {
		return getBlockEntity(meta);
	}

	@Override
	public TileEntity getBlockEntity() {
		try {
			return (TileEntity) mtLeverEntityClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public TileEntity getBlockEntity(int meta) {
		return getBlockEntity();
	}
}
