package net.minecraft.src.Elevators;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemElevator extends ItemBlock {
	public ItemElevator(int var1) {
		super(var1);
	}

	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. This is for ITEMS, not BLOCKS !
	 */
	public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3,
			int var4, int var5, int var6, int var7) {
		boolean var8 = false;

		if (var3.getBlockId(var4, var5, var6) == ElevatorsCore.Elevator.blockID) {
			var8 = true;
		}

		ElevatorsCore.say("Collect!", true);

		if (var3.getBlockId(var4, var5, var6) != Block.snow.blockID) {
			if (var7 == 0) {
				--var5;
			}

			if (var7 == 1) {
				++var5;
			}

			if (var7 == 2) {
				--var6;
			}

			if (var7 == 3) {
				++var6;
			}

			if (var7 == 4) {
				--var4;
			}

			if (var7 == 5) {
				++var4;
			}
		}

		if (var7 == 1 && var8) {
			if (!var3.isAirBlock(var4, var5 + 2, var6)) {
				return false;
			}

			--var1.stackSize;
			var3.setBlockAndMetadataWithNotify(var4, var5 + 2, var6,
					ElevatorsCore.Elevator.blockID, 1);
		} else if (!var8 || var8 && var7 != 0) {
			if (!var3.isAirBlock(var4, var5, var6)) {
				return false;
			}

			--var1.stackSize;
			var3.setBlockWithNotify(var4, var5, var6,
					ElevatorsCore.Elevator.blockID);
		}

		return true;
	}
}
