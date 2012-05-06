package MultiTexturedDoors.network;

import EurysMods.network.PacketPayload;
import MultiTexturedDoors.TileEntityMTDoor;

public class PacketUpdateMTDoor extends PacketMTD
{
    public PacketUpdateMTDoor()
    {
        super(5);
    }

    public PacketUpdateMTDoor(TileEntityMTDoor var1)
    {
        super(5);
        this.payload = var1.getPacketPayload();
        this.xPosition = var1.x;
        this.yPosition = var1.y;
        this.zPosition = var1.z;
        this.isChunkDataPacket = true;
    }

    public PacketUpdateMTDoor(int var1, int var2, int var3, int var4, int var5)
    {
        super(5);
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

    public int getDoorPiece()
    {
        return this.payload.intPayload[1];
    }
}
