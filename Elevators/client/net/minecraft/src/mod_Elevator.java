package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Elevators.ElevatorsCore;
import net.minecraft.src.forge.NetworkMod;

public class mod_Elevator extends NetworkMod {
	public static NetworkMod instance;

	public String getVersion() {
		return ElevatorsCore.version;
	}

	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_MinecraftForge")) {
			ElevatorsCore.initialize();
		}
	}

	public mod_Elevator() {
		instance = this;
	}
	
	@Override
	public String getPriorities() {
		return "after:mod_MinecraftForge;after:mod_EurysMods";
	}

	@Override
	public boolean onTickInGame(float tick, Minecraft mc) {
		if (mc.currentScreen == null) {
			ElevatorsCore.enableAdding = true;
		}
		return true;
	}

	@Override
	public boolean onTickInGUI(float tick, Minecraft mc, GuiScreen screen) {
		if (screen instanceof GuiContainerCreative && ElevatorsCore.enableAdding
				&& !mc.theWorld.isRemote) {
			ElevatorsCore.say("Attempting to add stuff!");
			((ContainerCreative) ((GuiContainer) screen).inventorySlots).itemList
					.addAll(ElevatorsCore.newBlocks);
			ElevatorsCore.enableAdding = false;
		}

		return true;
	}

	@Override
	public void load() {
	}
}
