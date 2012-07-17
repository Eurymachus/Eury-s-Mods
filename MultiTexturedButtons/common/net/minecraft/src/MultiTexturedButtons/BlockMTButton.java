package net.minecraft.src.MultiTexturedButtons;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockButton;
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
import net.minecraft.src.EurysMods.core.IContainer;

public class BlockMTButton extends BlockButton implements IContainer {
	Class mtButtonEntityClass;

	public BlockMTButton(int par1, Class buttonClass, float hardness,
			StepSound sound, boolean disableStats, boolean requiresSelfNotify) {
		super(par1, 0);
		this.isBlockContainer = true;
		mtButtonEntityClass = buttonClass;
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
		switch (MultiTexturedButtons.getDamageValue(par1IBlockAccess, par2, par3, par4)) {
		case 0:
			return 22;
		case 1:
			return 23;
		case 2:
			return 24;
		default:
			return 22;
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par0, int par1) {
		return MultiTexturedButtons.getTextureFromMetaData(par1);
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		ItemStack itemstack = new ItemStack(MTBCore.BlockMTButton, 1,
				MultiTexturedButtons.getDamageValue(par1World, par2, par3, par4));
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

	public TileEntity getBlockEntity() {
		try {
			return (TileEntity) mtButtonEntityClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public TileEntity getBlockEntity(int meta) {
		return getBlockEntity();
	}

	@Override
	public TileEntity getTileEntity(int meta) {
		return getBlockEntity(meta);
	}
}
