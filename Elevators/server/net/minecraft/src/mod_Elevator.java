package net.minecraft.src;

import net.minecraft.src.Elevators.ElevatorsCore;
import net.minecraft.src.forge.NetworkMod;

public class mod_Elevator extends NetworkMod {
	public static NetworkMod instance;

	@Override
	public String getVersion() {
		return ElevatorsCore.version;
	}

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_MinecraftForge") && ModLoader.isModLoaded("mod_EurysMods")) {
			ElevatorsCore.initialize();
		}
	}
	
	@Override
	public String getPriorities() {
		return "after:mod_MinecraftForge;after:mod_EurysMods";
	}

	public mod_Elevator() {
		instance = this;
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
