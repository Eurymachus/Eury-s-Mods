package net.minecraft.src.MultiTexturedPPlates;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.core.TileEntityMT;
import net.minecraft.src.EurysMods.network.PacketTileEntityMT;
import net.minecraft.src.MultiTexturedPPlates.network.PacketUpdateMTPPlate;

public class TileEntityMTPPlate extends TileEntityMT {
	public int triggerType;

	public int getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(int trigger) {
		this.triggerType = trigger;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("triggerType", this.getTriggerType());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.setTriggerType(nbttagcompound.getInteger("triggerType"));
	}

	@Override
	public Packet getUpdatePacket() {
		return new PacketUpdateMTPPlate(this).getPacket();
	}

	@Override
	public void handleUpdatePacket(World world, PacketTileEntityMT packet) {
		this.setTriggerType(((PacketUpdateMTPPlate) packet).getTriggerType());
		super.handleUpdatePacket(world, packet);
	}
}
