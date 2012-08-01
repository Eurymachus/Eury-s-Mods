package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;

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
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer entityplayer, World par3World, int x, int y, int z, int par7)
    {
        if (!entityplayer.canPlayerEdit(x, y, z))
        {
            return false;
        }
        else
        {
        	Entity entity;
        	for (int i = 0; i < par3World.loadedEntityList.size(); i++) {
        		Entity loadedEntity = (Entity)par3World.loadedEntityList.get(i);
        		if (loadedEntity instanceof EntityPainting) {
        			EntityPainting painting = (EntityPainting)loadedEntity;
        			if (Math.abs(painting.xPosition - x) < 1 && Math.abs(painting.yPosition - y) < 1 && Math.abs(painting.zPosition - z) < 1) {
        	    		EnumArt currentArt = painting.art;
        	    		int direction = painting.direction;
        	            ArrayList artList = new ArrayList();
        	            EnumArt[] enumart = EnumArt.values();
        	            int enumartlength = enumart.length;

        	            for (int var9 = 0; var9 < enumartlength; ++var9)
        	            {
        	                EnumArt newArt = enumart[var9];
        	                painting.art = newArt;
        	                painting.setDirection(direction);

        	                if (painting.onValidSurface())
        	                {
        	                    artList.add(newArt);
        	                }
        	            }
        	            painting.art = currentArt;
        	            painting.setDirection(direction);
        	            
        	            if (artList.size() > 0) {
        	            	PaintingChooser.openGui(painting.worldObj, entityplayer, painting, artList);
        	            }
        				return true;
        			}
        		}
        	}
        }
        return false;
    }
}
