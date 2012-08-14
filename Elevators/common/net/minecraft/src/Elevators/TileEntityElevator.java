package net.minecraft.src.Elevators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.src.ChunkPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.Elevators.network.PacketElevator;
import net.minecraft.src.EurysMods.network.PacketPayload;
import net.minecraft.src.EurysMods.network.PacketTileEntity;

public class TileEntityElevator extends TileEntity {
	private Map properties = new HashMap();
	private List floors = new ArrayList();
	private static final int FLOOR_ONE = 0;
	private static final int COLOR_DATA = 1;
	private static final int TITLE = 2;
	public static final int NO_ACTION = 0;
	public static final int REQUEST_NEW_FLOOR = 1;
	public static final int DEMAND_NEW_FLOOR = 2;
	public static final int POWER_ON = 3;
	public static final int NO_FLOOR = 0;
	private boolean providesPower = false;
	private boolean canProvidePower = true;
	private boolean canBeHalted = true;
	private boolean enableMobilePower = false;
	private int destination_Y;
	private int state;

	public TileEntityElevator() {
		this.properties.put(Integer.valueOf(0), "0");
		this.properties.put(Integer.valueOf(1), "0");
		this.properties.put(Integer.valueOf(2), "");
		this.state = 0;
		this.destination_Y = 0;
	}

	public void setFloors(Collection var1) {
		this.floors.clear();
		this.floors.addAll(var1);
		Collections.sort(this.floors);
		this.onInventoryChanged();
	}

	public int getDestination() {
		return this.destination_Y;
	}

	public void setPower(boolean var1) {
		if (!this.canProvidePower) {
			this.providesPower = false;
		} else {
			this.providesPower = var1;
			this.onInventoryChanged();
		}
	}

	public boolean getCanProvidePower() {
		return this.canProvidePower;
	}

	public boolean getCanHalt() {
		return this.canBeHalted;
	}

	public boolean getProvidesPower() {
		return this.providesPower;
	}

	public boolean getMobilePower() {
		return this.enableMobilePower;
	}

	public int getCurrentState() {
		return this.state;
	}

	public void clearState() {
		this.state = 0;
		this.onInventoryChanged();
	}

	public void setFirstRefresh() {
		this.state = 3;
		this.onInventoryChanged();
	}

	public boolean reset() {
		this.state = 0;

		if (!this.hasNoFloors() && this.betweenFloors()) {
			this.requestFloor(this.getClosestFloor(this.yCoord));
			this.state = 1;
			this.onInventoryChanged();
			return true;
		} else {
			this.onInventoryChanged();
			return false;
		}
	}

	public boolean demandY(int var1) {
		if (var1 > 0 && var1 < ElevatorsCore.max_elevator_Y) {
			this.destination_Y = var1;
			this.state = 2;
			this.onInventoryChanged();
			return true;
		} else {
			return false;
		}
	}

	public boolean requestFloor(int var1) {
		int var2 = this.getYFromFloor(var1);

		if (this.hasFloorAt(var2)) {
			this.destination_Y = var2;
			this.state = 1;
			this.onInventoryChanged();
			return true;
		} else {
			return false;
		}
	}

	public boolean hasFloorAt(int var1) {
		return this.hasNoFloors() ? false : (var1 > 0
				&& var1 < ElevatorsCore.max_elevator_Y ? this.floors
				.contains(Integer.valueOf(var1)) : false);
	}

	public boolean betweenFloors() {
		return this.hasNoFloors()
				|| !this.floors.contains(Integer.valueOf(this.yCoord));
	}

	public boolean hasNoFloors() {
		return this.floors == null || this.floors.isEmpty();
	}

	public int curFloor() {
		return this.betweenFloors() ? 0 : this
				.getClosestFloorFromYCoor_AlwaysDown(this.yCoord);
	}

	public int floorsBelow() {
		return this.hasNoFloors() ? 0 : (this.betweenFloors() ? this
				.getClosestFloorFromYCoor_AlwaysDown(this.yCoord) : this
				.curFloor() - 1);
	}

	public int floorsAbove() {
		return this.hasNoFloors() ? 0 : (this.betweenFloors() ? this.floors
				.size() - this.floorsBelow() : this.floors.size()
				- this.curFloor());
	}

