package net.minecraft.src.PaintingChooser;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class ItemPaintingChooser extends Item
{
    public ItemPaintingChooser(int par1)
    {
        super(par1);
    }
    
    public int getIconFromDamage(int i) {
    	return 26;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
    {
        if (par7 == 0)
        {
            return false;
        }
        else if (par7 == 1)
        {
            return false;
        }
        else
        {
            byte var8 = 0;

            if (par7 == 4)
            {
                var8 = 1;
            }

            if (par7 == 3)
            {
                var8 = 2;
            }

            if (par7 == 5)
            {
                var8 = 3;
            }

            if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
}