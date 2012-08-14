package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.core.TileEntityMT;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.MultiTexturedSigns.network.PacketUpdateMTSign;

public class TileEntityMTSign extends TileEntityMT {
	public String mtSignText[] = { "", "", "", "" };
	public int mtsLineBeingEdited;
	private boolean mtsIsEditable;

	public boolean getIsEditAble() {
		return mtsIsEditable;
	}

	public String[] getMtSignText() {
		return this.mtSignText;
	}

	public void setMtSignText(String[] newSignText) {
		for (int i = 0; i < 4; i++) {
			this.mtSignText[i] = newSignText[i];
		}
	}

	public TileEntityMTSign() {
		mtsLineBeingEdited = -1;
		mtsIsEditable = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setString("mtsText1", mtSignText[0]);
		nbttagcompound.setString("mtsText2", mtSignText[1]);
		nbttagcompound.setString("mtsText3", mtSignText[2]);
		nbttagcompound.setString("mtsText4", mtSignText[3]);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		mtsIsEditable = true;
		super.readFromNBT(nbttagcompound);
		for (int i = 0; i < 4; i++) {
			mtSignText[i] = nbttagcompound.getString((new StringBuilder())
					.append("mtsText").append(i + 1).toString());
			if (mtSignText[i].length() > 15) {
				mtSignText[i] = mtSignText[i].substring(0, 15);
			}
		}
	}

	public PacketPayload getPacketPayload() {
		PacketPayload p = new PacketPayload(0, 0, 4, 0);
		for (int i = 0; i < this.mtSignText.length; i++) {
			p.setStringPayload(i, this.mtSignText[i]);
		}
		return p;
	}

	@Override
	public Packet getUpdatePacket() {
		return new PacketUpdateMTSign(this).getPacket();
	}

	@Override
	public void handleUpdatePacket(World world, PacketTileEntityMT packet) {
		this.setMtSignText(((PacketUpdateMTSign) packet).getMtSignText());
		super.handleUpdatePacket(world, packet);
	}
}