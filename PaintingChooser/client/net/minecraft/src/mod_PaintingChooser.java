package net.minecraft.src;

import java.util.Map;

import net.minecraft.src.PaintingChooser.EntityPaintings;
import net.minecraft.src.PaintingChooser.PChooserCore;
import net.minecraft.src.PaintingChooser.RenderPaintings;
import net.minecraft.src.forge.NetworkMod;

public class mod_PaintingChooser extends NetworkMod {
	@Override
	public String getVersion() {
		return PChooserCore.version;
	}

	public static NetworkMod instance;

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_EurysMods")) {
			PChooserCore.initialize();
		} else {
			ModLoader.getLogger().warning(
					"[PaintingChooser] requires EurysMods-Core to work.");
		}
	}

	public mod_PaintingChooser() {
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

	@Override
	public void addRenderer(Map rendermap) {
		rendermap.put(EntityPaintings.class, new RenderPaintings());
	}
}