	public int numFloors() {
		return this.hasNoFloors() ? 0 : this.floors.size();
	}

	public int getFloorFromY(int var1) {
		if (this.hasNoFloors()) {
			return 0;
		} else {
			if (this.floors.contains(Integer.valueOf(var1))) {
				Iterator var2 = this.floors.iterator();

				for (int var3 = 0; var3 < this.floors.size(); ++var3) {
					if (((Integer) this.floors.get(var3)).intValue() == var1) {
						return var3;
					}
				}
			}

			return 0;
		}
	}

	public int getYFromFloor(int var1) {
		return this.hasNoFloors() ? 0
				: (var1 > 0 && var1 <= this.floors.size() ? ((Integer) this.floors
						.get(var1 - 1)).intValue() : 0);
	}

	public int getClosestFloor(int var1) {
		if (this.hasNoFloors()) {
			return 0;
		} else {
			int var2 = ElevatorsCore.max_elevator_Y + 1;
			int var3 = 0;

			for (int var4 = 0; var4 < this.floors.size(); ++var4) {
				int var5 = ((Integer) this.floors.get(var4)).intValue();
				int var6 = var5 - var1;

				if (var6 < 0) {
					var6 *= -1;
				}

				if (var6 == 0) {
					return var4 + 1;
				}

				if (var6 < var2) {
					var3 = var4 + 1;
					var2 = var6;
				}
			}

			return var3;
		}
	}

	public int getClosestYFromYCoor(int var1) {
		int var2 = this.getClosestFloor(var1);
		return this.getYFromFloor(var2);
	}

	public int getClosestFloorFromYCoor_AlwaysDown(int var1) {
		if (this.hasNoFloors()) {
			return 0;
		} else {
			int var2 = 0;
			Iterator var3 = this.floors.iterator();

			while (var3.hasNext()) {
				int var4 = ((Integer) var3.next()).intValue();

				if (var4 <= var1) {
					++var2;
				}
			}

			return var2;
		}
	}

	public int getFloorOneYValue() {
		int var1 = 0;

		if (this.properties.containsKey(Integer.valueOf(0))) {
			try {
				var1 = Integer.parseInt((String) this.properties.get(Integer
						.valueOf(0)));
			} catch (Exception var3) {
				var1 = 0;
				ElevatorsCore.say("Given y coordinate was "
						+ (String) this.properties.get(Integer.valueOf(0))
						+ ". Unable to parse integer.");
			}
		}

		return var1;
	}

	public String getFloorName(int var1) {
		return (String) this.properties.get(Integer.valueOf(var1 + 2));
	}

	public Map getAllProperties() {
		return this.properties;
	}

	public void setFloorNames(Map var1) {
		if (var1 != null) {
			this.properties.putAll(var1);
			this.onInventoryChanged();
		}
	}

	public boolean checkoutData() {
		ChunkPosition var1 = new ChunkPosition(this.xCoord, this.yCoord,
				this.zCoord);

		if (ElevatorsCore.checkedFloorNames.containsKey(var1)) {
			ElevatorsCore
					.say("Found data for elevator - getting data and deleting entry");
			this.convertArrayToFloorMap((String[]) ElevatorsCore.checkedFloorNames
					.get(var1));
			ElevatorsCore.checkedFloorNames.remove(var1);
		}

		if (ElevatorsCore.checkedProperties.containsKey(var1)) {
			ElevatorsCore.say("Found properties for elevator");
			this.convertArrayToBooleanProperties((boolean[]) ElevatorsCore.checkedProperties
					.get(var1));
			ElevatorsCore.checkedProperties.remove(var1);
			return true;
		} else {
			return false;
		}
	}

	public void convertArrayToBooleanProperties(boolean[] var1) {
		try {
			this.canProvidePower = var1[0];
			this.canBeHalted = var1[1];
			this.enableMobilePower = var1[2];
		} catch (Exception var3) {
			;
		}

		this.providesPower = this.canProvidePower;
	}

