package net.minecraft.src.MultiTexturedPPlates.network;

import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedPPlates.MultiTexturedPPlates;

public class PacketMTP extends PacketUpdate {
	public PacketMTP(int packetId) {
		super(packetId);
		this.channel = MultiTexturedPPlates.Core.getModChannel();
	}
}
