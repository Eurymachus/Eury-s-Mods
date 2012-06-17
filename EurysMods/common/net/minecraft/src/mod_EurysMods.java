package net.minecraft.src;

import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.forge.NetworkMod;

public class mod_EurysMods extends NetworkMod {
	@Override
	public String getVersion() {
		return EurysCore.version;
	}

	public static mod_EurysMods instance;

	@Override
	public void modsLoaded() {
		instance = this;
		EurysCore.initialize();
	}

	public mod_EurysMods() {
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