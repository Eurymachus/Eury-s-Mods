package net.minecraft.src.Elevators;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.Elevators.network.PacketUpdateRiders;

public class EntityElevator extends Entity {
	private static final int blockID = ElevatorsCore.Elevator.blockID;
	private byte stillcount = 0;
	private byte waitToAccelerate = 0;
	private int dest;
	private float destY;
	private boolean atDestination;
	boolean unUpdated;
	private float elevatorSpeed = 0.0F;
	private static final float elevatorAccel = 0.01F;
	private static final float maxElevatorSpeed = 0.4F;
	private static final float minElevatorMovingSpeed = 0.016F;
	public Set mountedEntities;
	private String[] namedFloors;
	private boolean[] properties;
	public boolean emerHalt = false;
	public int startStops = 0;
	public int tickcount = 0;
	public int metadata = 0;
	public EntityElevator ceiling = null;
	public EntityElevator floor = null;
	public EntityElevator centerElevator = null;
	public Set conjoinedelevators = new HashSet();
	public boolean center = false;
	private boolean conjoinedHasBeenSet = false;
	private boolean isClient;
	boolean slowingDown = false;

	public EntityElevator(World var1) {
		super(var1);
		this.atDestination = false;
		this.unUpdated = true;
		this.mountedEntities = new HashSet();
		this.riddenByEntity = null;
		this.waitToAccelerate = 100;
		this.centerElevator = this;
		this.ridingEntity = null;
		this.conjoinedelevators.add(this);
	}

	public EntityElevator(World var1, double var2, double var4, double var6) {
		super(var1);
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = var2;
		this.prevPosY = var4;
		this.prevPosZ = var6;
		this.say("Elevator created at " + var2 + ", " + var4 + ", " + var6);
		this.setPosition(this.prevPosX, this.prevPosY, this.prevPosZ, true);
		this.dest = (int) var4;
		this.destY = (float) var4 + 0.5F;
		this.atDestination = false;
		this.unUpdated = true;
		this.isClient = false;
		this.mountedEntities = new HashSet();
		this.riddenByEntity = null;
	}

	public EntityElevator(World var1, ChunkPosition var2, int var3,
			boolean var4, boolean var5, int var6) {
		super(var1);
		this.say("Elevator created at " + var2.x + ", " + var2.y + ", "
				+ var2.z);
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = (var2.x + 0.5F);
		this.prevPosY = (var2.y + 0.5F);
		this.prevPosZ = (var2.z + 0.5F);
		this.setPosition(this.prevPosX, this.prevPosY, this.prevPosZ, true);
		this.dest = var3;
		this.destY = this.dest + 0.5F;
		this.atDestination = false;
		this.unUpdated = true;
		this.mountedEntities = new HashSet();
		this.riddenByEntity = null;
		this.isClient = var5;
		this.center = var4 || var5;
		this.metadata = var6;
	}

	public void updateRiderPosition() {
		if (!this.isDead) {
			int[] entityIDs = new int[this.mountedEntities.size()];
			float[] entityYCoords = new float[this.mountedEntities.size()];
			// Packet250CustomPayload var3 = new Packet250CustomPayload();
			// var3.packetType = 238;

			if (!this.mountedEntities.isEmpty() && !this.worldObj.isRemote) {
				this.mountedEntities.remove((Object) null);

				if (!this.mountedEntities.isEmpty()) {
					int i = 0;
					Iterator entities = this.mountedEntities.iterator();

					while (entities.hasNext()) {
						Entity entity = (Entity) entities.next();

						if (entity != null) {
							if (entity.ridingEntity == null) {
								entity.mountEntity(this);
							}

							entity.setPosition(entity.posX,
									this.posY + this.getMountedYOffset()
											+ entity.getYOffset(), entity.posZ);
							entityIDs[i] = entity.entityId;
							entityYCoords[i] = (float) entity.posY;
							++i;
						}
					}
				}
			}

			PacketUpdateRiders ridersPacket = new PacketUpdateRiders(
					"UPDATE_RIDERS", entityIDs, entityYCoords);
			// var3.dataInt = var1;
			// var3.dataFloat = var2;
			ElevatorsCore.sendRiderUpdates(ridersPacket, (int)this.posX, (int)this.posY, (int)this.posZ);
		}
	}

