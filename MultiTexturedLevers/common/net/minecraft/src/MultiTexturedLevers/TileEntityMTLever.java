package net.minecraft.src.MultiTexturedLevers;

import net.minecraft.src.Packet;
import net.minecraft.src.EurysMods.core.TileEntityMT;
import net.minecraft.src.MultiTexturedLevers.network.PacketMTL;

public class TileEntityMTLever extends TileEntityMT {

	@Override
	public Packet getUpdatePacket() {
		return new PacketMTL(this).getPacket();
	}
}
