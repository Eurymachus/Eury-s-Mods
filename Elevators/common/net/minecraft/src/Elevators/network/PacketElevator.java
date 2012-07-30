package net.minecraft.src.Elevators.network;

import net.minecraft.src.Elevators.Elevators;
import net.minecraft.src.EurysMods.network.PacketUpdate;

public class PacketElevator extends PacketUpdate {

	public PacketElevator(int packetId) {
		super(packetId);
		this.channel = Elevators.Core.getModChannel();
	}
}
