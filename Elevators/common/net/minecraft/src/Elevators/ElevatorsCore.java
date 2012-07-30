package net.minecraft.src.Elevators;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_Elevator;
import net.minecraft.src.Elevators.network.NetworkConnection;
import net.minecraft.src.Elevators.network.PacketElevatorGui;
import net.minecraft.src.EurysMods.network.PacketUpdate;
import net.minecraft.src.forge.DimensionManager;
import net.minecraft.src.forge.MinecraftForge;

public class ElevatorsCore {

	public static String version = "1.6.0";
	private static Props props = new Props((new File(Elevators.minecraftDir
			+ "config/" + "mod_Elevator.cfg")).getPath());
	public static HashMap elevatorRequests = new HashMap();
	static int OFFSET = 256;
	static byte NOUPDATE = -1;
	public static String[] messages;
	public static String floorName;
	public static String basementName;
	public static boolean verbose;
	public static boolean killBelow;
	public static boolean invertKeys;
	public static boolean strictShaft;
	public static int elevator_entityID;
	public static int max_elevator_Y;
	public static int guiElevatorID;
	public static int topTexture;
	public static int sideTexture;
	public static Block ElevatorButton;
	public static Block Elevator;
	public static Block ElevatorCaller;
	public static Block Transient;
	public static Map checkedFloorNames;
	public static Map checkedProperties;
	public static Set disallowed_renderTypes;
	public static Set allowed_blockIDs;
	public static Set disallowed_blockIDs;
	public static Set solid_allowed_blockIDs;
	public static Set solid_disallowed_blockIDs;
	// public static final int GUI_DATA = 237;
	public static final String GUI_DATA = "GUI_DATA";
	public static final int UPDATE_RIDERS = 238;
	public static final int DISMOUNT_RIDERS = 239;
	public static final int GUI_COMMUNICATION_ERROR = 300;
	public static final int GUI_OPTIONS = 198;
	public static final int GUI_RESET = 199;
	public static final int GUI_CANCEL = 200;
	public static final int GUI_OPTIONS_CANCEL = 201;
	public static final int GUI_OPTIONS_SLIDER = 202;
	public static final int GUI_OPTIONS_NAMESLIST = 203;
	public static final int GUI_OPTIONS_FLOORNAME = 204;
	public static final int GUI_OPTIONS_ELEVATORNAME = 205;
	public static final int GUI_OPTIONS_APPLY = 206;
	public static final int GUI_OPTIONS_POWER = 207;
	public static final int GUI_OPTIONS_HALT = 208;
	public static final int GUI_OPTIONS_MOBILE = 209;
	public static List newBlocks = new ArrayList();
	public static boolean enableAdding = true;

	public static void initialize() {
		Elevators.initialize();
		ModLoader.registerEntityID(EntityElevator.class, "ironclad_elevator",
				elevator_entityID);
		MinecraftForge.registerConnectionHandler(new NetworkConnection());
		// ModLoaderMp.registerEntityTrackerEntry(EntityElevator.class,
		// elevator_entityID);
		// ModLoaderMp.registerEntityTracker(EntityElevator.class, 160, 1);
	}

	public static void addItems() {
		configuration();
		ElevatorButton = (new BlockElevatorButton(
				props.getInt("ElevatorButton_blockID"),
				Block.blockSteel.blockIndexInTexture))
				.setBlockName("elevatorbutton");
		Elevator = (new BlockElevator(props.getInt("Elevator_blockID")))
				.setBlockName("elevator");
		ElevatorCaller = (new BlockElevatorCaller(
				props.getInt("ElevatorCaller_blockID"), Material.ground))
				.setBlockName("elevatorcaller");
		Transient = (new BlockTransientElevator(
				props.getInt("TransientElevator_blockID")))
				.setBlockName("transient");
		checkedFloorNames = new HashMap();
		checkedProperties = new HashMap();
		disallowed_renderTypes = new HashSet();
		allowed_blockIDs = new HashSet();
		disallowed_blockIDs = new HashSet();
		solid_allowed_blockIDs = new HashSet();
		solid_disallowed_blockIDs = new HashSet();
		universalLoad();
		newBlocks.add(new ItemStack(ElevatorsCore.ElevatorButton));
		newBlocks.add(new ItemStack(ElevatorsCore.ElevatorCaller));
		newBlocks.add(new ItemStack(ElevatorsCore.Elevator));
	}