	public void convertArrayToFloorMap(String[] var1) {
		if (var1 != null && var1.length >= 1) {
			ElevatorsCore.say("Converting array to properties");
			int var2 = 1;

			try {
				var2 = Integer.parseInt(var1[0]);
			} catch (Exception var7) {
				ElevatorsCore.say("Unable to parse integer.");
			} finally {
				ElevatorsCore.say("Using floor " + var2 + " as the floor one");
			}

			if (var2 > this.numFloors()) {
				var2 = ElevatorsCore.max_elevator_Y;
			} else if (var2 < 1) {
				var2 = 0;
			} else {
				var2 = this.getYFromFloor(var2);
			}

			var1[0] = String.valueOf(var2);
			ElevatorsCore
					.say("Given \'floor one\' y coordinate is: " + var1[0]);

			for (int var3 = 0; var3 < var1.length; ++var3) {
				if (var3 < 3) {
					this.properties.put(Integer.valueOf(var3), var1[var3]);
				} else if (this.getYFromFloor(var3) != 0) {
					this.properties.put(
							Integer.valueOf(this.getYFromFloor(var3)),
							var1[var3]);
				}
			}
		}
	}

	public boolean[] convertBooleanPropertiesToArray() {
		boolean[] var1 = new boolean[] { this.canProvidePower,
				this.canBeHalted, this.enableMobilePower };
		return var1;
	}

	public String[] convertFloorMapToArray() {
		int var1 = this.numFloors();
		String[] var2 = new String[var1 + 3];
		int var3 = this.getFloorOneYValue();
		ElevatorsCore.say("floor one is " + var3);

		if (var3 < 1) {
			var2[0] = "1";
		} else if (var3 < ElevatorsCore.max_elevator_Y
				&& this.getClosestFloorFromYCoor_AlwaysDown(var3) > 0) {
			var2[0] = String.valueOf(this
					.getClosestFloorFromYCoor_AlwaysDown(var3));
		} else {
			var2[0] = String.valueOf(var1 + 1);
		}

		if (!this.properties.containsKey(Integer.valueOf(1))) {
			var2[1] = "0";
		} else {
			var2[1] = (String) this.properties.get(Integer.valueOf(1));
		}

		ElevatorsCore.say("Color data: " + var2[1]);

		if (!this.properties.containsKey(Integer.valueOf(2))) {
			var2[2] = "";
		} else {
			var2[2] = (String) this.properties.get(Integer.valueOf(2));
		}

		ElevatorsCore.say("Elevator name: " + var2[2]);

		for (int var4 = 1; var4 <= var1; ++var4) {
			int var5 = this.getYFromFloor(var4);

			if (var5 > 0 && var5 < ElevatorsCore.max_elevator_Y
					&& this.properties.containsKey(Integer.valueOf(var5))) {
				var2[var4 + 2] = (String) this.properties.get(Integer
						.valueOf(var5));
			} else {
				var2[var4 + 2] = "";
			}

			ElevatorsCore.say("Floor " + var4 + ": " + var2[var4 + 2]);
		}

		return var2;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound var1) {
		super.writeToNBT(var1);
		Iterator var2 = this.properties.keySet().iterator();

		while (var2.hasNext()) {
			int var3 = ((Integer) var2.next()).intValue();

			if (this.properties.get(Integer.valueOf(var3)) != null) {
				var1.setString("x" + String.valueOf(var3),
						(String) this.properties.get(Integer.valueOf(var3)));
			}
		}

		var1.setBoolean("provides", this.providesPower);
		var1.setBoolean("canProvide", this.canProvidePower);
		var1.setBoolean("canHalt", this.canBeHalted);
		var1.setBoolean("mobilePower", this.enableMobilePower);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound var1) {
		super.readFromNBT(var1);

		for (int var2 = 0; var2 < ElevatorsCore.max_elevator_Y + 2; ++var2) {
			String var3 = "";

			try {
				var3 = var1.getString("x" + String.valueOf(var2));
			} finally {
				;
			}

			if (var3 != null && !var3.equals("")) {
				this.properties.put(Integer.valueOf(var2), var3);
			}
		}

		this.providesPower = var1.getBoolean("provides");
		this.canProvidePower = var1.getBoolean("canProvide");
		this.canBeHalted = var1.getBoolean("canHalt");
		this.enableMobilePower = var1.getBoolean("mobilePower");
	}

	public void handleUpdatePacket(World world, PacketTileEntity packet) {
	}

	public PacketPayload getPacketPayload() {
		PacketPayload p = new PacketPayload(1, 1, 1, 1);
		return p;
	}
}
