package net.minecraft.src.MultiTexturedSigns.network;

import net.minecraft.src.EurysMods.network.PacketIds;

public class PacketOpenGui extends PacketMTS {
	public PacketOpenGui() {
		super(PacketIds.MTSIGN_GUI);
	}

	public PacketOpenGui(int x, int y, int z) {
		super(PacketIds.MTSIGN_GUI);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
	}
}
