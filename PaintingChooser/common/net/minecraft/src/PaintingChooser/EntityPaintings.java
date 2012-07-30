package net.minecraft.src.PaintingChooser;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.*;

public class EntityPaintings extends EntityPainting
{
    private int tickCounter1;

    public EntityPaintings(World par1World)
    {
        super(par1World);
        this.tickCounter1 = 0;
        this.direction = 0;
        this.yOffset = 0.0F;
        this.setSize(0.5F, 0.5F);
    }

    public EntityPaintings(World par1World, int par2, int par3, int par4, int par5)
    {
        this(par1World);
        this.xPosition = par2;
        this.yPosition = par3;
        this.zPosition = par4;
        ArrayList var6 = new ArrayList();
        EnumArt[] var7 = EnumArt.values();
        int var8 = var7.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            EnumArt var10 = var7[var9];
            this.art = var10;
            this.setDirection(par5);

            if (this.onValidSurface())
            {
                var6.add(var10);
            }
        }

        if (var6.size() > 0)
        {
            this.art = (EnumArt)var6.get(0);
            PaintingChooser.openGui(par1World,var6, this);
        }

        this.setDirection(par5);
    }

    public void setPainting(EnumArt var1)
    {
        this.art = var1;
        this.setDirection(this.direction);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.tickCounter1++ == 100 && !this.worldObj.isRemote)
        {
            this.tickCounter1 = 0;

            if (!this.isDead && !this.onValidSurface())
            {
                this.setDead();
                this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
            }
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (!this.isDead && !this.worldObj.isRemote)
        {
            this.setDead();
            this.setBeenAttacked();
            this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
        }

        return true;
    }
    /**
     * Tries to moves the entity by the passed in displacement. Args: x, y, z
     */
    public void moveEntity(double par1, double par3, double par5)
    {
        if (!this.worldObj.isRemote && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.setDead();
            this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
        }
    }

    /**
     * Adds to the current velocity of the entity. Args: x, y, z
     */
    public void addVelocity(double par1, double par3, double par5)
    {
        if (!this.worldObj.isRemote && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.setDead();
            this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(PChooserCore.itemPaintings)));
        }
    }
}
