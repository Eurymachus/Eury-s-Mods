package net.minecraft.src.MultiTexturedBeds;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemBed;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTBed extends ItemBed implements ITextureProvider {
	private static final String[] bedNames = new String[] { "black", "red",
			"green", "brown", "blue", "purple", "cyan", "silver", "gray",
			"pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };

	public ItemMTBed(int par1) {
		super(par1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setNoRepair();
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return (new StringBuilder()).append(super.getItemName()).append(".")
				.append(bedNames[itemstack.getItemDamage()]).toString();
	}

	public int filterData(int i) {
		return i;
	}

	public int getIconFromDamage(int i) {
		return i;
	}

	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. This is for ITEMS, not BLOCKS !
	 */
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7) {
		if (par7 != 1) {
			return false;
		} else {
			++par5;
			BlockMTBed var8 = (BlockMTBed) MTBedsCore.mtBlockBed;
			int var9 = MathHelper
					.floor_double((par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			byte var10 = 0;
			byte var11 = 0;

			if (var9 == 0) {
				var11 = 1;
			}

			if (var9 == 1) {
				var10 = -1;
			}

			if (var9 == 2) {
				var11 = -1;
			}

			if (var9 == 3) {
				var10 = 1;
			}

			if (par2EntityPlayer.canPlayerEdit(par4, par5, par6)
					&& par2EntityPlayer.canPlayerEdit(par4 + var10, par5, par6
							+ var11)) {
				if (par3World.isAirBlock(par4, par5, par6)
						&& par3World.isAirBlock(par4 + var10, par5, par6
								+ var11)
						&& par3World.isBlockNormalCube(par4, par5 - 1, par6)
						&& par3World.isBlockNormalCube(par4 + var10, par5 - 1,
								par6 + var11)) {
					par3World.setBlockAndMetadataWithNotify(par4, par5, par6,
							var8.blockID, var9);
					TileEntity tileentity = par3World.getBlockTileEntity(par4,
							par5, par6);
					if (tileentity != null
							&& tileentity instanceof TileEntityMTBed) {
						((TileEntityMTBed) tileentity)
								.setMetaValue(par1ItemStack.getItemDamage());
						((TileEntityMTBed) tileentity).setBedPiece(0);
						tileentity.onInventoryChanged();
					}

					if (par3World.getBlockId(par4, par5, par6) == var8.blockID) {
						par3World.setBlockAndMetadataWithNotify(par4 + var10,
								par5, par6 + var11, var8.blockID, var9 + 8);
						TileEntity tileentity1 = par3World.getBlockTileEntity(
								par4 + var10, par5, par6 + var11);
						if (tileentity1 != null
								&& tileentity1 instanceof TileEntityMTBed) {
							((TileEntityMTBed) tileentity1)
									.setMetaValue(par1ItemStack.getItemDamage());
							((TileEntityMTBed) tileentity1).setBedPiece(1);
							tileentity1.onInventoryChanged();
						}
					}

					--par1ItemStack.stackSize;
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	@Override
	public String getTextureFile() {
		return MultiTexturedBeds.MTBed.getItemSheet();
	}
}
