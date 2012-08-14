package net.minecraft.src.Elevators.network;

import net.minecraft.src.World;
import net.minecraft.src.Elevators.Elevators;
import net.minecraft.src.EurysMods.network.PacketUpdate;

public class PacketElevator extends PacketUpdate {
	
	PacketElevator(int packetId) {
		super(packetId);
		this.channel = Elevators.Core.getModChannel();
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}

}
