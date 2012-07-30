package net.minecraft.src.PaintingChooser.network;

import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.PaintingChooser.PaintingChooser;

public class PacketPainting extends PacketUpdate {
	public PacketPainting(int packetId) {
		super(packetId);
		this.channel = PaintingChooser.PChooser.getModChannel();
	}
}
