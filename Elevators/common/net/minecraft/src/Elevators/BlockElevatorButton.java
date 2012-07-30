package net.minecraft.src.Elevators;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import net.minecraft.src.Block;
import net.minecraft.src.BlockButton;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class BlockElevatorButton extends BlockButton {
	Set elvs = new HashSet();
	Set checkedBlocks = new HashSet();

	public BlockElevatorButton(int var1, int var2) {
		super(var1, var2);
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
		setRequiresSelfNotify();
	}

	public boolean canBePlacedOnBlock(World world, int x, int y, int z) {
		return world.isBlockNormalCube(x, y, z)
				|| world.getBlockId(x, y, z) == ElevatorsCore.ElevatorCaller.blockID;
	}

	/**
	 * Is this block powering the block on the specified side
	 */
	public boolean isPoweringTo(IBlockAccess iblockaccess, int x, int y,
			int z, int side) {
		return false;
	}

	/**
	 * Is this block indirectly powering the block on the specified side
	 */
	public boolean isIndirectlyPoweringTo(World world, int x, int y,
			int z, int side) {
		return false;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	public boolean canProvidePower() {
		return false;
	}

	/**
	 * Called upon block activation (left or right click on the block.). The
	 * three integers represent x,y,z of the block.
	 */
	public boolean blockActivated(World world, int x, int y, int z,
			EntityPlayer entityplayer) {
		int k = world.getBlockMetadata(x, y, z);
		int l = k & 7;
		int m = 8 - (k & 8);

		if (m == 0) {
			return true;
		} else {
			world.setBlockMetadataWithNotify(x, y, z, l + m);
			world.scheduleBlockUpdate(x, y, z, this.blockID,
					this.tickRate());
			world.markBlocksDirty(x, y, z, x, y, z);
			world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D,
					"random.click", 0.3F, 0.6F);
			ChunkPosition chunkpos = null;

			if (l == 1) {
				chunkpos = new ChunkPosition(x - 1, y, z);
			} else if (l == 2) {
				chunkpos = new ChunkPosition(x + 1, y, z);
			} else if (l == 3) {
				chunkpos = new ChunkPosition(x, y, z - 1);
			} else if (l == 4) {
				chunkpos = new ChunkPosition(x, y, z + 1);
			} else {
				this.dropBlockAsItem(world, x, y, z,
						world.getBlockMetadata(x, y, z), 0);
				world.setBlockWithNotify(x, y, z, 0);
			}

			if (chunkpos == null) {
				return false;
			} else if (world.getBlockId(chunkpos.x, chunkpos.y, chunkpos.z) == ElevatorsCore.ElevatorCaller.blockID) {
				boolean var16 = BlockElevatorCaller.findAndActivateElevator(
						world, chunkpos.x, chunkpos.y, chunkpos.z, 0);

				if (!world.isRemote && var16) {
					entityplayer.addChatMessage(ElevatorsCore.messages[0]);
				} else if (!world.isRemote) {
					entityplayer.addChatMessage(ElevatorsCore.messages[1]);
				}

				return true;
			} else {
				this.checkedBlocks.clear();
				this.elvs.clear();
				this.checkForElevators(world,
						new ChunkPosition(x, y, z), 0);
				this.checkForElevators(world, chunkpos, 0);
				ElevatorsCore.say("Checked " + this.checkedBlocks.size()
						+ " blocks");
				ElevatorsCore.say("Found " + this.elvs.size() + " elevators");
				chunkpos = null;
				int absoluteFloor = ElevatorsCore.max_elevator_Y + 5;
				int closestFloor = -1;

				if (!this.elvs.isEmpty()) {
					Iterator elevators = this.elvs.iterator();

					while (elevators.hasNext()) {
						ChunkPosition elevatorPos = (ChunkPosition) elevators.next();
						BlockElevator.refreshAndCombineAllAdjacentElevators(
								world, elevatorPos);
						TileEntityElevator tileentityelevator = BlockElevator.getTileEntity(
								world, elevatorPos.x, elevatorPos.y, elevatorPos.z);

						if (tileentityelevator != null) {
							int closestFloorFromY = tileentityelevator
									.getClosestFloorFromYCoor_AlwaysDown(y);
							closestFloorFromY = tileentityelevator.getYFromFloor(closestFloorFromY);
							ElevatorsCore.say("closest y: " + closestFloorFromY);

							if (MathHelper.abs((closestFloorFromY - elevatorPos.y)) < absoluteFloor
									&& tileentityelevator.hasFloorAt(closestFloorFromY)) {
								absoluteFloor = (int) MathHelper
										.abs((closestFloorFromY - elevatorPos.y));
								chunkpos = elevatorPos;
								closestFloor = closestFloorFromY;
							}
						}
					}
				}

				if (chunkpos != null) {
					if (!world.isRemote && closestFloor != chunkpos.y) {
						entityplayer.addChatMessage(ElevatorsCore.messages[0]);
					}

					ElevatorsCore.elevator_demandY(world, chunkpos, closestFloor);
				} else if (!world.isRemote) {
					entityplayer.addChatMessage(ElevatorsCore.messages[1]);
				}

				return true;
			}
		}
	}

	public void checkForElevators(World world, ChunkPosition chunkpos, int elevators) {
		if (!this.checkedBlocks.contains(chunkpos)) {
			this.checkedBlocks.add(chunkpos);
			boolean isElevator = false;

			if (world.getBlockId(chunkpos.x, chunkpos.y, chunkpos.z) == ElevatorsCore.Elevator.blockID) {
				if (!BlockElevator.isCeiling(world, chunkpos)) {
					this.elvs.add(chunkpos);
					return;
				}

				isElevator = true;
			}

			if (!isElevator && !ElevatorsCore.isBlockOpeningMaterial(world, chunkpos)) {
				++elevators;

				if (elevators > 2) {
					return;
				}

				for (int var5 = 0; var5 < 4; ++var5) {
					int var6 = chunkpos.x;
					int var7 = chunkpos.z;

					if (var5 == 0) {
						--var7;
					} else if (var5 == 1) {
						++var7;
					} else if (var5 == 2) {
						--var6;
					} else if (var5 == 3) {
						++var6;
					}

					ChunkPosition var8 = new ChunkPosition(var6, chunkpos.y, var7);
					this.checkForElevators(world, var8, elevators);
				}
			} else {
				if (chunkpos.y > 0
						&& !ElevatorsCore.isBlockLedgeMaterial(world, chunkpos.x,
								chunkpos.y - 1, chunkpos.z)) {
					this.checkForElevators(world, new ChunkPosition(chunkpos.x,
							chunkpos.y - 1, chunkpos.z), elevators);
				}

				if (chunkpos.y < ElevatorsCore.max_elevator_Y
						&& !ElevatorsCore.isBlockLedgeMaterial(world, chunkpos.x,
								chunkpos.y + 1, chunkpos.z)) {
					this.checkForElevators(world, new ChunkPosition(chunkpos.x,
							chunkpos.y + 1, chunkpos.z), elevators);
				}
			}
		}
	}
}
