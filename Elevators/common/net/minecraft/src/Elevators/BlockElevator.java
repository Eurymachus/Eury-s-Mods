package net.minecraft.src.Elevators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockElevator extends BlockContainer {
	static boolean isClient = true;
	static List conjoinedElevators = new ArrayList();
	public static final int NO_ACTION = 0;
	public static final int REQUEST_NEW_FLOOR = 1;
	public static final int DEMAND_NEW_FLOOR = 2;
	public static final int POWER_ON = 3;
	public static final int NO_FLOOR = 0;

	public BlockElevator(int id) {
		super(id, 22, Material.iron);
		setHardness(3.0F);
		setResistance(10.0F);
		setStepSound(Block.soundMetalFootstep);
		setRequiresSelfNotify();
		this.minX = 0.0D;
		this.maxX = 1.0D;
		this.minY = 0.0D;
		this.maxY = 1.0D;
		this.minZ = 0.0D;
		this.maxZ = 1.0D;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return isCeiling(var2) ? (var1 != 0 ? ElevatorsCore.sideTexture
				: ElevatorsCore.topTexture)
				: (var1 != 1 ? ElevatorsCore.sideTexture
						: ElevatorsCore.topTexture);
	}

	public static TileEntityElevator getTileEntity(World world, int x, int y,
			int z) {
		if (isCeiling(world, x, y, z)) {
			return null;
		} else if (world.getBlockId(x, y, z) != ElevatorsCore.Elevator.blockID) {
			ElevatorsCore.say("That\'s not an elevator.");
			return null;
		} else {
			TileEntityElevator tileentityelevator = (TileEntityElevator) world
					.getBlockTileEntity(x, y, z);

			if (tileentityelevator != null && tileentityelevator instanceof TileEntityElevator) {
				return tileentityelevator;
			} else {
				world.setBlockTileEntity(x, y, z, new TileEntityElevator());
				TileEntityElevator tileentityelevator1 = (TileEntityElevator) world
						.getBlockTileEntity(x, y, z);
				return tileentityelevator1 != null && tileentityelevator1 instanceof TileEntityElevator ? tileentityelevator1
						: null;
			}
		}
	}

	public static TileEntityElevator getTileEntity(IBlockAccess iblockaccess, int x,
			int y, int z) {
		if (isCeiling(iblockaccess.getBlockMetadata(x, y, z))) {
			return null;
		} else if (iblockaccess.getBlockId(x, y, z) != ElevatorsCore.Elevator.blockID) {
			ElevatorsCore.say("That\'s not an elevator.");
			return null;
		} else {
			TileEntityElevator tileentityelevator = (TileEntityElevator) iblockaccess
					.getBlockTileEntity(x, y, z);
			return tileentityelevator != null && tileentityelevator instanceof TileEntityElevator ? tileentityelevator
					: null;
		}
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World world, int x, int y, int z) {
		ChunkPosition chunkpos = new ChunkPosition(x, y, z);
		this.updateCeilingStatus(world, x, y, z);
		this.updateCeilingStatus(world, x, y + 3, z);
		getTileEntity(world, x, y, z);
		refreshAndCombineAllAdjacentElevators(world, chunkpos, true);
		checkoutWaitingNames(world, x, y, z);
		ElevatorsCore.elevator_powerOn(world, chunkpos);
		ElevatorsCore.say("Elevator Added: " + ElevatorsCore.pos2Str(chunkpos));
	}

	/**
	 * Called whenever the block is removed.
	 */
	public void onBlockRemoval(World world, int x, int y, int z) {
		ChunkPosition chunkpos = new ChunkPosition(x, y, z);
		super.onBlockRemoval(world, x, y, z);
		world.removeBlockTileEntity(x, y, z);

		if (hasCeiling(world, x, y, z)) {
			this.updateCeilingStatus(world, x, y + 3, z);
		}

		refreshAndCombineAllAdjacentElevators(world, chunkpos);
		this.notifyNeighbors(world, x, y, z);
		ElevatorsCore.say("Elevator Removed: " + ElevatorsCore.pos2Str(chunkpos));
	}

	public static boolean checkoutWaitingNames(World world, int x, int y,
			int z) {
		TileEntityElevator var4 = getTileEntity(world, x, y, z);
		ElevatorsCore.say("Looking for waiting property entries...");
		return var4 != null ? var4.checkoutData() : false;
	}

	public static boolean hasCeiling(World world, ChunkPosition chunkpos) {
		return hasCeiling(world, chunkpos.x, chunkpos.y, chunkpos.z);
	}

	public static boolean hasCeiling(World world, int x, int y, int z) {
		return y > ElevatorsCore.max_elevator_Y - 3 ? false : (isCeiling(
				world, x, y, z) ? false : world.getBlockId(x,
				y + 3, z) == ElevatorsCore.Elevator.blockID);
	}

	public static boolean hasFloor(World world, int x, int y, int z) {
		return y < 3 ? false
				: world.getBlockId(x, y - 3, z) == ElevatorsCore.Elevator.blockID;
	}

	public static boolean isCeiling(World world, ChunkPosition chunkpos) {
		return isCeiling(world, chunkpos.x, chunkpos.y, chunkpos.z);
	}

	public static boolean isCeiling(World world, int x, int y, int z) {
		return isCeiling(world.getBlockMetadata(x, y, z));
	}

	public static boolean isCeiling(int metaData) {
		return (metaData & 1) == 1;
	}

	public void updateCeilingStatus(World world, int x, int y, int z) {
		if (world.getBlockId(x, y, z) == ElevatorsCore.Elevator.blockID) {
			int metaData = world.getBlockMetadata(x, y, z);

			if (hasFloor(world, x, y, z)
					&& !isCeiling(world, x, y, z)) {
				metaData |= 1;
				world.setBlockMetadataWithNotify(x, y, z, metaData);
			} else if (!hasFloor(world, x, y, z)
					&& isCeiling(world, x, y, z)) {
				this.dropBlockAsItem(world, x, y, z, 0, 0);
				world.setBlockWithNotify(x, y, z, 0);
			}
		}
	}

	public static boolean hasOpening(World world, int x, int y, int z,
			int l, boolean strictshaft, boolean yCheck) {
		boolean hasCeiling = hasCeiling(world, x, l, z);
		int ceilingY = hasCeiling ? y + 3 : y + 2;

		if (y != l && !world.isAirBlock(x, y, z)
				&& (!hasCeiling || y != l + 3) && !yCheck) {
			return false;
		} else if (!strictshaft && !hasCeiling) {
			return true;
		} else {
			for (int var9 = y + 1; var9 <= ceilingY; ++var9) {
				boolean var10 = ElevatorsCore.isBlockOpeningMaterial(world,
						x, var9, z) || l == var9;
				var10 = var10 || hasCeiling && var9 == l + 3;

				if (!var10) {
					return false;
				}
			}

			return true;
		}
	}

	public static boolean hasPossibleFloor(World world, int x, int y,
			int z, int l) {
		if (!hasOpening(world, x, y, z, l, true, false)) {
			return false;
		} else {
			for (int i = 0; i < 4; ++i) {
				int currentX = x;
				int currentZ = z;

				if (i == 0) {
					currentZ = z - 1;
				} else if (i == 1) {
					currentZ = z + 1;
				} else if (i == 2) {
					currentX = x - 1;
				} else if (i == 3) {
					currentX = x + 1;
				}

				if (ElevatorsCore.isBlockLedgeMaterial(world, currentX, y, currentZ)
						&& ElevatorsCore.isBlockOpeningMaterial(world, currentX,
								y + 1, currentZ)
						&& ElevatorsCore.isBlockOpeningMaterial(world, currentX,
								y + 2, currentZ)) {
					return true;
				}
			}

			return false;
		}
	}

	public static Set refreshElevator(World var0, ChunkPosition var1) {
		return refreshElevator(var0, var1.x, var1.y, var1.z);
	}

	public static Set refreshElevator(World world, int x, int y, int z) {
		HashSet elevatorList = new HashSet();

		if (world.getBlockId(x, y, z) != ElevatorsCore.Elevator.blockID) {
			return elevatorList;
		} else {
			for (int i = y; i > 0
					&& (i == y || ElevatorsCore.isBlockOpeningMaterial(
							world, x, i, z)); --i) {
				if (hasPossibleFloor(world, x, i, z, y)) {
					elevatorList.add(Integer.valueOf(i));

					if (i == y) {
					}
				}
			}


			for (int j = y + 1; j < ElevatorsCore.max_elevator_Y - 3
					&& (ElevatorsCore.isBlockOpeningMaterial(world, x, j,
							z) || j == y + 3
							&& hasCeiling(world, x, y, z)); ++j) {
				if (hasPossibleFloor(world, x, j, z, y)) {
					elevatorList.add(Integer.valueOf(j));
				}
			}

			TileEntityElevator tileentityelevator = getTileEntity(world, x, y, z);

			if (tileentityelevator != null) {
				tileentityelevator.setFloors(elevatorList);
			}

			return elevatorList;
		}
	}

	public static boolean isReachable(World world, ChunkPosition chunkpos, int posY) {
		return isReachable(world, chunkpos, posY, ElevatorsCore.strictShaft);
	}

	public static boolean isReachable(World world, ChunkPosition chunkpos, int posY,
			boolean strictshaft) {
		if (chunkpos.y > 0 && chunkpos.y < ElevatorsCore.max_elevator_Y) {
			int y;

			if (posY < chunkpos.y) {
				for (y = posY; y <= chunkpos.y; ++y) {
					if (!hasOpening(world, chunkpos.x, y, chunkpos.z, posY, strictshaft,
							y != posY && y != chunkpos.y)) {
						ElevatorsCore.say("Blocked at: " + chunkpos.x + ", " + y
								+ ", " + chunkpos.z);
						return false;
					}
				}
			} else {
				if (posY == chunkpos.y) {
					return true;
				}

				for (y = posY; y >= chunkpos.y; --y) {
					if (!hasOpening(world, chunkpos.x, y, chunkpos.z, posY, strictshaft,
							y != posY && y != chunkpos.y)) {
						ElevatorsCore.say("Blocked at: " + chunkpos.x + ", " + y
								+ ", " + chunkpos.z);
						return false;
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	public static void refreshAndCombineAllAdjacentElevators(World var0,
			ChunkPosition var1) {
		refreshAndCombineAllAdjacentElevators(var0, var1, false);
	}

	public static void refreshAndCombineAllAdjacentElevators(World world,
			ChunkPosition chunkpos, boolean combine) {
		boolean test = false;
		boolean[] properties = new boolean[] { true, true, true };
		boolean isWaiting = false;
		HashMap var6 = new HashMap();
		TileEntityElevator tileentityelevator = getTileEntity(world, chunkpos.x, chunkpos.y, chunkpos.z);

		if (!combine && tileentityelevator != null) {
			isWaiting = checkoutWaitingNames(world, chunkpos.x, chunkpos.y, chunkpos.z);
			var6.putAll(tileentityelevator.getAllProperties());

			if (isWaiting) {
				properties[2] = tileentityelevator.getMobilePower();
				properties[1] = tileentityelevator.getCanHalt();
				properties[0] = tileentityelevator.getCanProvidePower();
			}
		}

		ElevatorsCore.say("Combining adjacent elevators...");
		resetAdjacenciesList(world, chunkpos);
		Iterator elevators = conjoinedElevators.iterator();
		HashSet elevatorsList = new HashSet();

		while (elevators.hasNext()) {
			ChunkPosition elevatorChunkPos = (ChunkPosition) elevators.next();
			elevatorsList.addAll(refreshElevator(world, elevatorChunkPos));
			TileEntityElevator tileentityelevator1 = getTileEntity(world, elevatorChunkPos.x, elevatorChunkPos.y,
					elevatorChunkPos.z);

			if (tileentityelevator1 != null) {
				tileentityelevator1.setFloorNames(var6);

				if (isWaiting) {
					tileentityelevator1.convertArrayToBooleanProperties(properties);
				}
			}
		}

		if (!test) {
			ElevatorsCore
					.say("----------------------------------------------------");
			ElevatorsCore.say("Testing the following floors: ");
			listFloors(elevatorsList);
		}

		Iterator newElevators = elevatorsList.iterator();
		HashSet newElevatorsList = new HashSet();
		int elevatorY;

		while (newElevators.hasNext()) {
			elevatorY = ((Integer) newElevators.next()).intValue();

			if (!test) {
				ElevatorsCore.say("testing y = " + elevatorY);
			}

			boolean iterate = true;

			for (int i = 0; i < conjoinedElevators.size() && iterate; ++i) {
				ChunkPosition conjoinedPosition = (ChunkPosition) conjoinedElevators
						.get(i);

				if (!isReachable(world, new ChunkPosition(conjoinedPosition.x, elevatorY,
						conjoinedPosition.z), conjoinedPosition.y)) {
					iterate = false;
				}
			}

			if (!iterate) {
				newElevatorsList.add(Integer.valueOf(elevatorY));
			}
		}

		if (!test) {
			ElevatorsCore
					.say("----------------------------------------------------");
			ElevatorsCore.say("The following floors will be removed: ");
			listFloors(newElevatorsList);
		}

		elevatorsList.removeAll(newElevatorsList);

		if (!test) {
			ElevatorsCore
					.say("----------------------------------------------------");
			ElevatorsCore
					.say("Populating all adjacent elevators with the following floors: ");
			listFloors(elevatorsList);
			ElevatorsCore
					.say("----------------------------------------------------");
		}

		for (elevatorY = 0; elevatorY < conjoinedElevators.size(); ++elevatorY) {
			ChunkPosition conjoinedPosition = (ChunkPosition) conjoinedElevators.get(elevatorY);
			TileEntityElevator tileentityelevator2 = getTileEntity(world, conjoinedPosition.x, conjoinedPosition.y,
					conjoinedPosition.z);

			if (tileentityelevator2 != null) {
				tileentityelevator2.setFloors(elevatorsList);
			}
		}
	}

	public static void listFloors(Set floorsToList) {
		Iterator floors = floorsToList.iterator();

		while (floors.hasNext()) {
			ElevatorsCore.say("Floor at y = " + floors.next() + ".");
		}
	}

	/**
	 * Called upon block activation (left or right click on the block.). The
	 * three integers represent x,y,z of the block.
	 */
	public boolean blockActivated(World world, int x, int y, int z,
			EntityPlayer entityplayer) {
		if (world.isRemote) {
			return true;
		} else {
			this.updateCeilingStatus(world, x, y, z);
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();

			if (itemstack != null && itemstack.itemID == ElevatorsCore.Elevator.blockID) {
				return false;
			} else {
				boolean isCeiling = false;

				if (isCeiling(world, x, y, z)) {
					y -= 3;
					isCeiling = true;
				}

				ElevatorsCore
						.say("----------------------------------------------------");
				ElevatorsCore.say("Elevator Activated! - at " + x + ", "
						+ y + ", " + z);
				ChunkPosition chunkPos = new ChunkPosition(x, y, z);
				refreshAndCombineAllAdjacentElevators(world, chunkPos);
				TileEntityElevator tileentityelevator = getTileEntity(world, x, y, z);

				if (tileentityelevator == null) {
					entityplayer.addChatMessage(ElevatorsCore.messages[3]);
					return true;
				} else {
					int floorsBelow = tileentityelevator.floorsBelow();
					int floorsAbove = tileentityelevator.floorsAbove();
					int currentFloor = tileentityelevator.curFloor();
					ElevatorsCore.say("Floors Below: " + floorsBelow);
					ElevatorsCore.say("Floors Above: " + floorsAbove);
					ElevatorsCore.say("Current floor: " + currentFloor);
					ElevatorsCore.say("Player is on elevator: "
							+ this.isEntityOnThisElevator(world, chunkPos, entityplayer));

					if (floorsBelow == 1
							&& (!isCeiling && !hasCeiling(world, chunkPos) || floorsAbove == 0)
							&& this.isEntityOnThisElevator(world, chunkPos, entityplayer)) {
						ElevatorsCore
								.say("\'Short circuit\' request to bottom floor!");
						ElevatorsCore.elevator_requestFloor(world, chunkPos,
								currentFloor - 1);
					} else if (floorsAbove == 1 && (isCeiling || floorsBelow == 0)
							&& this.isEntityOnThisElevator(world, chunkPos, entityplayer)) {
						ElevatorsCore
								.say("\'Short circuit\' request to top floor!");
						ElevatorsCore.elevator_requestFloor(world, chunkPos,
								currentFloor + 1);
					} else {
						Elevators.openGUI(entityplayer, this, chunkPos, tileentityelevator);
					}

					ElevatorsCore
							.say("----------------------------------------------------");
					return true;
				}
			}
		}
	}

	public boolean isEntityOnThisElevator(World world, ChunkPosition chunkpos,
			EntityPlayer entityplayer) {
		Iterator elevators = conjoinedElevators.iterator();

		do {
			if (!elevators.hasNext()) {
				return false;
			}
		} while (!ElevatorsCore.isEntityOnBlock(world,
				(ChunkPosition) elevators.next(), entityplayer));

		return true;
	}

	public static void resetAdjacenciesList(World world, ChunkPosition chunkpos) {
		if (!conjoinedElevators.isEmpty()) {
			conjoinedElevators.clear();
		}

		populateAdjacenciesList(world, chunkpos, 0);
	}

	public static void populateAdjacenciesList(World world, ChunkPosition chunkpos,
			int numOfAdjacencies) {
		conjoinedElevators.add(chunkpos);

		if (numOfAdjacencies <= 127) {
			for (int i = 0; i < 4; ++i) {
				int posX = chunkpos.x;
				int posZ = chunkpos.z;

				if (i == 0) {
					++posX;
				} else if (i == 1) {
					--posX;
				} else if (i == 2) {
					++posZ;
				} else if (i == 3) {
					--posZ;
				}

				ChunkPosition var6 = new ChunkPosition(posX, chunkpos.y, posZ);

				if (!conjoinedElevators.contains(var6)
						&& world.getBlockId(posX, chunkpos.y, posZ) == ElevatorsCore.Elevator.blockID) {
					populateAdjacenciesList(world, var6, numOfAdjacencies + 1);
				}
			}
		}
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	public boolean canProvidePower() {
		return true;
	}

	/**
	 * Is this block indirectly powering the block on the specified side
	 */
	public boolean isIndirectlyPoweringTo(World world, int x, int y,
			int z, int side) {
		return this.isPoweringTo(world, x, y, z, side);
	}

	/**
	 * Is this block powering the block on the specified side
	 */
	public boolean isPoweringTo(IBlockAccess iblockaccess, int x, int y,
			int z, int side) {
		if (isCeiling(iblockaccess.getBlockMetadata(x, y, z))) {
			return false;
		} else if (side == 0) {
			return false;
		} else {
			TileEntityElevator tileentityelevator = getTileEntity(iblockaccess, x, y, z);
			return tileentityelevator == null ? false : tileentityelevator.getProvidesPower();
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World world, int x, int y, int z, Random random) {
		ChunkPosition chunkpos = new ChunkPosition(x, y, z);
		TileEntityElevator tileentityelevator = getTileEntity(world, x, y, z);

		if (tileentityelevator != null) {
			int currentState = tileentityelevator.getCurrentState();
			int destination = tileentityelevator.getDestination();
			boolean isPowering = tileentityelevator.getProvidesPower();
			ElevatorsCore.say("Elevator Updated at "
					+ ElevatorsCore.pos2Str(chunkpos));
			ElevatorsCore.say("Current Y: " + y + ", requested Y: " + destination
					+ "; Proving power: " + isPowering);
			refreshAndCombineAllAdjacentElevators(world, chunkpos);
			ChunkPosition newChunkPos;

			switch (currentState) {
			case 0:
				ElevatorsCore.say("No action is currently required");

				if (isPowering) {
					break;
				}

			case 2:
				ElevatorsCore.say("Block has been demanded to " + destination);

				for (int i = 0; i < conjoinedElevators.size()
						&& destination != y; ++i) {
					newChunkPos = (ChunkPosition) conjoinedElevators.get(i);

					if (!isReachable(world, new ChunkPosition(newChunkPos.x, destination,
							newChunkPos.z), newChunkPos.y, false)) {
						destination = y;
						ElevatorsCore
								.say("Unable to meet demand - part of the elevator is blocked!");
						ElevatorsCore.say("Elevator chunk blocked at: "
								+ ElevatorsCore.pos2Str(newChunkPos));
					}
				}

				if (destination == y) {
					tileentityelevator.clearState();
					break;
				}

			case 1:
				if (isPowering && destination != y) {
					ElevatorsCore.say("Block has been demanded to " + destination
							+ " - toggling power in preparation for travel");
					this.toggleConjoinedPower(world, false);
					ElevatorsCore.refreshElevator(world, chunkpos, 2);
				} else if (!world.isRemote && destination != y
						&& (tileentityelevator.hasFloorAt(destination) || currentState == 2)) {
					ElevatorsCore
							.say("Current is not the same as requested! Move requested!");
					new HashSet();
					newChunkPos = null;

					for (int i = 0; i < conjoinedElevators.size(); ++i) {
						ChunkPosition conjoinedPosition = (ChunkPosition) conjoinedElevators
								.get(i);
						ElevatorsCore.say("Adjoined at " + conjoinedPosition.x + ", "
								+ conjoinedPosition.y + ", " + conjoinedPosition.z);
						TileEntityElevator tileentityelevator1 = getTileEntity(world, conjoinedPosition.x,
								conjoinedPosition.y, conjoinedPosition.z);

						if (tileentityelevator1 != null && !isCeiling(world, conjoinedPosition)) {
							tileentityelevator1.demandY(destination);
							int var16 = world.getBlockMetadata(conjoinedPosition.x, conjoinedPosition.y,
									conjoinedPosition.z);
							EntityElevator entityelevator = new EntityElevator(world,
									conjoinedPosition, destination, false, isClient, var16);

							if (conjoinedPosition.x == x && conjoinedPosition.y == y
									&& conjoinedPosition.z == z || isClient) {
								entityelevator.center = true;
							}

							if (hasCeiling(world, conjoinedPosition)) {
								ChunkPosition newConjoinedPosition = new ChunkPosition(
										conjoinedPosition.x, conjoinedPosition.y + 3, conjoinedPosition.z);
								EntityElevator newEntityElevator = new EntityElevator(world,
										newConjoinedPosition, destination + 3, false, isClient,
										world.getBlockMetadata(newConjoinedPosition.x, newConjoinedPosition.y,
												newConjoinedPosition.z));
								newEntityElevator.center = isClient;
								newEntityElevator.centerElevator = entityelevator;
								newEntityElevator.floor = entityelevator;
								entityelevator.ceiling = newEntityElevator;
								world.spawnEntityInWorld(newEntityElevator);
							}

							world.spawnEntityInWorld(entityelevator);
						}
					}

					tileentityelevator.clearState();
				} else {
					tileentityelevator.clearState();
				}

				break;

			case 3:
				ElevatorsCore.say("Block needs to be powered on!");
				this.toggleConjoinedPower(world, true);
				this.notifyNeighbors(world, x, y, z);
				tileentityelevator.clearState();
			}
		}
	}

	public void toggleConjoinedPower(World world, boolean isPowered) {
		for (int i = 0; i < conjoinedElevators.size(); ++i) {
			ChunkPosition chunkpos = (ChunkPosition) conjoinedElevators.get(i);
			TileEntityElevator var5 = getTileEntity(world, chunkpos.x, chunkpos.y,
					chunkpos.z);
			var5.setPower(isPowered);
		}
	}

	public void notifyNeighbors(World world, int x, int y, int z) {
		ElevatorsCore.say("Notifying neighbors of change...");
		world.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
		world.notifyBlocksOfNeighborChange(x - 1, y, z, this.blockID);
		world.notifyBlocksOfNeighborChange(x + 1, y, z, this.blockID);
		world.notifyBlocksOfNeighborChange(x, y, z - 1, this.blockID);
		world.notifyBlocksOfNeighborChange(x, y, z + 1, this.blockID);
	}

	/**
	 * Returns the TileEntity used by this block.
	 */
	public TileEntity getBlockEntity() {
		return new TileEntityElevator();
	}
}
