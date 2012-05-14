package net.minecraft.src.MultiTexturedSigns.network;

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

       	this.payload = new PacketPayload(1,0,4,0);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.isChunkDataPacket = true;
    }
    
	public void setItemDamage(int itemDamage)
	{
		this.payload.setIntPayload(0, itemDamage);
	}
	
	public void setMtSignText(String[] signText)
	{
		for (int i = 0; i < signText.length; i++)
		{
			this.payload.setStringPayload(i, signText[i]);;
		}
	}
    
	public int getItemDamage()
	{
		return this.payload.getIntPayload(0);
	}
	
	public String[] getMtSignText()
	{
		String[] signText = new String[4];
		for (int i = 0; i < 4; i++)
		{
			signText[i] = this.payload.getStringPayload(i);
		}
		return signText;
	}
}
