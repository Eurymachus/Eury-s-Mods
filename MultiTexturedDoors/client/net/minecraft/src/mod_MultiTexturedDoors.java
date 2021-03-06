package net.minecraft.src;

import net.minecraft.src.MultiTexturedDoors.MTDCore;
import net.minecraft.src.MultiTexturedDoors.RenderMTDoor;
import net.minecraft.src.forge.NetworkMod;

public class mod_MultiTexturedDoors extends NetworkMod {
	@Override
	public String getVersion() {
		return MTDCore.version;
	}

	public static NetworkMod instance;

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_EurysMods")) {
			instance = this;
			MTDCore.initialize();
		} else {
			ModLoader.getLogger().warning(
					"[MultiTexturedButtons] requires EurysMods-Core to work.");
		}
	}

	public mod_MultiTexturedDoors() {
	}

	@Override
	public void load() {
	}

	@Override
	public boolean renderWorldBlock(RenderBlocks renderblocks,
			IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) {
		return RenderMTDoor.renderWorldBlock(renderblocks, iblockaccess, i, j,
				k, block, l);
	}

	@Override
	public boolean clientSideRequired() {
		return true;
	}

	@Override
	public boolean serverSideRequired() {
		return false;
	}
}