package net.minecraft.src.MultiTexturedBeds;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.core.TileEntityMT;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.MultiTexturedBeds.network.PacketUpdateMTBed;

public class TileEntityMTBed extends TileEntityMT {
	public int bedPiece;

	public int getBedPiece() {
		return this.bedPiece;
	}

	public void setBedPiece(int piece) {
		this.bedPiece = piece;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("bedPiece", this.getBedPiece());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.setBedPiece(nbttagcompound.getInteger("bedPiece"));
	}

	public PacketPayload getPacketPayload() {
		PacketPayload p = new PacketPayload(1, 0, 0, 0);
		p.setIntPayload(0, this.getBedPiece());
		return p;
	}

	@Override
	public Packet getUpdatePacket() {
		return new PacketUpdateMTBed(this).getPacket();
	}

	public void handleUpdatePacket(World world, PacketTileEntityMT packet) {
		this.setBedPiece(((PacketUpdateMTBed) packet).getBedPiece());
		super.handleUpdatePacket(world, packet);
	}
}
