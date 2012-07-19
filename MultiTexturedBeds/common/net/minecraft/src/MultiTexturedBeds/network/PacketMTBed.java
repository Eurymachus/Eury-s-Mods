package net.minecraft.src.MultiTexturedBeds.network;

import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedSigns.MultiTexturedSigns;

public class PacketMTBed extends PacketUpdate {
	public PacketMTBed(int packetId) {
		super(packetId);
		this.channel = MultiTexturedSigns.MTS.getModChannel();
	}
}
