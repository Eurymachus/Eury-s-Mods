package MultiTexturedPPlates;

import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.StepSound;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class BlockMTPPlate extends BlockContainer
{
    Class mtPPlateEntityClass;

    public BlockMTPPlate(int var1, Class var2, float var3, StepSound var4, boolean var5, boolean var6)
    {
        super(var1, Material.ORIENTABLE);
        this.mtPPlateEntityClass = var2;
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

        this.a(true);
        float var7 = 0.0625F;
        this.a(var7, 0.0F, var7, 1.0F - var7, 0.03125F, 1.0F - var7);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int var1, int var2)
    {
        byte var3 = -1;

        switch (var2)
        {
            case 0:
                var3 = 22;
                break;

            case 1:
                var3 = 23;
                break;

            case 2:
                var3 = 24;
        }

        if (var3 == -1)
        {
            var3 = 1;
        }

        return var3;
    }

    /**
     * How many world ticks before ticking
     */
    public int d()
    {
        return 20;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World var1, int var2, int var3, int var4)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean a()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockSolidOnSide(var2, var3 - 1, var4, 1) || var1.getTypeId(var2, var3 - 1, var4) == Block.FENCE.id;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World var1, int var2, int var3, int var4) {}

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        boolean var6 = false;

        if (!var1.isBlockSolidOnSide(var2, var3 - 1, var4, 1) && var1.getTypeId(var2, var3 - 1, var4) != Block.FENCE.id)
        {
            var6 = true;
        }

        if (var6)
        {
            this.b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 0);
            var1.setTypeId(var2, var3, var4, 0);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void a(World var1, int var2, int var3, int var4, Random var5)
    {
        if (!var1.isStatic)
        {
            if (var1.getData(var2, var3, var4) != 0)
            {
                this.setStateIfMobInteractsWithPlate(var1, var2, var3, var4);
            }
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var1.isStatic)
        {
            if (var1.getData(var2, var3, var4) != 1)
            {
                this.setStateIfMobInteractsWithPlate(var1, var2, var3, var4);
            }
        }
    }

    private void setStateIfMobInteractsWithPlate(World var1, int var2, int var3, int var4)
    {
        TileEntity var5 = var1.getTileEntity(var2, var3, var4);

        if (var5 != null && var5 instanceof TileEntityMTPPlate)
        {
            TileEntityMTPPlate var6 = (TileEntityMTPPlate)var5;
            boolean var7 = var1.getData(var2, var3, var4) == 1;
            boolean var8 = false;
            float var9 = 0.125F;
            List var10 = null;

            if (var6.getTriggerType() == 0)
            {
                var10 = var1.getEntities((Entity)null, AxisAlignedBB.b((double)((float)var2 + var9), (double)var3, (double)((float)var4 + var9), (double)((float)(var2 + 1) - var9), (double)var3 + 0.25D, (double)((float)(var4 + 1) - var9)));
            }

            if (var6.getTriggerType() == 1)
            {
                var10 = var1.a(EntityLiving.class, AxisAlignedBB.b((double)((float)var2 + var9), (double)var3, (double)((float)var4 + var9), (double)((float)(var2 + 1) - var9), (double)var3 + 0.25D, (double)((float)(var4 + 1) - var9)));
            }

            if (var6.getTriggerType() == 2)
            {
                var10 = var1.a(EntityHuman.class, AxisAlignedBB.b((double)((float)var2 + var9), (double)var3, (double)((float)var4 + var9), (double)((float)(var2 + 1) - var9), (double)var3 + 0.25D, (double)((float)(var4 + 1) - var9)));
            }

            if (var10.size() > 0)
            {
                var8 = true;
            }

            if (var8 && !var7)
            {
                var1.setData(var2, var3, var4, 1);
                var1.applyPhysics(var2, var3, var4, this.id);
                var1.applyPhysics(var2, var3 - 1, var4, this.id);
                var1.b(var2, var3, var4, var2, var3, var4);
                var1.makeSound((double)var2 + 0.5D, (double)var3 + 0.1D, (double)var4 + 0.5D, "random.click", 0.3F, 0.6F);
            }

            if (!var8 && var7)
            {
                var1.setData(var2, var3, var4, 0);
                var1.applyPhysics(var2, var3, var4, this.id);
                var1.applyPhysics(var2, var3 - 1, var4, this.id);
                var1.b(var2, var3, var4, var2, var3, var4);
                var1.makeSound((double)var2 + 0.5D, (double)var3 + 0.1D, (double)var4 + 0.5D, "random.click", 0.3F, 0.5F);
            }

            if (var8)
            {
                var1.c(var2, var3, var4, this.id, this.d());
            }
        }
    }

    /**
     * Called whenever the block is removed.
     */
    public void remove(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getData(var2, var3, var4);

        if (var5 > 0)
        {
            var1.applyPhysics(var2, var3, var4, this.id);
            var1.applyPhysics(var2, var3 - 1, var4, this.id);
        }

        TileEntityMTPPlate var6 = (TileEntityMTPPlate)var1.getTileEntity(var2, var3, var4);

        if (var6 != null)
        {
            byte var7 = -1;

            switch (var6.getMetaValue())
            {
                case 0:
                    var7 = 0;
                    break;

                case 1:
                    var7 = 1;
                    break;

                case 2:
                    var7 = 2;
            }

            if (var7 > -1)
            {
                ItemStack var8 = new ItemStack(MTPCore.mtPPlate, 1, var7);
                EntityItem var9 = new EntityItem(var1, (double)((float)var2), (double)((float)var3), (double)((float)var4), new ItemStack(var8.id, 1, var8.getData()));
                var1.addEntity(var9);
            }
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess var1, int var2, int var3, int var4)
    {
        boolean var5 = var1.getData(var2, var3, var4) == 1;
        float var6 = 0.0625F;

        if (var5)
        {
            this.a(var6, 0.0F, var6, 1.0F - var6, 0.03125F, 1.0F - var6);
        }
        else
        {
            this.a(var6, 0.0F, var6, 1.0F - var6, 0.0625F, 1.0F - var6);
        }
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean a(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return var1.getData(var2, var3, var4) > 0;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean d(World var1, int var2, int var3, int var4, int var5)
    {
        return var1.getData(var2, var3, var4) == 0 ? false : var5 == 1;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return true;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        float var1 = 0.5F;
        float var2 = 0.125F;
        float var3 = 0.5F;
        this.a(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int g()
    {
        return 1;
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
            return (TileEntity)this.mtPPlateEntityClass.newInstance();
        }
        catch (Exception var2)
        {
            throw new RuntimeException(var2);
        }
    }
}
