package MultiTexturedButtons.network;

import EurysMods.network.PacketPayload;
import MultiTexturedButtons.TileEntityMTButton;

public class PacketUpdateMTButton extends PacketMTB
{
    public PacketUpdateMTButton()
    {
        super(2);
    }

    public PacketUpdateMTButton(TileEntityMTButton var1)
    {
        super(2);
        this.payload = var1.getPacketPayload();
        this.xPosition = var1.x;
        this.yPosition = var1.y;
        this.zPosition = var1.z;
        this.isChunkDataPacket = true;
    }

    public PacketUpdateMTButton(int var1, int var2, int var3, int var4)
    {
        super(2);
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
