package net.minecraft.src.MultiTexturedBeds.network;

import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.MultiTexturedBeds.TileEntityMTBed;

public class PacketUpdateMTBed extends PacketMTBed {

	public PacketUpdateMTBed() {
		super(PacketIds.MTBED_UPDATE);
	}

	public PacketUpdateMTBed(TileEntityMTBed tileentitymtbed) {
		super(PacketIds.MTBED_UPDATE);

		this.payload = tileentitymtbed.getPacketPayload();
		TileEntity entity = tileentitymtbed;
		this.xPosition = entity.xCoord;
		this.yPosition = entity.yCoord;
		this.zPosition = entity.zCoord;
		this.isChunkDataPacket = true;
	}

	public void setItemDamage(int itemDamage) {
		this.payload.setIntPayload(0, itemDamage);
	}

	public int getItemDamage() {
		return this.payload.getIntPayload(0);
	}

	public void setBedPiece(int bedPiece) {
		this.payload.setIntPayload(1, bedPiece);
	}

	public int getBedPiece() {
		return this.payload.getIntPayload(1);
	}
}
