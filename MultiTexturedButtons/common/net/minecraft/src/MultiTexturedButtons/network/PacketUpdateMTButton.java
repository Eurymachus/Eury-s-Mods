package net.minecraft.src.MultiTexturedButtons.network;

import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.MultiTexturedButtons.MultiTexturedButtons;
import net.minecraft.src.MultiTexturedButtons.TileEntityMTButton;

public class PacketUpdateMTButton extends PacketTileEntityMT {
	public PacketUpdateMTButton() {
		super(MultiTexturedButtons.Core.getModChannel());
	}

	public PacketUpdateMTButton(TileEntityMTButton tileentitymtbutton) {
		super(MultiTexturedButtons.Core.getModChannel(), tileentitymtbutton);
	}
}
