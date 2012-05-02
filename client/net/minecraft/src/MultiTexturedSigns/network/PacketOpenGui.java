package net.minecraft.src.MultiTexturedSigns.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedSigns.TileEntityMTSign;

public class PacketOpenGui extends PacketMTS
{
    public PacketOpenGui()
    {
    	super(PacketIds.MTSIGN_GUI);
    }
    
    public PacketOpenGui(int x, int y, int z)
    {
    	super(PacketIds.MTSIGN_GUI);
    	this.xPosition = x;
    	this.yPosition = y;
    	this.zPosition = z;
    }

	@Override
	public int getID()
	{
		return PacketIds.MTSIGN_GUI;
	}
}
