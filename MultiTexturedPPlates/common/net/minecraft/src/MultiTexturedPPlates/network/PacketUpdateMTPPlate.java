package net.minecraft.src.MultiTexturedPPlates.network;

import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.MultiTexturedPPlates.MultiTexturedPPlates;
import net.minecraft.src.MultiTexturedPPlates.TileEntityMTPPlate;

public class PacketUpdateMTPPlate extends PacketTileEntityMT {
	public PacketUpdateMTPPlate() {
		super(MultiTexturedPPlates.Core.getModChannel());
	}

	public PacketUpdateMTPPlate(TileEntityMTPPlate tileentitymtpplate) {
		super(MultiTexturedPPlates.Core.getModChannel(), tileentitymtpplate);
		this.payload = new PacketPayload(1, 0, 0, 0);
		this.setTriggerType(tileentitymtpplate.getTriggerType());
	}

	public void setTriggerType(int triggerType) {
		this.payload.setIntPayload(0, triggerType);
	}

	public int getTriggerType() {
		return this.payload.getIntPayload(0);
	}
}