	private void ejectRiders() {
		if (!this.mountedEntities.isEmpty()) {
			int i = 0;
			int[] entityIDs = new int[this.mountedEntities.size()];
			float[] entityYCoords = new float[this.mountedEntities.size()];

			for (Iterator entities = this.mountedEntities.iterator(); entities
					.hasNext(); ++i) {
				Entity entity = (Entity) entities.next();
				entity.setPosition(entity.posX, this.posY
						+ entity.height + entity.getYOffset(),
						entity.posZ);
				entity.ridingEntity = null;
				entityIDs[i] = entity.entityId;
				entityYCoords[i] = (float) entity.posY;
				// var1.dataInt[var2] = var4.entityId;
				// var1.dataFloat[var2] = (float)var4.posY;
			}

			PacketUpdateRiders ridersPacket = new PacketUpdateRiders(
					"DISMOUNT_RIDERS", entityIDs, entityYCoords);
			// Packet250CustomPayload var1 = new Packet250CustomPayload();
			// var1.packetType = 239;
			// var1.dataInt = new int[this.mountedEntities.size()];
			// var1.dataFloat = new float[this.mountedEntities.size()];
			ElevatorsCore.sendRiderUpdates(ridersPacket, (int)this.posX, (int)this.posY, (int)this.posZ);
		}
	}

	private void updateAllConjoined() {
		Iterator elevators = this.conjoinedelevators.iterator();
		HashSet elevatorHash = new HashSet();
		elevatorHash.addAll(this.conjoinedelevators);

		while (elevators.hasNext()) {
			EntityElevator entityelevator = (EntityElevator) elevators.next();
			entityelevator.prevPosY = entityelevator.posY;
			entityelevator.boundingBox.offset(0.0D, this.motionY, 0.0D);
			entityelevator.setPosition(entityelevator.posX,
					entityelevator.boundingBox.minY
							+ entityelevator.yOffset
							- entityelevator.ySize,
					entityelevator.posZ, true);
			EntityElevator elevatorceiling = entityelevator.ceiling;

			if (elevatorceiling != null) {
				elevatorceiling.prevPosY = entityelevator.posY;
				elevatorceiling.boundingBox.offset(0.0D, this.motionY, 0.0D);
				elevatorceiling.setPosition(elevatorceiling.posX,
						elevatorceiling.boundingBox.minY
								+ elevatorceiling.yOffset
								- elevatorceiling.ySize,
						elevatorceiling.posZ, true);
				ElevatorsCore.say("" + elevatorceiling.metadata);
				elevatorHash.add(elevatorceiling);
			}
		}

		int[] entityIDs = new int[elevatorHash.size()];
		float[] entityYCoords = new float[elevatorHash.size()];
		double[] entitysData = new double[elevatorHash.size()];
		int i = 0;

		for (elevators = elevatorHash.iterator(); elevators.hasNext(); ++i) {
			EntityElevator elevator = (EntityElevator) elevators.next();
			entityIDs[i] = elevator.entityId;
			entityYCoords[i] = (float) elevator.posY;
			entitysData[i] = elevator.metadata;
			ElevatorsCore.say(elevator.entityId + ": " + elevator.metadata);
		}

		PacketUpdateRiders ridersPacket = new PacketUpdateRiders(
				"UPDATE_RIDERS", entityIDs, entityYCoords, entitysData);
		// Packet250CustomPayload var10 = new Packet250CustomPayload();
		// var10.packetType = 238;
		// var10.dataInt = var8;
		// var10.dataFloat = var9;
		// var10.dataDouble = var5;
		ElevatorsCore.sendRiderUpdates(ridersPacket, (int)this.posX, (int)this.posY, (int)this.posZ);
	}

