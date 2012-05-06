package MultiTexturedDoors;

import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.StepSound;
import net.minecraft.server.TileEntity;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class BlockMTDoor extends BlockContainer
{
    Class mtDoorEntityClass;

    public BlockMTDoor(int var1, Class var2, float var3, StepSound var4, boolean var5, boolean var6)
    {
        super(var1, Material.STONE);
        this.mtDoorEntityClass = var2;
        this.c(var3);
        this.a(var4);

        if (var5)
        {
            this.s();
        }

        if (var6)
        {
            this.j();
        }

        float var7 = 0.5F;
        float var8 = 1.0F;
        this.a(0.5F - var7, 0.0F, 0.5F - var7, 0.5F + var7, var8, 0.5F + var7);
    }

    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        byte var6 = -1;
        TileEntityMTDoor var7 = (TileEntityMTDoor)var1.getTileEntity(var2, var3, var4);

        if (var7 != null)
        {
            switch (var7.getMetaValue())
            {
                case 0:
                    var6 = 16;
                    break;

                case 1:
                    var6 = 17;
                    break;

                case 2:
                    var6 = 18;
            }
        }

        if (var6 == -1)
        {
            return 4;
        }
        else if (var5 != 0 && var5 != 1)
        {
            int var8 = this.func_48212_i(var1, var2, var3, var4);
            int var9 = var6;

            if ((var8 & 8) != 0)
            {
                var9 = var6 - 16;
            }

            int var10 = var8 & 3;
            boolean var11 = (var8 & 4) != 0;

            if (!var11)
            {
                if (var10 == 0 && var5 == 5)
                {
                    var9 = -var9;
                }
                else if (var10 == 1 && var5 == 3)
                {
                    var9 = -var9;
                }
                else if (var10 == 2 && var5 == 4)
                {
                    var9 = -var9;
                }
                else if (var10 == 3 && var5 == 2)
                {
                    var9 = -var9;
                }

                if ((var8 & 16) != 0)
                {
                    var9 = -var9;
                }
            }
            else if (var10 == 0 && var5 == 2)
            {
                var9 = -var9;
            }
            else if (var10 == 1 && var5 == 5)
            {
                var9 = -var9;
            }
            else if (var10 == 2 && var5 == 3)
            {
                var9 = -var9;
            }
            else if (var10 == 3 && var5 == 4)
            {
                var9 = -var9;
            }

            return var9;
        }
        else
        {
            return var6;
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean a()
    {
        return false;
    }

    public boolean b(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.func_48212_i(var1, var2, var3, var4);
        return (var5 & 4) != 0;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World var1, int var2, int var3, int var4)
    {
        this.updateShape(var1, var2, var3, var4);
        return super.e(var1, var2, var3, var4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess var1, int var2, int var3, int var4)
    {
        this.setDoorRotation(this.func_48212_i(var1, var2, var3, var4));
    }

    public int getDoorOrientation(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.func_48212_i(var1, var2, var3, var4) & 3;
    }

    public boolean func_48213_h(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (this.func_48212_i(var1, var2, var3, var4) & 4) != 0;
    }

    private void setDoorRotation(int var1)
    {
        float var2 = 0.1875F;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int var3 = var1 & 3;
        boolean var4 = (var1 & 4) != 0;
        boolean var5 = (var1 & 16) != 0;

        if (var3 == 0)
        {
            if (!var4)
            {
                this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
            else if (!var5)
            {
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
            else
            {
                this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
        }
        else if (var3 == 1)
        {
            if (!var4)
            {
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
            else if (!var5)
            {
                this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
        }
        else if (var3 == 2)
        {
            if (!var4)
            {
                this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            else if (!var5)
            {
                this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
        }
        else if (var3 == 3)
        {
            if (!var4)
            {
                this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
            else if (!var5)
            {
                this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
            else
            {
                this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void attack(World var1, int var2, int var3, int var4, EntityHuman var5)
    {
        this.interact(var1, var2, var3, var4, var5);
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
    {
        if (this.material == Material.ORE)
        {
            return false;
        }
        else
        {
            int var6 = this.func_48212_i(var1, var2, var3, var4);
            int var7 = var6 & 7;
            var7 ^= 4;

            if ((var6 & 8) != 0)
            {
                var1.setData(var2, var3 - 1, var4, var7);
                var1.b(var2, var3 - 1, var4, var2, var3, var4);
            }
            else
            {
                var1.setData(var2, var3, var4, var7);
                var1.b(var2, var3, var4, var2, var3, var4);
            }

            var1.a(var5, 1003, var2, var3, var4, 0);
            return true;
        }
    }

    public void onPoweredBlockChange(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.func_48212_i(var1, var2, var3, var4);
        boolean var7 = (var6 & 4) != 0;

        if (var7 != var5)
        {
            int var8 = var6 & 7;
            var8 ^= 4;

            if ((var6 & 8) != 0)
            {
                var1.setData(var2, var3 - 1, var4, var8);
                var1.b(var2, var3 - 1, var4, var2, var3, var4);
            }
            else
            {
                var1.setData(var2, var3, var4, var8);
                var1.b(var2, var3, var4, var2, var3, var4);
            }

            var1.a((EntityHuman)null, 1003, var2, var3, var4, 0);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var1.getData(var2, var3, var4);

        if ((var6 & 8) != 0)
        {
            if (var1.getTypeId(var2, var3 - 1, var4) != this.id)
            {
                var1.setTypeId(var2, var3, var4, 0);
            }

            if (var5 > 0 && var5 != this.id)
            {
                this.doPhysics(var1, var2, var3 - 1, var4, var5);
            }
        }
        else
        {
            boolean var7 = false;

            if (var1.getTypeId(var2, var3 + 1, var4) != this.id)
            {
                var1.setTypeId(var2, var3, var4, 0);
                var7 = true;
            }

            if (!var1.isBlockSolidOnSide(var2, var3 - 1, var4, 1))
            {
                var1.setTypeId(var2, var3, var4, 0);
                var7 = true;

                if (var1.getTypeId(var2, var3 + 1, var4) == this.id)
                {
                    var1.setTypeId(var2, var3 + 1, var4, 0);
                }
            }

            if (var7)
            {
                if (!var1.isStatic)
                {
                    this.b(var1, var2, var3, var4, var6, 0);
                }
            }
            else
            {
                boolean var8 = var1.isBlockIndirectlyPowered(var2, var3, var4) || var1.isBlockIndirectlyPowered(var2, var3 + 1, var4);

                if ((var8 || var5 > 0 && Block.byId[var5].isPowerSource() || var5 == 0) && var5 != this.id)
                {
                    this.onPoweredBlockChange(var1, var2, var3, var4, var8);
                }
            }
        }
    }

    /**
     * Called whenever the block is removed.
     */
    public void remove(World var1, int var2, int var3, int var4)
    {
        TileEntityMTDoor var5 = (TileEntityMTDoor)var1.getTileEntity(var2, var3, var4);

        if (var5 != null)
        {
            byte var6 = -1;

            switch (var5.getMetaValue())
            {
                case 0:
                    var6 = 0;
                    break;

                case 1:
                    var6 = 1;
                    break;

                case 2:
                    var6 = 2;
            }

            if (var6 > -1 && var5.getDoorPiece() == 0)
            {
                ItemStack var7 = new ItemStack(MTDCore.mtDoorItem, 1, var6);
                EntityItem var8 = new EntityItem(var1, (double)((float)var2), (double)((float)var3), (double)((float)var4), new ItemStack(var7.id, 1, var7.getData()));
                var1.addEntity(var8);
            }
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition a(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6)
    {
        this.updateShape(var1, var2, var3, var4);
        return super.a(var1, var2, var3, var4, var5, var6);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World var1, int var2, int var3, int var4)
    {
        return var3 >= 255 ? false : var1.isBlockSolidOnSide(var2, var3 - 1, var4, 1) && super.canPlace(var1, var2, var3, var4) && super.canPlace(var1, var2, var3 + 1, var4);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int g()
    {
        return 1;
    }

    public int func_48212_i(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getData(var2, var3, var4);
        boolean var6 = (var5 & 8) != 0;
        int var7;
        int var8;

        if (var6)
        {
            var7 = var1.getData(var2, var3 - 1, var4);
            var8 = var5;
        }
        else
        {
            var7 = var5;
            var8 = var1.getData(var2, var3 + 1, var4);
        }

        boolean var9 = (var8 & 1) != 0;
        int var10 = var7 & 7 | (var6 ? 8 : 0) | (var9 ? 16 : 0);
        return var10;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random var1)
    {
        return 0;
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity a_()
    {
        try
        {
            return (TileEntity)this.mtDoorEntityClass.newInstance();
        }
        catch (Exception var2)
        {
            throw new RuntimeException(var2);
        }
    }
}
