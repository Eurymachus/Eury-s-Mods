package MultiTexturedSigns;

import EurysMods.network.PacketPayload;
import MultiTexturedSigns.network.PacketUpdateMTSign;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class TileEntityMTSign extends TileEntity
{
    public String[] mtSignText = new String[] {"", "", "", ""};
    public int mtsLineBeingEdited = -1;
    private boolean mtsIsEditable = true;
    private int metaValue;

    public boolean getIsEditAble()
    {
        return this.mtsIsEditable;
    }

    public String[] getMtSignText()
    {
        return this.mtSignText;
    }

    public void setMtSignText(String[] var1)
    {
        for (int var2 = 0; var2 < 4; ++var2)
        {
            this.mtSignText[var2] = var1[var2];
        }
    }

    public int getMetaValue()
    {
        return this.metaValue;
    }

    public void setMetaValue(int var1)
    {
        this.metaValue = var1;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        super.b(var1);
        var1.setInt("metaValue", this.metaValue);
        var1.setString("mtsText1", this.mtSignText[0]);
        var1.setString("mtsText2", this.mtSignText[1]);
        var1.setString("mtsText3", this.mtSignText[2]);
        var1.setString("mtsText4", this.mtSignText[3]);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound var1)
    {
        this.mtsIsEditable = true;
        super.a(var1);
        this.metaValue = var1.getInt("metaValue");

        for (int var2 = 0; var2 < 4; ++var2)
        {
            this.mtSignText[var2] = var1.getString("mtsText" + (var2 + 1));

            if (this.mtSignText[var2].length() > 15)
            {
                this.mtSignText[var2] = this.mtSignText[var2].substring(0, 15);
            }
        }
    }

    public PacketPayload getPacketPayload()
    {
        int[] var1 = new int[1];
        float[] var2 = new float[1];
        var1[0] = this.metaValue;
        var2[0] = 0.0F;
        String[] var3 = this.mtSignText;
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
        return (new PacketUpdateMTSign(this)).getPacket();
    }

    public void handleUpdatePacket(PacketUpdateMTSign var1, World var2)
    {
        this.setMetaValue(var1.getItemDamage());
        this.setMtSignText(var1.getMtSignText());
        this.update();
        var2.notify(var1.xPosition, var1.yPosition, var1.zPosition);
    }
}
