package net.minecraft.src.PaintingChooser;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class ItemPaintings extends Item
{
    public ItemPaintings(int par1)
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
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int par7)
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

            if (!par2EntityPlayer.canPlayerEdit(x, y, z))
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
            			if (painting.xPosition-x < 1 && painting.yPosition-y < 1 && painting.zPosition-z < 1) {
            				ModLoader.getLogger().warning("PAINTING IS HERE");
            				return true;
            			}
            		}
            	}
                EntityPaintings var9 = new EntityPaintings(par3World, par2EntityPlayer, x, y, z, var8);

                if (var9.onValidSurface())
                {
                    if (!par3World.isRemote)
                    {
                        par3World.spawnEntityInWorld(var9);
                    }

                    --par1ItemStack.stackSize;
                }

                return true;
            }
        }
    }
}