	public static void configuration() {
		prepareProps();
		messages = new String[4];
		messages[0] = props.getString("elevator_called_message");
		messages[1] = props.getString("elevator_notfound_message");
		messages[2] = props.getString("arrival_message");
		messages[3] = props.getString("outoforder_message");
		floorName = props.getString("floor_title");
		basementName = props.getString("basement_title");
		verbose = props.getBoolean("verbose");
		killBelow = props.getBoolean("kill_below");
		invertKeys = props.getBoolean("invertElevatorKeys", false);
		strictShaft = props.getBoolean("entireShaftMustBeClear", true);
		elevator_entityID = props.getInt("elevator_entityID", 220);
		max_elevator_Y = props.getInt("Max_Elevator_Y", 255);
		guiElevatorID = props.getInt("Elevator_GUI_ID");
		topTexture = props.getInt("Elevator_Top_Texture");
		sideTexture = props.getInt("Elevator_SideAndBottom_Texture");
		props.save();
	}

	public static void prepareProps() {
		props.getInt("TransientElevator_blockID", 216);
		props.getInt("ElevatorButton_blockID", 215);
		props.getInt("Elevator_blockID", 214);
		props.getInt("ElevatorCaller_blockID", 213);
		props.getInt("Max_Elevator_Y", 255);
		props.getInt("Elevator_GUI_ID", 553);
		props.getBoolean("verbose", false);
		props.getBoolean("kill_below", false);
		props.getInt("Elevator_Top_Texture",
				Block.blockDiamond.blockIndexInTexture);
		props.getInt("Elevator_SideAndBottom_Texture",
				Block.blockSteel.blockIndexInTexture);
		props.getString("opening_disallowed_renderTypes",
				"0, 10, 11, 13, 14, 16, 17, 18, 24, 25, 26");
		props.getString("opening_allowed_blockIDs", "215, 77, 34");
		props.getString("opening_disallowed_blockIDs", "");
		props.getString("solid_allowed_blockIDs", "");
		props.getString("solid_disallowed_blockIDs", "61, 62, 54, 58");
		props.getString("elevator_called_message", "An elevator is coming");
		props.getString("elevator_notfound_message", "No elevator was found");
		props.getString("arrival_message", "You have arrived at ");
		props.getString("outoforder_message",
				"Out of order. Please replace elevator.");
		props.getString("floor_title", "Floor");
		props.getString("basement_title", "Basement Level");
	}

