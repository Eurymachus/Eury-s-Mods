package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_MultiTexturedSigns;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTSigns extends Item implements ITextureProvider
{
    public ItemMTSigns(int i)
    {
        super(i);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setNoRepair();
    }
    
    private String[] signNames = new String[]{"IronSign", "GoldSign", "DiamondSign"};
    
    @Override
    public String getItemNameIS(ItemStack itemstack)
    {
    	return (new StringBuilder())
        .append(super.getItemName())
        .append(".")
        .append(signNames[itemstack.getItemDamage()])
        .toString();
    }

    @Override
    public int getIconFromDamage(int i)
    {
    	int index = i;
        switch(i)
        {
	        case 0: return 16;
	        case 1: return 17;
	        case 2: return 18;
	        default: return 19;
        }
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	if (world.isRemote) return false;
        if (l == 0)
        {
            return false;
        }
        if (!world.getBlockMaterial(i, j, k).isSolid())
        {
            return false;
        }
        if (l == 1)
        {
            j++;
        }
        if (l == 2)
        {
            k--;
        }
        if (l == 3)
        {
            k++;
        }
        if (l == 4)
        {
            i--;
        }
        if (l == 5)
        {
            i++;
        }
        if (!entityplayer.canPlayerEdit(i, j, k))
        {
            return false;
        }
        if (!MultiTexturedSigns.mtSignPost.canPlaceBlockAt(world, i, j, k))
        {
            return false;
        }
        if (l == 1)
        {
            int i1 = MathHelper.floor_double((double)(((entityplayer.rotationYaw + 180F) * 16F) / 360F) + 0.5D) & 0xf;
            world.setBlockAndMetadataWithNotify(i, j, k, MultiTexturedSigns.mtSignPost.blockID, i1);
        }
        else
        {
            world.setBlockAndMetadataWithNotify(i, j, k, MultiTexturedSigns.mtSignWall.blockID, l);
        }
        itemstack.stackSize--;
        TileEntity tileentity = world.getBlockTileEntity(i, j, k);
        if (tileentity != null && tileentity instanceof TileEntityMTSign)
        {
            TileEntityMTSign tileentitymtsign = (TileEntityMTSign)tileentity;
	        tileentitymtsign.setMetaValue(itemstack.getItemDamage());
	        MultiTexturedSigns.displaymtsGuiEditSign(entityplayer, tileentitymtsign);
        }
        return true;
    }
    
    public String getTextureFile()
    {
            return MultiTexturedSigns.MTSCore.getItemSheet();
    }
}
