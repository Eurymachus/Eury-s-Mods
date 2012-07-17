package net.minecraft.src.MultiTexturedSigns;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.BlockSign;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockMTSign extends BlockSign implements ITextureProvider {

	public BlockMTSign(int i, Class class1, boolean flag, float hardness,
			float resistance, boolean disableStats, boolean requiresSelfNotify) {
		super(i, class1, flag);

		setHardness(hardness);
		setResistance(resistance);
		if (disableStats)
			disableStats();
		if (requiresSelfNotify)
			setRequiresSelfNotify();
	}

	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2,
			int par3, int par4, int par5) {
		return MultiTexturedSigns.getDamageValue(par1IBlockAccess, par2, par3, par4);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return MultiTexturedSigns.getBlockTextureFromMetadata(par2);
	}

	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		int itemDamage = -1;
		switch (MultiTexturedSigns.getDamageValue(world, i, j, k)) {
		case 0:
			itemDamage = 0;
			break;
		case 1:
			itemDamage = 2;
			break;
		case 2:
			itemDamage = 4;
			break;
		}
		if (itemDamage > -1) {
			ItemStack itemstack = new ItemStack(MTSCore.mtsItemSignParts,
					1, itemDamage);
			EntityItem entityitem = new EntityItem(world, i, j, k,
					new ItemStack(itemstack.itemID, 1,
							itemstack.getItemDamage()));
			world.spawnEntityInWorld(entityitem);
		}
		super.onBlockRemoval(world, i, j, k);
	}

	@Override
	public String getTextureFile() {
		return MultiTexturedSigns.MTS.getBlockSheet();
	}
}
