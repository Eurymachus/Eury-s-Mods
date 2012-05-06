package MultiTexturedSigns.network;

import EurysMods.network.PacketPayload;
import MultiTexturedSigns.TileEntityMTSign;

public class PacketUpdateMTSign extends PacketMTS
{
    public String[] signLines;

    public PacketUpdateMTSign()
    {
        super(0);
    }

    public PacketUpdateMTSign(TileEntityMTSign var1)
    {
        super(0);
        this.payload = var1.getPacketPayload();
        this.xPosition = var1.x;
        this.yPosition = var1.y;
        this.zPosition = var1.z;
        this.isChunkDataPacket = true;
    }

    public PacketUpdateMTSign(int var1, int var2, int var3, int var4, String[] var5)
    {
        super(0);
        this.payload = new PacketPayload(1, 0, 4);
        this.xPosition = var1;
        this.yPosition = var2;
        this.zPosition = var3;
        this.payload.intPayload[0] = var4;
        this.payload.stringPayload = var5;
        this.isChunkDataPacket = true;
    }

    public int getItemDamage()
    {
        return this.payload.intPayload[0];
    }

    public String[] getMtSignText()
    {
        return this.payload.stringPayload;
    }

    public int getPacketSize()
    {
        int var1 = 0;

        for (int var2 = 0; var2 < 4; ++var2)
        {
            var1 += this.signLines[var2].length();
        }

        return var1;
    }
}
