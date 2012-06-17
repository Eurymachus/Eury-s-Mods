package net.minecraft.src;

import net.minecraft.src.MultiTexturedSigns.MTSCore;
import net.minecraft.src.forge.NetworkMod;

public class mod_MultiTexturedSigns extends NetworkMod {
	@Override
	public String getVersion() {
		return MTSCore.version;
	}

	public static NetworkMod instance;

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_EurysMods")) {
			instance = this;
			MTSCore.initialize();
		} else {
			ModLoader.getLogger().warning(
					"[MultiTexturedSigns] requires EurysMods-Core to work.");
		}
	}

	public mod_MultiTexturedSigns() {
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