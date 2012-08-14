package net.minecraft.src.MultiTexturedButtons;

import net.minecraft.src.Packet;
import net.minecraft.src.EurysMods.core.TileEntityMT;
import net.minecraft.src.MultiTexturedButtons.network.PacketUpdateMTButton;

public class TileEntityMTButton extends TileEntityMT {

	@Override
	public Packet getUpdatePacket() {
		return new PacketUpdateMTButton(this).getPacket();
	}
}