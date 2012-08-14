package net.minecraft.src.Elevators.network;

import net.minecraft.src.EurysMods.network.PacketIds;
import net.minecraft.src.EurysMods.network.PacketPayload;

public class PacketElevatorGui extends PacketElevator {
	public PacketElevatorGui() {
		super(PacketIds.GUI);
	}

	public PacketElevatorGui(String command, int[] intData,
			double[] doubleData, String[] stringData) {
		this();
		this.payload = new PacketPayload(intData.length, 0,
				stringData.length + 1, 0, doubleData.length);
		this.setCommand(command);
		this.setIntData(intData);
		this.setStringData(stringData);
		//this.setBoolData(boolData);
		this.setDoubleData(doubleData);
	}

	public PacketElevatorGui(String command) {
		this();
		this.payload = new PacketPayload(0, 0, 1, 0);
		this.setCommand(command);
	}

	public void setCommand(String command) {
		this.payload.setStringPayload(0, command);
	}

	public String getCommand() {
		return this.payload.getStringPayload(0);
	}

	public void setIntData(int[] intData) {
		if (intData != null && this.payload.getIntSize() > 0) {
			for (int i = 0; i < intData.length; i++) {
				this.payload.setIntPayload(i, intData[i]);
			}
		}
	}

	public void setDoubleData(double[] doubleData) {
		if (doubleData != null && this.payload.getDoubleSize() > 0) {
			for (int i = 0; i < doubleData.length; i++) {
				this.payload.setDoublePayload(i, doubleData[i]);
			}
		}
	}

	public void setBoolData(boolean[] boolData) {
		if (boolData != null && this.payload.getBoolSize() > 0) {
			for (int i = 0; i < boolData.length; i++) {
				this.payload.setBoolPayload(i, boolData[i]);
			}
		}
	}

	public void setStringData(String[] stringData) {
		if (stringData != null && this.payload.getStringSize() > 0) {
			int i = 1;
			for (int j = 0; j < stringData.length; j++) {
				this.payload.setStringPayload(i, stringData[j]);
				i++;
			}
		}
	}

	public String[] getDataString() {
		if (this.payload.getStringSize() > 1) {
			String[] dataString = new String[this.payload.getStringSize() - 1];
			int j = 0;
			for (int i = 1; i < this.payload.getStringSize(); i++) {
				dataString[j] = this.payload.getStringPayload(i);
				j++;
			}
			return dataString;
		}
		return null;
	}

	public boolean[] getBoolPayload() {
		if (this.payload.getBoolSize() > 1) {
			boolean[] dataBool = new boolean[3];
			for (int i = 0; i < this.payload.getBoolSize(); i++) {
				dataBool[i] = this.payload.getBoolPayload(i);
			}
			return dataBool;
		}
		return null;
	}
}
