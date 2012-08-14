package net.minecraft.src.MultiTexturedSigns.network;

import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.MultiTexturedSigns.MultiTexturedSigns;
import net.minecraft.src.MultiTexturedSigns.TileEntityMTSign;

public class PacketUpdateMTSign extends PacketTileEntityMT {
	private String[] signLines;

	public PacketUpdateMTSign() {
		super(MultiTexturedSigns.MTS.getModChannel());
	}

	public PacketUpdateMTSign(TileEntityMTSign tileentitymtsign) {
		super(MultiTexturedSigns.MTS.getModChannel(), tileentitymtsign);
		this.payload = tileentitymtsign.getPacketPayload();
	}

	public void setMtSignText(String[] signText) {
		for (int i = 0; i < signText.length; i++) {
			this.payload.setStringPayload(i, signText[i]);
			;
		}
	}

	public String[] getMtSignText() {
		String[] signText = new String[4];
		for (int i = 0; i < 4; i++) {
			signText[i] = this.payload.getStringPayload(i);
		}
		return signText;
	}
}
