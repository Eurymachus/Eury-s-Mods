package MultiTexturedPPlates.network;

import EurysMods.network.PacketPayload;
import MultiTexturedPPlates.TileEntityMTPPlate;

public class PacketUpdateMTPPlate extends PacketMTP
{
    public PacketUpdateMTPPlate()
    {
        super(4);
    }

    public PacketUpdateMTPPlate(TileEntityMTPPlate var1)
    {
        super(4);
        this.payload = var1.getPacketPayload();
        this.xPosition = var1.x;
        this.yPosition = var1.y;
        this.zPosition = var1.z;
        this.isChunkDataPacket = true;
    }

    public PacketUpdateMTPPlate(int var1, int var2, int var3, int var4, int var5)
    {
        super(4);
        this.payload = new PacketPayload(2, 0, 0);
        this.xPosition = var1;
        this.yPosition = var2;
        this.zPosition = var3;
        this.payload.intPayload[0] = var4;
        this.payload.intPayload[1] = var5;
        this.isChunkDataPacket = true;
    }

    public int getItemDamage()
    {
        return this.payload.intPayload[0];
    }

    public int getTriggerType()
    {
        return this.payload.intPayload[1];
    }
}