	public static void universalLoad() {
		say("Starting in verbose mode!");
		ModLoader.registerBlock(Elevator, ItemElevator.class);
		Item.itemsList[Elevator.blockID] = null;
		Item.itemsList[Elevator.blockID] = (new ItemElevator(Elevator.blockID
				- OFFSET)).setItemName("ElevatorItem");
		ModLoader.registerBlock(ElevatorButton);
		ModLoader.registerBlock(ElevatorCaller);
		ModLoader.registerBlock(Transient);
		MinecraftForge.registerEntity(EntityElevator.class,
				mod_Elevator.instance, elevator_entityID, 16, 16, true);
		ModLoader.registerTileEntity(TileEntityElevator.class, "ironelv");
		ModLoader.addName(Elevator, "Elevator");
		ModLoader.addName(ElevatorButton, "Elevator Button");
		ModLoader.addName(ElevatorCaller, "Elevator Caller");
		ModLoader.addName(Transient, "You shouldn\'t have this!");
		ModLoader.addRecipe(new ItemStack(ElevatorButton, 1), new Object[] {
				"I", "I", 'I', Item.ingotIron });
		ModLoader.addRecipe(new ItemStack(Elevator, 4), new Object[] { "IDI",
				"IRI", "III", 'I', Item.ingotIron, 'D', Item.diamond, 'R',
				Item.redstone });
		ModLoader.addRecipe(new ItemStack(ElevatorCaller, 1), new Object[] {
				"SSS", "SRS", "SSS", 'S', Block.stone, 'R', Item.redstone });
		popIntSetFromString(disallowed_renderTypes,
				props.getString("opening_disallowed_renderTypes"),
				"0, 10, 11, 13, 14, 16, 17, 18, 24, 25, 26");
		popIntSetFromString(allowed_blockIDs,
				props.getString("opening_allowed_blockIDs"), "215, 77, 34");
		popIntSetFromString(disallowed_blockIDs,
				props.getString("opening_disallowed_blockIDs"), "");
		popIntSetFromString(solid_allowed_blockIDs,
				props.getString("solid_allowed_blockIDs"), "");
		popIntSetFromString(solid_disallowed_blockIDs,
				props.getString("solid_disallowed_blockIDs"), "61, 62, 54, 58");
		allowed_blockIDs.removeAll(disallowed_blockIDs);
		solid_allowed_blockIDs.removeAll(solid_disallowed_blockIDs);
		solid_allowed_blockIDs.add(Integer.valueOf(ElevatorCaller.blockID));
		solid_disallowed_blockIDs.remove(Integer
				.valueOf(ElevatorCaller.blockID));

		if (!solid_disallowed_blockIDs.contains(Integer
				.valueOf(Block.glass.blockID))) {
			solid_allowed_blockIDs.add(Integer.valueOf(Block.glass.blockID));
		}

		if (!disallowed_renderTypes.isEmpty()) {
			say("Opening: Disallowed render types: "
					+ convertIntSetToString(disallowed_renderTypes), true);
		}

		if (!disallowed_blockIDs.isEmpty()) {
			say("Opening: Disallowed block IDs: "
					+ convertIntSetToString(disallowed_blockIDs), true);
		}

		if (!allowed_blockIDs.isEmpty()) {
			say("Opening: Allowed block IDs: "
					+ convertIntSetToString(allowed_blockIDs), true);
		}

		if (!solid_disallowed_blockIDs.isEmpty()) {
			say("Ledge: Disallowed block IDs: "
					+ convertIntSetToString(solid_disallowed_blockIDs), true);
		}

		if (!solid_allowed_blockIDs.isEmpty()) {
			say("Ledge: Allowed block IDs: "
					+ convertIntSetToString(solid_allowed_blockIDs), true);
		}

		props.setString("disallowed_renderTypes",
				convertIntSetToString(disallowed_renderTypes));
		props.setString("disallowed_blockIDs",
				convertIntSetToString(disallowed_blockIDs));
		props.setString("allowed_blockIDs",
				convertIntSetToString(allowed_blockIDs));
		props.setString("disallowed_blockIDs",
				convertIntSetToString(solid_disallowed_blockIDs));
		props.setString("allowed_blockIDs",
				convertIntSetToString(solid_allowed_blockIDs));
		props.save();
	}

	public static void say(String var0) {
		say(var0, false);
	}

	public static void say(String var0, boolean var1) {
		if (verbose || var1) {
			System.out.println("[ElevatorMod] " + var0);
		}
	}

	public static void sendRiderUpdates(PacketUpdate packet, int x, int y, int z) {
		Elevators.sendPacketToAll(packet, x, y, z);
		/*			Elevators.Core.getProxy().sendPacketToAll(packet.getPacket(),
					packet.xPosition, packet.yPosition, packet.zPosition, 16,
					mod_Elevator.instance);
		} catch (Exception var2) {
			say("An error occurred while sending update packets", true);
		}*/
	}

