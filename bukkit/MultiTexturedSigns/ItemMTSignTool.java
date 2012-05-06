package MultiTexturedSigns;

import forge.ITextureProvider;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class ItemMTSignTool extends Item implements ITextureProvider
{
    public ItemMTSignTool(int var1)
    {
        super(var1);
        this.e(1);
        this.setMaxDurability(100);
        this.textureId = 32;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
    {
        if (!var2.d(var4, var5, var6))
        {
            return false;
        }
        else
        {
            TileEntity var8 = var3.getTileEntity(var4, var5, var6);

            if (var8 != null && var8 instanceof TileEntityMTSign)
            {
                TileEntityMTSign var9 = (TileEntityMTSign)var8;
                MultiTexturedSigns.displaymtsGuiEditSign(var2, var9);
                var1.damage(5, var2);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public String getTextureFile()
    {
        return MultiTexturedSigns.MTS.getItemSheet();
    }
}
