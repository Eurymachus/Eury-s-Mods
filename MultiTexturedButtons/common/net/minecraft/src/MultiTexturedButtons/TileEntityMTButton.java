package net.minecraft.src.MultiTexturedButtons;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.MultiTexturedButtons.network.PacketUpdateMTButton;

public class TileEntityMTButton extends TileEntity {
	public int metaValue;

	public int getMetaValue() {
		return this.metaValue;
	}

	public void setMetaValue(int meta) {
		this.metaValue = meta;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("metaValue", this.metaValue);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.setMetaValue(nbttagcompound.getInteger("metaValue"));
	}

	public PacketPayload getPacketPayload() {
		PacketPayload p = new PacketPayload(1, 0, 0, 0);
		p.setIntPayload(0, this.metaValue);
		return p;
	}

	public Packet getDescriptionPacket() {
		return getUpdatePacket();
	}

	public Packet getUpdatePacket() {
		return new PacketUpdateMTButton(this).getPacket();
	}

	public void handleUpdatePacket(PacketUpdate packet, World world) {
		PacketUpdateMTButton bp = (PacketUpdateMTButton) packet;
		this.setMetaValue(bp.getItemDamage());
		this.onInventoryChanged();
		world.markBlockNeedsUpdate(packet.xPosition, packet.yPosition,
				packet.zPosition);
	}
}