	public static boolean requestGUIMapping(ChunkPosition chunkpos,
			EntityPlayer entityplayer, String[] floorMap, boolean[] properties) {
		int var4 = entityplayer.entityId;

		if (chunkpos == null) {
			return false;
		} else {
			Block var5 = Block.blocksList[entityplayer.worldObj.getBlockId(
					chunkpos.x, chunkpos.y, chunkpos.z)];

			if (var5 != null && var5.blockID == Elevator.blockID) {
				BlockElevator var6 = (BlockElevator) var5;
				TileEntityElevator var7 = BlockElevator.getTileEntity(
						entityplayer.worldObj, chunkpos.x, chunkpos.y,
						chunkpos.z);

				if (var7 != null) {
					elevatorRequests.put(Integer.valueOf(var4), chunkpos);
					int[] guiData = new int[] { var4, var7.numFloors(),
							var7.curFloor() };
					double[] doubleData = new double[] { 5.0D, 5.0D, 5.0D };

					try {
						doubleData[0] = properties[0] ? 5.0D : -5.0D;
						doubleData[1] = properties[1] ? 5.0D : -5.0D;
						doubleData[2] = properties[2] ? 5.0D : -5.0D;
					} catch (Exception e) {
						say("Elevator property check from: "
								+ entityplayer.username + " has failed!");
					}

					PacketElevatorGui guiPacket = new PacketElevatorGui(
							GUI_DATA, guiData, doubleData, floorMap);
					Elevators.Core.getProxy().sendPacket(entityplayer,
							guiPacket.getPacket());
					say("Received elevator request from "
							+ entityplayer.username + ". Request was given ID#"
							+ var4);
					return true;
				} else {
					say("Unable to find mapping for player "
							+ entityplayer.username, true);
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public static boolean isEntityOnBlock(World var0, ChunkPosition var1,
			Entity var2) {
		AxisAlignedBB var3 = Elevator.getCollisionBoundingBoxFromPool(var0,
				var1.x, var1.y, var1.z);
		say("box bounds: " + var3.minX + ", " + var3.minY + ", " + var3.minZ
				+ " : " + var3.maxX + ", " + var3.maxY + ", " + var3.maxZ);
		var3.maxY += 0.5D;
		List var4 = var0.getEntitiesWithinAABBExcludingEntity((Entity) null,
				var3);
		return var4.contains(var2);
	}

	public static String pos2Str(ChunkPosition var0) {
		return var0.x + ", " + var0.y + ", " + var0.z;
	}

	public static boolean isNamed(int var0, String[] var1) {
		int var2 = var0 + 2;
		return var2 >= 3 && var2 < var1.length ? var1[var2] != null
				&& !var1[var2].equals("") : false;
	}

	public static String getFloorName(int var0, int var1, String[] var2) {
		String var3 = "";
		int var4 = var0 + 2;

		if (var4 >= 3 && var4 < var2.length) {
			var3 = var2[var4];
		}

		if (var3 == null || var3.equals("")) {
			int var5 = var1 < 1 ? var0 : var0 - var1 + 1;

			if (var0 >= var1) {
				var3 = floorName + " " + var5;
			} else {
				var3 = basementName + " " + MathHelper.abs(var5 - 1);
			}
		}

		return var3;
	}

	public static String getAbbreviatedFloorName(int var0, int var1) {
		String var2 = "";
		int var3 = var1 < 1 ? var0 : var0 - var1 + 1;

		if (var3 > 0) {
			var2 = String.valueOf(var3);
		} else {
			var2 = "B" + String.valueOf(MathHelper.abs(var3 - 1));
		}

		return var2;
	}

	public static String convertIntSetToString(Set var0) {
		int var1 = 0;
		String var2 = "";
		Iterator var3 = var0.iterator();

		while (var3.hasNext()) {
			++var1;
			var2 = var2 + var3.next();

			if (var1 < var0.size()) {
				var2 = var2 + ", ";
			}
		}

		return var2;
	}

	public static void popIntSetFromString(Set var0, String var1, String var2) {
		String[] var3 = var1.split(",", 0);

		try {
			for (int var4 = 0; var4 < var3.length; ++var4) {
				if (!var3[var4].trim().isEmpty()) {
					var0.add(Integer.valueOf(Integer.parseInt(var3[var4].trim())));
				}
			}
		} catch (Exception var5) {
			say("There was a problem reading the properties file, using default list instead.");
			var0.clear();
			popIntSetFromString(var0, var2, "");
		}
	}

	public static boolean isBlockOpeningMaterial(World var0, ChunkPosition var1) {
		return isBlockOpeningMaterial(var0, var1.x, var1.y, var1.z);
	}

	public static boolean isBlockOpeningMaterial(World var0, int var1,
			int var2, int var3) {
		Block var4 = Block.blocksList[var0.getBlockId(var1, var2, var3)];
		return var4 == null ? true : (var4.blockMaterial.isGroundCover() ? true
				: (disallowed_blockIDs.contains(Integer.valueOf(var0
						.getBlockId(var1, var2, var3))) ? false
						: (allowed_blockIDs.contains(Integer.valueOf(var0
								.getBlockId(var1, var2, var3))) ? true
								: !disallowed_renderTypes.contains(Integer
										.valueOf(var4.getRenderType())))));
	}

	public static boolean isBlockLedgeMaterial(World var0, int var1, int var2,
			int var3) {
		Block var4 = Block.blocksList[var0.getBlockId(var1, var2, var3)];
		return var4 == null ? false
				: (solid_disallowed_blockIDs.contains(Integer.valueOf(var0
						.getBlockId(var1, var2, var3))) ? false
						: (var4.blockMaterial.isGroundCover() ? false
								: (var4.blockID == Elevator.blockID ? false
										: (solid_allowed_blockIDs
												.contains(Integer.valueOf(var0
														.getBlockId(var1, var2,
																var3))) ? true
												: (var4.isOpaqueCube() ? true
														: var4.renderAsNormalBlock())))));
	}

	public static void refreshElevator(World var0, ChunkPosition var1) {
		refreshElevator(var0, var1, 2);
	}

	public static void refreshElevator(World var0, ChunkPosition var1, int var2) {
		int var3 = var0.getBlockId(var1.x, var1.y, var1.z);

		if (var3 == Elevator.blockID) {
			var0.scheduleBlockUpdate(var1.x, var1.y, var1.z, var3, var2);
		}
	}

	public static void elevator_requestFloor(World var0, ChunkPosition var1,
			int var2) {
		TileEntityElevator var3 = BlockElevator.getTileEntity(var0, var1.x,
				var1.y, var1.z);

		if (var3 != null) {
			if (var3.requestFloor(var2)) {
				say("Destination set: " + var3.getDestination());
				refreshElevator(var0, var1, 10);
			}
		}
	}

	public static void elevator_demandY(World var0, ChunkPosition var1, int var2) {
		TileEntityElevator var3 = BlockElevator.getTileEntity(var0, var1.x,
				var1.y, var1.z);

		if (var3 != null) {
			if (var3.demandY(var2)) {
				say("Destination set: " + var3.getDestination());
				refreshElevator(var0, var1, 10);
			}
		}
	}

	public static void elevator_reset(World var0, ChunkPosition var1) {
		TileEntityElevator var2 = BlockElevator.getTileEntity(var0, var1.x,
				var1.y, var1.z);

		if (var2 != null) {
			if (var2.reset()) {
				say("Destination set: " + var2.getDestination());
				refreshElevator(var0, var1, 10);
			}
		}
	}

	public static void elevator_powerOn(World var0, ChunkPosition var1) {
		TileEntityElevator var2 = BlockElevator.getTileEntity(var0, var1.x,
				var1.y, var1.z);

		if (var2 != null) {
			var2.setFirstRefresh();
			refreshElevator(var0, var1, 10);
		}
	}
}
