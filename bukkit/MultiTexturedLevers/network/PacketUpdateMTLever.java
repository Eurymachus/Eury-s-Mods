package MultiTexturedLevers.network;

import EurysMods.network.PacketPayload;
import MultiTexturedLevers.TileEntityMTLever;

public class PacketUpdateMTLever extends PacketMTL
{
    public PacketUpdateMTLever()
    {
        super(3);
    }

    public PacketUpdateMTLever(TileEntityMTLever var1)
    {
        super(3);
        this.payload = var1.getPacketPayload();
        this.xPosition = var1.x;
        this.yPosition = var1.y;
        this.zPosition = var1.z;
        this.isChunkDataPacket = true;
    }

    public PacketUpdateMTLever(int var1, int var2, int var3, int var4)
    {
        super(3);
        this.payload = new PacketPayload(1, 0, 0);
        this.xPosition = var1;
        this.yPosition = var2;
        this.zPosition = var3;
        this.payload.intPayload[0] = var4;
        this.isChunkDataPacket = true;
    }

    public int getItemDamage()
    {
        return this.payload.intPayload[0];
    }
}
