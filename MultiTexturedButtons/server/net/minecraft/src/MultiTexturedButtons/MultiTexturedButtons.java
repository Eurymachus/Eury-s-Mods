package net.minecraft.src.MultiTexturedButtons;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedButtons.network.PacketHandles;

public class MultiTexturedButtons {
	public static String minecraftDir = EurysCore.getMinecraftDir();
	public static ICore Core;

	private static boolean initialized = false;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ServerCore(new ServerProxy(), new PacketHandles());
		Core.setModName("MultiTexturedButtons");
		Core.setModChannel("MTB");
		load();
	}

	public static void load() {
		EurysCore.console(Core.getModName(), "Registering items...");
		ModLoader.registerTileEntity(TileEntityMTButton.class, "mtButton");
		MTBCore.addItems();
		EurysCore.console(Core.getModName(), "Naming items...");
		MTBCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		MTBCore.addRecipes();
	}

	public static int getDamageValue(IBlockAccess blockAccess, int x,
			int y, int z) {
		TileEntity tileentity = blockAccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTButton) {
			TileEntityMTButton tileentitymtbutton = (TileEntityMTButton) tileentity;
			return tileentitymtbutton.getMetaValue();
		}
		return 0;
	}

	public static int getTextureFromMetaData(int par2) {
		return 0;
	}
}
