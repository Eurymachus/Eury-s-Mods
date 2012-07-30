package net.minecraft.src.Elevators.network;

import net.minecraft.src.TileEntity;
import net.minecraft.src.Elevators.TileEntityElevator;

public class PacketUpdateElevator extends PacketElevator {
	public PacketUpdateElevator() {
		super(0);
	}

	public PacketUpdateElevator(TileEntityElevator tileentityelevator) {
		this();
		this.payload = tileentityelevator.getPacketPayload();
		TileEntity entity = tileentityelevator;
		this.xPosition = entity.xCoord;
		this.yPosition = entity.yCoord;
		this.zPosition = entity.zCoord;
		this.isChunkDataPacket = true;
	}
}
