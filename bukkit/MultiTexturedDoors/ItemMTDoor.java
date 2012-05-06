package MultiTexturedDoors;

import forge.ITextureProvider;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class ItemMTDoor extends Item implements ITextureProvider
{
    private String[] doorNames = new String[] {"Iron", "Gold", "Diamond"};

    public ItemMTDoor(int var1)
    {
        super(var1);
        this.maxStackSize = 1;
        this.a(true);
        this.setMaxDurability(0);
        this.setNoRepair();
    }

    public String a(ItemStack var1)
    {
        return super.getName() + "." + this.doorNames[var1.getData()];
    }

    public int filterData(int var1)
    {
        return var1;
    }

    public ItemMTDoor setBlockNames(String[] var1)
    {
        this.doorNames = var1;
        return this;
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
        Block var8 = MTDCore.mtDoor;

        if (var7 != 1)
        {
            return false;
        }
        else if (var1.count == 0)
        {
            return false;
        }
        else
        {
            ++var5;

            if (var2.d(var4, var5, var6) && var2.d(var4, var5 + 1, var6))
            {
                if (!var8.canPlace(var3, var4, var5, var6))
                {
                    return false;
                }
                else
                {
                    int var9 = MathHelper.floor((double)((var2.yaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    placeDoorBlock(var3, var4, var5, var6, var9, var8, var1.getData());
                    --var1.count;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    public static void placeDoorBlock(World var0, int var1, int var2, int var3, int var4, Block var5, int var6)
    {
        byte var7 = 0;
        byte var8 = 0;

        if (var4 == 0)
        {
            var8 = 1;
        }

        if (var4 == 1)
        {
            var7 = -1;
        }

        if (var4 == 2)
        {
            var8 = -1;
        }

        if (var4 == 3)
        {
            var7 = 1;
        }

        int var9 = (var0.e(var1 - var7, var2, var3 - var8) ? 1 : 0) + (var0.e(var1 - var7, var2 + 1, var3 - var8) ? 1 : 0);
        int var10 = (var0.e(var1 + var7, var2, var3 + var8) ? 1 : 0) + (var0.e(var1 + var7, var2 + 1, var3 + var8) ? 1 : 0);
        boolean var11 = var0.getTypeId(var1 - var7, var2, var3 - var8) == var5.id || var0.getTypeId(var1 - var7, var2 + 1, var3 - var8) == var5.id;
        boolean var12 = var0.getTypeId(var1 + var7, var2, var3 + var8) == var5.id || var0.getTypeId(var1 + var7, var2 + 1, var3 + var8) == var5.id;
        boolean var13 = false;

        if (var11 && !var12)
        {
            var13 = true;
        }
        else if (var10 > var9)
        {
            var13 = true;
        }

        var0.suppressPhysics = true;
        var0.setTypeIdAndData(var1, var2, var3, var5.id, var4);
        TileEntity var14 = var0.getTileEntity(var1, var2, var3);

        if (var14 != null && var14 instanceof TileEntityMTDoor)
        {
            TileEntityMTDoor var15 = (TileEntityMTDoor)var14;
            var15.setMetaValue(var6);
            var15.setDoorPiece(0);
            var15.update();
        }

        var0.setTypeIdAndData(var1, var2 + 1, var3, var5.id, 8 | (var13 ? 1 : 0));
        TileEntity var17 = var0.getTileEntity(var1, var2 + 1, var3);

        if (var17 != null && var17 instanceof TileEntityMTDoor)
        {
            TileEntityMTDoor var16 = (TileEntityMTDoor)var17;
            var16.setMetaValue(var6);
            var16.setDoorPiece(1);
            var16.update();
        }

        var0.suppressPhysics = false;
        var0.applyPhysics(var1, var2, var3, var5.id);
        var0.applyPhysics(var1, var2 + 1, var3, var5.id);
    }

    public String getTextureFile()
    {
        return MultiTexturedDoors.Core.getBlockSheet();
    }
}
