package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_MultiTexturedSigns;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTSignTool extends Item implements ITextureProvider
{
	public ItemMTSignTool(int i)
	{
		super(i);
		setMaxStackSize(1);
        setMaxDamage(100);
        iconIndex = 32;
	}
	
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if (!entityplayer.canPlayerEdit(i, j, k))
        {
            return false;
        }
        else
        {
        	TileEntity tileentity = world.getBlockTileEntity(i, j, k);
        	if ((tileentity != null) && (tileentity instanceof TileEntityMTSign))
        	{
		        TileEntityMTSign mtstileentitysign = (TileEntityMTSign)tileentity;
		        if (mtstileentitysign != null)
		        {
		        	MultiTexturedSigns.displaymtsGuiEditSign(entityplayer, mtstileentitysign);
		        	itemstack.damageItem(5, entityplayer);
		        }
        	}
	        return true;
        }
    }

	public String getTextureFile()
	{
    	return MultiTexturedSigns.MTSCore.getItemSheet();
	}
}
