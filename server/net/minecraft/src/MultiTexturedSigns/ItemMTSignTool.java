package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_MultiTexturedSigns;
import net.minecraft.src.MultiTexturedSigns.network.PacketOpenGui;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMTSignTool extends Item
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
        	if (tileentity != null && tileentity instanceof TileEntityMTSign)
        	{
		        TileEntityMTSign tileentitymtsign = (TileEntityMTSign)tileentity;
		        if (tileentitymtsign != null)
		        {
		        	PacketOpenGui gui = new PacketOpenGui(tileentitymtsign.xCoord, tileentitymtsign.yCoord, tileentitymtsign.zCoord);
		        	MTSCore.displaymtsGuiEditSign(entityplayer, gui);
		        	itemstack.damageItem(5, entityplayer);
		        }
		        return true;
        	}
        	else return false;
        }
    }
}
