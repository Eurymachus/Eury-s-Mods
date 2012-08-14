package net.minecraft.src.EurysMods.core;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.PacketTileEntityMT;

public abstract class TileEntityMT extends TileEntity {

	private int textureValue;

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("textureValue", textureValue);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		textureValue = nbttagcompound.getInteger("textureValue");
	}

	public Packet getDescriptionPacket() {
		return getUpdatePacket();
	}

	public void setTextureValue(int texture) {
		this.textureValue = texture;
	}

	public int getTextureValue() {
		return this.textureValue;
	}

	public abstract Packet getUpdatePacket();

	public void handleUpdatePacket(World world, PacketTileEntityMT packet) {
		this.setTextureValue(packet.getTextureValue());
		this.onInventoryChanged();
		world.markBlockNeedsUpdate(packet.xPosition, packet.yPosition,
				packet.zPosition);
	}
}
