package net.minecraft.src.Elevators.network;

import net.minecraft.src.EurysMods.network.PacketPayload;

public class PacketUpdateRiders extends PacketElevator {

	public PacketUpdateRiders() {
		super(2);
	}

	public PacketUpdateRiders(String command, int[] entityIDs,
			float[] entityYCoords) {
		this();
		this.payload = new PacketPayload(entityIDs.length,
				entityYCoords.length, 1, 0);
		this.setCommand(command);
		this.setEntityIDs(entityIDs);
		this.setEntityYCoords(entityYCoords);
	}

	private void setEntityYCoords(float[] entityYCoords) {
		if (this.payload.getFloatSize() > 0) {
			for (int i = 0; i < entityYCoords.length; i++) {
				this.payload.setFloatPayload(i, entityYCoords[i]);
			}
		}
	}

	private void setEntityIDs(int[] entityIDs) {
		if (this.payload.getIntSize() > 0) {
			for (int i = 0; i < entityIDs.length; i++) {
				this.payload.setIntPayload(i, entityIDs[i]);
			}
		}
	}

	public PacketUpdateRiders(String command, int[] entityIDs,
			float[] entityYCoords, double[] entitysData) {
		this();
		this.payload = new PacketPayload(entityIDs.length,
				entityYCoords.length, 1, 0, entitysData.length);
		this.setCommand(command);
		this.setEntityIDs(entityIDs);
		this.setEntityYCoords(entityYCoords);
		this.setEntitysData(entitysData);
	}

	private void setEntitysData(double[] entitysData) {
		if (this.payload.getDoubleSize() > 0) {
			for (int i = 0; i < entitysData.length; i++) {
				this.payload.setDoublePayload(i, entitysData[i]);
			}
		}
	}

	public void setCommand(String command) {
		this.payload.setStringPayload(0, command);
	}

	public String getCommand() {
		return this.payload.getStringPayload(0);
	}
}