	public void setEmerHalt(boolean var1) {
		if (this.properties.length <= 1 || this.properties[1] || !var1) {
			if (this.isClient) {
				this.emerHalt = var1;

				if (this.ceiling != null) {
					this.ceiling.setEmerHalt(this.emerHalt);
				}

				if (this.emerHalt && !this.mountedEntities.isEmpty()) {
					this.ejectRiders();
				}

				if (this.emerHalt) {
					this.motionY = 0.0D;
					this.elevatorSpeed = 0.0F;
				}

				if (!this.isCeiling()) {
					if (this.center) {
						Iterator var2 = this.conjoinedelevators.iterator();

						while (var2.hasNext()) {
							EntityElevator var3 = (EntityElevator) var2.next();

							if (var3 != this && var3.emerHalt != this.emerHalt) {
								var3.setEmerHalt(this.emerHalt);
							}
						}
					} else if (this.centerElevator.emerHalt != this.emerHalt) {
						this.centerElevator.setEmerHalt(this.emerHalt);
					}
				}
			}
		}
	}

	public boolean isCeiling() {
		return (this.metadata & 1) == 1;
	}

	public void refreshRiders() {
		if (!this.isCeiling()) {
			AxisAlignedBB var1 = this.getBoundingBox().expand(0.0D, 0.3D, 0.0D);
			var1.minY += 0.3D;
			HashSet var2 = new HashSet();
			var2.addAll(this.worldObj.getEntitiesWithinAABBExcludingEntity(
					this, var1));
			Iterator var3 = var2.iterator();

			while (var3.hasNext()) {
				Entity var4 = (Entity) var3.next();

				if (!this.mountedEntities.contains(var4)) {
					this.attemptMount(var4);
				}
			}
		}
	}

	public void attemptMount(Entity var1) {
		if (var1 != null && !(var1 instanceof EntityElevator)
				&& !this.collideEntity(var1) && var1.ridingEntity == null) {
			var1.mountEntity(this);
			this.mountedEntities.add(var1);
		}
	}

