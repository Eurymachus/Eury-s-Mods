package net.minecraft.src.MultiTexturedSigns.network;

import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedSigns.MultiTexturedSigns;

public class PacketOpenGui extends PacketUpdate {
	public PacketOpenGui() {
		super(PacketIds.GUI);
		this.channel = MultiTexturedSigns.MTS.getModChannel();
	}

	public PacketOpenGui(int x, int y, int z) {
		this();
		this.setPosition(x, y, z);
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}
}
