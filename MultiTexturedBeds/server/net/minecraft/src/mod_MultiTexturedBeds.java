package net.minecraft.src;

import net.minecraft.src.MultiTexturedBeds.MTBedsCore;
import net.minecraft.src.forge.NetworkMod;

public class mod_MultiTexturedBeds extends NetworkMod {
	@Override
	public String getVersion() {
		return MTBedsCore.version;
	}

	public static NetworkMod instance;

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_EurysMods")) {
			instance = this;
			MTBedsCore.initialize();
		} else {
			ModLoader.getLogger().warning(
					"[MultiTexturedBeds] requires EurysMods-Core to work.");
		}
	}

	public mod_MultiTexturedBeds() {
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