	public boolean collideEntity(Entity var1) {
		ElevatorsCore.say("Collision detected...");

		if (ElevatorsCore.killBelow && var1.posY < this.posY
				&& var1 instanceof EntityLiving) {
			EntityLiving var2 = (EntityLiving) var1;
			var2.attackEntityFrom(DamageSource.inWall, 50);
			ElevatorsCore.say("Damaging!");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
	 */
	public void mountEntity(Entity var1) {
	}

	public void setConjoined(Set var1) {
		this.conjoinedelevators.addAll(var1);
		this.conjoinedHasBeenSet = true;
	}

	/**
	 * Sets the x,y,z of the entity from the given parameters. Also seems to set
	 * up a bounding box.
	 */
	public void setPosition(double var1, double var3, double var5) {
		this.setPosition(var1, var3, var5, false);
	}

	public void setPosition(double var1, double var3, double var5, boolean var7) {
		if (var7) {
			super.setPosition(var1, var3, var5);
		}
	}

	public void updateRider(Entity var1) {
		if (var1 != null) {
			if (var1 instanceof EntityLiving) {
				this.say("Updating living rider #"
						+ var1.entityId
						+ " to "
						+ (this.posY + this.getMountedYOffset() + var1
								.getYOffset()));
				var1.setPosition(this.posX,
						this.centerElevator.posY + this.getMountedYOffset()
								+ var1.getYOffset(), this.posZ);
			} else if (!(var1 instanceof EntityElevator)) {
				this.say("Updating rider #"
						+ var1.entityId
						+ " to "
						+ (this.posY + this.getMountedYOffset() + var1
								.getYOffset()));
				var1.setPosition(var1.posX,
						this.centerElevator.posY + this.getMountedYOffset()
								+ var1.getYOffset(), var1.posZ);
			}
		}
	}

	/**
	 * Applies a velocity to each of the entities pushing them away from each
	 * other. Args: entity
	 */
	public void applyEntityCollision(Entity var1) {
		if (!this.isCeiling()) {
			this.attemptMount(var1);
		}
	}

	protected void entityInit() {
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return !this.isDead;
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		boolean var1 = true;
		int var2 = MathHelper.floor_double(this.posX);
		int var3 = MathHelper.floor_double(this.posY);
		int var4 = MathHelper.floor_double(this.posZ);
		++this.ticksExisted;
		++this.tickcount;

		if (this.tickcount > 45) {
			--this.startStops;

			if (this.startStops < 0) {
				this.startStops = 0;
			}

			this.tickcount = 0;
		}

		if (this.unUpdated) {
			if (this.worldObj.getBlockId(var2, var3, var4) == blockID) {
				if (!this.isCeiling()) {
					BlockElevator var5 = (BlockElevator) Block.blocksList[blockID];
					TileEntityElevator var6 = BlockElevator.getTileEntity(
							this.worldObj, var2, var3, var4);

					if (var6 != null) {
						this.namedFloors = var6.convertFloorMapToArray();
						this.properties = var6
								.convertBooleanPropertiesToArray();
					}
				}

				if (!this.isCeiling() && this.properties != null
						&& this.properties.length > 2 && this.properties[2]) {
					this.worldObj.setBlockWithNotify(var2, var3, var4,
							ElevatorsCore.Transient.blockID);
				} else {
					this.worldObj.setBlockWithNotify(var2, var3, var4, 0);
				}

				this.worldObj.notifyBlocksOfNeighborChange(var2 - 1, var3,
						var4, blockID);
				this.worldObj.notifyBlocksOfNeighborChange(var2 + 1, var3,
						var4, blockID);
				this.worldObj.notifyBlocksOfNeighborChange(var2, var3,
						var4 - 1, blockID);
				this.worldObj.notifyBlocksOfNeighborChange(var2, var3,
						var4 + 1, blockID);
			}

			this.conjoinAllNeighbors();
			this.unUpdated = false;
		}

		if (!this.emerHalt) {
			if (!this.isDead && !this.isCeiling()) {
				this.refreshRiders();

				if (this.properties != null && this.properties.length > 2
						&& this.properties[2]) {
					int var7;
					int var9;
					int var11;

					if (this.motionY > 0.0D) {
						var9 = (int) Math.ceil(this.posX - 0.5D);
						var11 = (int) Math.ceil(this.posY - 0.5D);
						var7 = (int) Math.ceil(this.posZ - 0.5D);
					} else {
						var9 = (int) Math.floor(this.posX - 0.5D);
						var11 = (int) Math.floor(this.posY - 0.5D);
						var7 = (int) Math.floor(this.posZ - 0.5D);
					}

					int var8 = this.worldObj.getBlockId(var9, var11, var7);

					if (var8 == 0) {
						this.worldObj.setBlockWithNotify(var9, var11, var7,
								ElevatorsCore.Transient.blockID);
					}
				}
			}

			if (this.worldObj.isRemote) {
				this.setPosition(this.posX, this.posY, this.posZ, true);
				this.say("Speed: " + this.motionY + ", posY: " + this.posY
						+ ", destY: " + this.destY);
				this.say("End Entity Update - Control is remote");
			} else if (!this.center) {
				if (this.centerElevator.isDead) {
					this.setDead();
				}

				this.say("End Entity Update - Not Center");
			} else {
				if (!this.conjoinedHasBeenSet) {
					this.conjoinAllNeighbors();
				}

				if (this.waitToAccelerate < 15) {
					if (this.waitToAccelerate < 10) {
						this.elevatorSpeed = 0.0F;
					} else {
						this.elevatorSpeed = 0.016F;
					}

					++this.waitToAccelerate;

					if (!this.conjoinedelevators.contains(this)) {
						this.conjoinedelevators.add(this);
					}

					this.say("Waiting to accelerate");
				} else {
					float var10 = this.elevatorSpeed + 0.01F;

					if (var10 > 0.4F) {
						var10 = 0.4F;
					}

					float var12 = (var10 * var10 - 2.5600003E-4F) / 0.02F + 0.125F;
					this.say("Speed: " + this.motionY + ", posY: " + this.posY
							+ ", destY: " + this.destY + ", range: " + var12);

					if (!this.slowingDown
							&& MathHelper
									.abs((float) (this.destY - this.posY)) >= var12) {
						this.elevatorSpeed = var10;
					} else {
						this.elevatorSpeed -= 0.01F;
						this.slowingDown = true;
					}

					if (this.elevatorSpeed > 0.4F) {
						this.elevatorSpeed = 0.4F;
					}

					if (this.elevatorSpeed < 0.016F) {
						this.elevatorSpeed = 0.016F;
					}
				}

				this.atDestination = this.onGround
						|| MathHelper
								.abs((float) (this.destY - this.posY)) < this.elevatorSpeed;

				if (this.destY < 1.0F
						|| this.destY > ElevatorsCore.max_elevator_Y) {
					this.atDestination = true;
					this.say("Requested destination is too high or too low!");
					this.say("Requested: " + this.destY + ", max: "
							+ ElevatorsCore.max_elevator_Y);
				}

				if (!this.atDestination) {
					this.motionY = this.destY > this.posY ? (double) this.elevatorSpeed
							: (double) (-this.elevatorSpeed);
					this.updateAllConjoined();
				} else if (this.atDestination) {
					this.killAllConjoined();
					return;
				}

				if (MathHelper.abs((float) this.motionY) < 0.016F
						&& this.stillcount++ > 10) {
					this.killAllConjoined();
				} else {
					this.stillcount = 0;
				}

				super.onUpdate();
				this.say("End Entity Update");
			}
		}
	}

	private void killAllConjoined() {
		Iterator var1 = this.conjoinedelevators.iterator();

		while (var1.hasNext()) {
			EntityElevator var2 = (EntityElevator) var1.next();
			var2.killElevator();

			if (var2.ceiling != null) {
				var2.ceiling.killElevator();
			}
		}
	}

	public void killElevator() {
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.posY);
		int var3 = MathHelper.floor_double(this.posZ);
		int var4 = MathHelper.floor_double(this.posY);

		if (!this.isCeiling() && this.namedFloors != null
				&& this.properties != null) {
			ElevatorsCore.checkedFloorNames.put(new ChunkPosition(var1, var4,
					var3), this.namedFloors);
			ElevatorsCore.checkedProperties.put(new ChunkPosition(var1, var4,
					var3), this.properties);
		}

		if (!this.worldObj.isRemote
				&& this.worldObj.getBlockId(var1, var4, var3) != blockID
				&& (!this.worldObj.canBlockBePlacedAt(blockID, var1, var4,
						var3, true, 1) || !this.worldObj.setBlockWithNotify(
						var1, var4, var3, blockID))) {
			this.dropItem(blockID, 1);
			this.setDead();
			this.say("Entity Dead - Destination blocked!");
		} else if (!this.worldObj.isRemote) {
			this.setDead();
			this.say("Entity Dead");
		}

		this.ejectRiders();
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource var1, int var2) {
		if (!this.worldObj.isRemote && !this.isDead) {
			if (!this.isCeiling()) {
				this.setEmerHalt(!this.emerHalt);
			} else if (this.floor != null) {
				this.floor.setEmerHalt(!this.floor.emerHalt);
			}

			++this.startStops;

			if (this.startStops > 2) {
				this.killElevator();
			}

			return true;
		} else {
			return true;
		}
	}

	public Set getNeighbors() {
		HashSet var1 = new HashSet();

		if (this.isCeiling()) {
			var1.add(this);
			return var1;
		} else {
			HashSet var2 = new HashSet();
			var2.addAll(this.worldObj.getEntitiesWithinAABBExcludingEntity(
					(Entity) null,
					this.getBoundingBox().expand(0.5D, 0.0D, 0.5D)));
			Iterator var3 = var2.iterator();

			while (var3.hasNext()) {
				Entity var4 = (Entity) var3.next();

				if (var4 instanceof EntityElevator) {
					var1.add(var4);
				}
			}

			return var1;
		}
	}

	public void conjoinAllNeighbors() {
		if (!this.isCeiling()) {
			this.conjoinedelevators.clear();
			HashSet var1 = new HashSet();
			this.conjoinedelevators.addAll(this.getNeighbors());
			var1.addAll(this.conjoinedelevators);
			var1.remove(this);
			Iterator var2;

			while (!var1.isEmpty()) {
				var2 = var1.iterator();
				HashSet var3 = new HashSet();

				while (var2.hasNext()) {
					EntityElevator var4 = (EntityElevator) var2.next();
					var3.addAll(var4.getNeighbors());
				}

				var1.clear();
				var3.removeAll(this.conjoinedelevators);
				this.conjoinedelevators.addAll(var3);
				var1.addAll(var3);
			}

			var2 = this.conjoinedelevators.iterator();

			while (var2.hasNext()) {
				EntityElevator var5 = (EntityElevator) var2.next();

				if (!var5.center && !this.isClient) {
					var5.center = false;
					var5.centerElevator = this;

					if (var5.ceiling != null) {
						var5.ceiling.centerElevator = this;
						var5.ceiling.conjoinedHasBeenSet = true;
						var5.ceiling.center = false;
					}
				} else {
					var5.setConjoined(this.conjoinedelevators);
					var5.centerElevator = var5;

					if (var5.ceiling != null) {
						var5.ceiling.centerElevator = var5;
						var5.ceiling.conjoinedHasBeenSet = true;
					}

					var5.center = true;
				}
			}

			this.conjoinedHasBeenSet = true;
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	protected void writeEntityToNBT(NBTTagCompound var1) {
		var1.setInteger("destY", this.dest);
		var1.setBoolean("emerHalt", this.emerHalt);
		var1.setBoolean("isClient", this.isClient);
		var1.setBoolean("isCenter", this.center);
		var1.setInteger("metadata", this.metadata);

		if (!this.isCeiling()) {
			int var2;

			for (var2 = 0; this.namedFloors != null
					&& var2 < this.namedFloors.length; ++var2) {
				var1.setString("x" + var2, this.namedFloors[var2]);
			}

			var1.setInteger("numNamed", this.namedFloors.length);

			for (var2 = 0; this.properties != null
					&& var2 < this.properties.length; ++var2) {
				var1.setBoolean("p" + var2, this.properties[var2]);
			}

			var1.setInteger("numProps", this.properties.length);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound var1) {
		try {
			this.dest = var1.getInteger("destY");
			this.metadata = var1.getInteger("metadata");
		} catch (Exception var5) {
			this.dest = var1.getByte("destY");
		}

		this.emerHalt = var1.getBoolean("emerHalt");
		this.isClient = var1.getBoolean("isClient");
		this.center = var1.getBoolean("isCenter");

		if (!this.conjoinedelevators.contains(this)) {
			this.conjoinedelevators.add(this);
		}

		this.destY = this.dest + 0.5F;
		this.setPosition(this.posX, this.posY, this.posZ, true);

		if (!this.isCeiling()) {
			int var2 = var1.getInteger("numNamed");
			int var3 = var1.getInteger("numProps");
			this.namedFloors = new String[var2];
			int var4;

			for (var4 = 0; var4 < var2; ++var4) {
				this.namedFloors[var4] = var1.getString("x" + var4);
			}

			this.properties = new boolean[var3];

			for (var4 = 0; var4 < var3; ++var4) {
				this.properties[var4] = var1.getBoolean("p" + var4);
			}
		}
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like boats or
	 * minecarts.
	 */
	public AxisAlignedBB getCollisionBox(Entity var1) {
		return var1.getBoundingBox();
	}

	/**
	 * returns the bounding box for this entity
	 */
	public AxisAlignedBB getBoundingBox() {
		AxisAlignedBB var10000 = this.boundingBox;
		return AxisAlignedBB.getBoundingBox(this.posX - 0.5D, this.posY - 0.5D,
				this.posZ - 0.5D, this.posX + 0.5D, this.posY + 0.5D,
				this.posZ + 0.5D);
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding
	 * this one.
	 */
	public double getMountedYOffset() {
		return 0.5D;
	}

	public float getShadowSize() {
		return 1.0F;
	}

	public World getWorld() {
		return this.worldObj;
	}

	private void say(String var1) {
		ElevatorsCore.say(" [ " + this.entityId + " ] " + var1);
	}
}
