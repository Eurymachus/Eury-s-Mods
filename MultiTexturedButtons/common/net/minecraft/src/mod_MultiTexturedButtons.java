package net.minecraft.src;

import net.minecraft.src.MultiTexturedButtons.MTBCore;
import net.minecraft.src.forge.NetworkMod;

public class mod_MultiTexturedButtons extends NetworkMod {
	@Override
	public String getVersion() {
		return MTBCore.version;
	}

	public static NetworkMod instance;

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_EurysMods")) {
			instance = this;
			MTBCore.initialize();
		} else {
			ModLoader.getLogger().warning(
					"[MultiTexturedButtons] requires EurysMods-Core to work.");
		}
	}

	public mod_MultiTexturedButtons() {
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