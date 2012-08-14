package net.minecraft.src.MultiTexturedBeds.network;

import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.MultiTexturedBeds.MultiTexturedBeds;
import net.minecraft.src.MultiTexturedBeds.TileEntityMTBed;

public class PacketUpdateMTBed extends PacketTileEntityMT {

	public PacketUpdateMTBed() {
		super(MultiTexturedBeds.MTBed.getModChannel());
	}

	public PacketUpdateMTBed(TileEntityMTBed tileentitymtbed) {
		super(MultiTexturedBeds.MTBed.getModChannel(), tileentitymtbed);
		this.payload = tileentitymtbed.getPacketPayload();
	}

	public void setBedPiece(int bedPiece) {
		this.payload.setIntPayload(0, bedPiece);
	}

	public int getBedPiece() {
		return this.payload.getIntPayload(0);
	}
}
