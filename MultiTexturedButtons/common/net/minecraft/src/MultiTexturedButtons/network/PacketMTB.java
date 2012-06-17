package net.minecraft.src.MultiTexturedButtons.network;

import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedButtons.MultiTexturedButtons;

public abstract class PacketMTB extends PacketUpdate {
	public PacketMTB(int packetId) {
		super(packetId);
		this.channel = MultiTexturedButtons.Core.getModChannel();
	}
}
