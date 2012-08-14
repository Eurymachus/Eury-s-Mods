package net.minecraft.src.MultiTexturedLevers.network;

import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.MultiTexturedLevers.MultiTexturedLevers;
import net.minecraft.src.MultiTexturedLevers.TileEntityMTLever;

public class PacketMTL extends PacketTileEntityMT {
	public PacketMTL() {
		super(MultiTexturedLevers.Core.getModChannel());
	}

	public PacketMTL(TileEntityMTLever tileentitymtlever) {
		super(MultiTexturedLevers.Core.getModChannel(), tileentitymtlever);
	}
}
