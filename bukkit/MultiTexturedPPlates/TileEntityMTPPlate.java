package MultiTexturedPPlates;

import EurysMods.network.PacketPayload;
import MultiTexturedPPlates.network.PacketUpdateMTPPlate;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class TileEntityMTPPlate extends TileEntity
{
    public int metaValue;
    public int triggerType;

    public int getMetaValue()
    {
        return this.metaValue;
    }

    public void setMetaValue(int var1)
    {
        this.metaValue = var1;
    }

    public int getTriggerType()
    {
        return this.triggerType;
    }

    public void setTriggerType(int var1)
    {
        this.triggerType = var1;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        super.b(var1);
        var1.setInt("metaValue", this.getMetaValue());
        var1.setInt("triggerType", this.getTriggerType());
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound var1)
    {
        super.a(var1);
        this.setMetaValue(var1.getInt("metaValue"));
        this.setTriggerType(var1.getInt("triggerType"));
    }

    public PacketPayload getPacketPayload()
    {
        int[] var1 = new int[2];
        float[] var2 = new float[1];
        String[] var3 = new String[1];
        var1[0] = this.metaValue;
        var1[1] = this.triggerType;
        var2[0] = 0.0F;
        var3[0] = "";
        PacketPayload var4 = new PacketPayload();
        var4.intPayload = var1;
        var4.floatPayload = var2;
        var4.stringPayload = var3;
        return var4;
    }

    /**
     * Overriden in a sign to provide the text
     */
    public Packet d()
    {
        return this.getUpdatePacket();
    }

    public Packet getUpdatePacket()
    {
        return (new PacketUpdateMTPPlate(this)).getPacket();
    }

    public void handleUpdatePacket(PacketUpdateMTPPlate var1, World var2)
    {
        this.setMetaValue(var1.getItemDamage());
        this.setTriggerType(var1.getTriggerType());
        this.update();
        var2.notify(var1.xPosition, var1.yPosition, var1.zPosition);
    }
}
