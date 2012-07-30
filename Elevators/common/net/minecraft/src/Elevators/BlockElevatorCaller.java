package net.minecraft.src.Elevators;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class BlockElevatorCaller extends Block {
	static Set elvs = new HashSet();
	static Set checkedBlocks = new HashSet();
	static Set checkedCallers = new HashSet();
	boolean previouslyPowered = false;

	public BlockElevatorCaller(int id, Material material) {
		super(id, material);
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
		this.blockIndexInTexture = Block.stoneOvenIdle.blockIndexInTexture + 17;
	}

	private boolean isBeingPoweredByNonElevator(World world, int x, int y,
			int z) {
		for (int i = 0; i < 6; ++i) {
			int currentX = x;
			int currentZ = z;
			int currentY = y;

			if (i == 0) {
				currentY = y - 1;
			} else if (i == 1) {
				currentY = y + 1;
			} else if (i == 2) {
				currentZ = z - 1;
			} else if (i == 3) {
				currentZ = z + 1;
			} else if (i == 4) {
				currentX = x - 1;
			} else if (i == 5) {
				currentX = x + 1;
			}

			int blockId = world.getBlockId(currentX, currentY, currentZ);
			ElevatorsCore.say("Checking: " + currentX + ", " + currentY + ", " + currentZ
					+ ": has block ID" + blockId);

			if (blockId > 0
					&& blockId != ElevatorsCore.Elevator.blockID
					&& Block.blocksList[blockId].isIndirectlyPoweringTo(world,
							currentX, currentY, currentZ, i)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns Returns true if the given side of this block type should be
	 * rendered (if it's solid or not), if the adjacent block is at the given
	 * coordinates. Args: blockAccess, x, y, z, side
	 */
	public boolean isBlockSolid(IBlockAccess iblockaccess, int x, int y,
			int z, int side) {
		return true;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (this.isBeingPoweredByNonElevator(world, x, y, z)
				&& !this.previouslyPowered) {
			findAndActivateElevator(world, x, y, z, 0);
			this.previouslyPowered = true;
		} else if (!this.isBeingPoweredByNonElevator(world, x, y, z)) {
			this.previouslyPowered = false;
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	public void onNeighborBlockChange(World world, int x, int y, int z,
			int blockId) {
		if (blockId != ElevatorsCore.ElevatorButton.blockID) {
			if (blockId <= 0
					|| blockId == ElevatorsCore.Elevator.blockID
					|| !Block.blocksList[blockId].canProvidePower()
					|| !this.isBeingPoweredByNonElevator(world, x, y, z)) {
				this.previouslyPowered = false;
				return;
			}

			if (!this.previouslyPowered) {
				world.scheduleBlockUpdate(x, y, z, this.blockID, 2);
			}
		} else {
			boolean isElevator = false;

			for (int i = 0; i < 6; ++i) {
				int currentX = x;
				int currentZ = z;

				if (i == 0) {
					currentX = x - 1;
				} else if (i == 1) {
					currentX = x + 1;
				} else if (i == 2) {
					currentZ = z - 1;
				} else if (i == 3) {
					currentZ = z + 1;
				}

				if (world.getBlockId(currentX, y, currentZ) == ElevatorsCore.ElevatorButton.blockID
						&& (world.getBlockMetadata(currentX, y, currentZ) & 8) > 0) {
					isElevator = true;
				}
			}

			if (isElevator) {
				findAndActivateElevator(world, x, y, z, 0);
			}
		}
	}

	public static boolean findAndActivateElevator(World world, int x,
			int y, int z, int size) {
		if (size > 16) {
			return false;
		} else {
			if (size == 0) {
				checkedCallers.clear();
				checkedBlocks.clear();
				elvs.clear();
			}

			checkedCallers.add(new ChunkPosition(x, y, z));
			checkForElevators(world, new ChunkPosition(x, y, z), 0);
			ElevatorsCore.say("ElevatorCaller activated at: " + x + ", "
					+ y + ", " + z);
			ElevatorsCore.say("Checked " + checkedBlocks.size() + " blocks");
			ElevatorsCore.say("Found " + elvs.size() + " elevators");
			int maxSize = 500;
			ChunkPosition chunkPos = null;
			int closestFromY;

			if (!elvs.isEmpty()) {
				Iterator var8 = elvs.iterator();

				while (var8.hasNext()) {
					ChunkPosition newChunkPos = (ChunkPosition) var8.next();
					BlockElevator.refreshAndCombineAllAdjacentElevators(world,
							newChunkPos);
					TileEntityElevator tileentityelevator = BlockElevator.getTileEntity(
							world, newChunkPos.x, newChunkPos.y, newChunkPos.z);

					if (tileentityelevator != null) {
						closestFromY = tileentityelevator.getClosestYFromYCoor(y);

						if (MathHelper.abs((closestFromY - newChunkPos.y)) < maxSize) {
							maxSize = (int) MathHelper
									.abs((closestFromY - newChunkPos.y));
							chunkPos = newChunkPos;
						}
					}
				}
			}

			if (chunkPos != null) {
				ElevatorsCore.elevator_demandY(world, chunkPos, y);
				clearSets(size);
				return true;
			} else {
				boolean elevatorActivated = false;

				for (int i = 0; i < 6 && !elevatorActivated; ++i) {
					int currentX = x;
					closestFromY = y;
					int currentZ = z;

					if (i == 0) {
						closestFromY = y - 1;
					} else if (i == 1) {
						closestFromY = y + 1;
					} else if (i == 2) {
						currentZ = z - 1;
					} else if (i == 3) {
						currentZ = z + 1;
					} else if (i == 4) {
						currentX = x - 1;
					} else if (i == 5) {
						currentX = x + 1;
					}

					if (world.getBlockId(currentX, closestFromY, currentZ) == ElevatorsCore.ElevatorCaller.blockID
							&& !checkedCallers.contains(new ChunkPosition(
									currentX, closestFromY, currentZ))) {
						elevatorActivated = findAndActivateElevator(world, currentX, closestFromY,
								currentZ, size + 1);
					}
				}

				clearSets(size);
				return elevatorActivated;
			}
		}
	}

	public static void checkForElevators(World world, ChunkPosition chunkpos,
			int elevators) {
		if (!checkedBlocks.contains(chunkpos)) {
			checkedBlocks.add(chunkpos);
			boolean isElevator = false;

			if (world.getBlockId(chunkpos.x, chunkpos.y, chunkpos.z) == ElevatorsCore.Elevator.blockID) {
				if (!BlockElevator.isCeiling(world, chunkpos)) {
					elvs.add(chunkpos);
					return;
				}

				isElevator = true;
			}

			if (!isElevator && !ElevatorsCore.isBlockOpeningMaterial(world, chunkpos)) {
				++elevators;

				if (elevators > 2) {
					return;
				}

				for (int i = 0; i < 4; ++i) {
					int currentX = chunkpos.x;
					int currentZ = chunkpos.z;

					if (i == 0) {
						--currentZ;
					} else if (i == 1) {
						++currentZ;
					} else if (i == 2) {
						--currentX;
					} else if (i == 3) {
						++currentX;
					}

					ChunkPosition newChunkPos = new ChunkPosition(currentX, chunkpos.y, currentZ);
					checkForElevators(world, newChunkPos, elevators);
				}
			} else {
				if (chunkpos.y > 0
						&& !ElevatorsCore.isBlockLedgeMaterial(world, chunkpos.x,
								chunkpos.y - 1, chunkpos.z)) {
					checkForElevators(world, new ChunkPosition(chunkpos.x,
							chunkpos.y - 1, chunkpos.z), elevators);
				}

				if (chunkpos.y < ElevatorsCore.max_elevator_Y
						&& !ElevatorsCore.isBlockLedgeMaterial(world, chunkpos.x,
								chunkpos.y + 1, chunkpos.z)) {
					checkForElevators(world, new ChunkPosition(chunkpos.x,
							chunkpos.y + 1, chunkpos.z), elevators);
				}
			}
		}
	}

	private static void clearSets(int setToClear) {
		if (setToClear == 0) {
			elvs.clear();
			checkedBlocks.clear();
			checkedCallers.clear();
		}
	}
}
