package net.minecraft.src.MultiTexturedSigns.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedSigns.TileEntityMTSign;

public class PacketUpdateMTSign extends PacketMTS
{
    public String[] signLines;
    
    public PacketUpdateMTSign()
    {
    	super(PacketIds.MTSIGN_UPDATE);
    }
    
    public PacketUpdateMTSign(TileEntityMTSign tileentitymtsign)
    {
		super(PacketIds.MTSIGN_UPDATE);
	
		this.payload = tileentitymtsign.getPacketPayload();
		TileEntity entity = (TileEntity)tileentitymtsign;
		this.xPosition = entity.xCoord;
		this.yPosition = entity.yCoord;
		this.zPosition = entity.zCoord;
		this.isChunkDataPacket = true;
	}
    
    public PacketUpdateMTSign(int x, int y, int z, int metaValue, String[] signText)
    {
       	super(PacketIds.MTSIGN_UPDATE);

       	this.payload = new PacketPayload(1,0,4);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.payload.intPayload[0] = metaValue;
		this.payload.stringPayload = signText;
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
    
    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
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
