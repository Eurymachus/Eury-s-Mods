package MultiTexturedLevers;

import forge.ITextureProvider;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class ItemMTLever extends Item implements ITextureProvider
{
    private String[] leverNames = new String[] {"Iron", "Gold", "Diamond"};

    public ItemMTLever(int var1)
    {
        super(var1);
        this.a(true);
        this.setMaxDurability(0);
        this.setNoRepair();
    }

    public String a(ItemStack var1)
    {
        return super.getName() + "." + this.leverNames[var1.getData()];
    }

    public int filterData(int var1)
    {
        return var1;
    }

    public int getIconFromDamage(int var1)
    {
        return var1;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
    {
        Block var8 = MTLCore.mtLever;

        if (var7 == 0)
        {
            --var5;
        }

        if (var7 == 1)
        {
            ++var5;
        }

        if (var7 == 2)
        {
            --var6;
        }

        if (var7 == 3)
        {
            ++var6;
        }

        if (var7 == 4)
        {
            --var4;
        }

        if (var7 == 5)
        {
            ++var4;
        }

        if (var1.count == 0)
        {
            return false;
        }
        else if (!var2.d(var4, var5, var6))
        {
            return false;
        }
        else if (!var8.canPlace(var3, var4, var5, var6))
        {
            return false;
        }
        else if (var5 == 255 && var8.material.isBuildable())
        {
            return false;
        }
        else if (var3.mayPlace(var8.id, var4, var5, var6, false, var7))
        {
            if (var3.setTypeIdAndData(var4, var5, var6, var8.id, this.filterData(var1.getData())))
            {
                if (var3.getTypeId(var4, var5, var6) == var8.id)
                {
                    var8.postPlace(var3, var4, var5, var6, var7);
                    var8.postPlace(var3, var4, var5, var6, var2);
                    TileEntity var9 = var3.getTileEntity(var4, var5, var6);

                    if (var9 != null && var9 instanceof TileEntityMTLever)
                    {
                        TileEntityMTLever var10 = (TileEntityMTLever)var9;
                        var10.setMetaValue(var1.getData());
                        var10.update();
                    }
                }

                var3.makeSound((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), var8.stepSound.getName(), (var8.stepSound.getVolume1() + 1.0F) / 2.0F, var8.stepSound.getVolume2() * 0.8F);
                --var1.count;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public String getTextureFile()
    {
        return MultiTexturedLevers.Core.getItemSheet();
    }
}
