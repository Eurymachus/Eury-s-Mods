package net.minecraft.src;

import net.minecraft.src.MultiTexturedPPlates.MTPCore;
import net.minecraft.src.forge.NetworkMod;

public class mod_MultiTexturedPPlates extends NetworkMod {
	@Override
	public String getVersion() {
		return MTPCore.version;
	}

	public static NetworkMod instance;

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_EurysMods")) {
			instance = this;
			MTPCore.initialize();
		} else {
			ModLoader.getLogger().warning(
					"[MultiTexturedPPlates] requires EurysMods-Core to work.");
		}
	}

	public mod_MultiTexturedPPlates() {
	}

	@Override
	public void load() {
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