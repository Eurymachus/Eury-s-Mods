package net.minecraft.src.MultiTexturedBeds;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.MultiTexturedBeds.network.PacketUpdateMTBed;
import net.minecraft.src.MultiTexturedDoors.network.PacketUpdateMTDoor;

public class TileEntityMTBed extends TileEntity {
	public int metaValue;
	public int bedPiece;

	public int getMetaValue() {
		return this.metaValue;
	}

	public void setMetaValue(int meta) {
		this.metaValue = meta;
	}

	public int getBedPiece() {
		return this.bedPiece;
	}

	public void setBedPiece(int piece) {
		this.bedPiece = piece;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("metaValue", this.getMetaValue());
		nbttagcompound.setInteger("doorPiece", this.getBedPiece());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.setMetaValue(nbttagcompound.getInteger("metaValue"));
		this.setBedPiece(nbttagcompound.getInteger("doorPiece"));
	}

	public PacketPayload getPacketPayload() {
		PacketPayload p = new PacketPayload(2, 0, 0, 0);
		p.setIntPayload(0, this.metaValue);
		p.setIntPayload(1, this.bedPiece);
		return p;
	}

	public Packet getDescriptionPacket() {
		return getUpdatePacket();
	}

	public Packet getUpdatePacket() {
		return new PacketUpdateMTBed(this).getPacket();
	}

	public void handleUpdatePacket(PacketUpdateMTBed packet, World world) {
		this.setMetaValue(packet.getItemDamage());
		this.setBedPiece(packet.getBedPiece());
		this.onInventoryChanged();
		world.markBlockNeedsUpdate(packet.xPosition, packet.yPosition,
				packet.zPosition);
	}
}
