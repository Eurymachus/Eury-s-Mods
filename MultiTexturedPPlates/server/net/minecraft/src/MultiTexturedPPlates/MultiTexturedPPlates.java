package net.minecraft.src.MultiTexturedPPlates;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedPPlates.network.PacketHandles;

public class MultiTexturedPPlates {
	public static String minecraftDir = EurysCore.getMinecraftDir();
	public static ICore Core;
	private static boolean initialized = false;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ServerCore(new ServerProxy(), new PacketHandles());
		Core.setModName("MultiTexturedPPlates");
		Core.setModChannel("MTP");
		load();
	}

	public static void load() {
		EurysCore.console(Core.getModName(), "Registering items...");
		ModLoader.registerTileEntity(TileEntityMTPPlate.class, "mtPPlate");
		MTPCore.addItems();
		EurysCore.console(Core.getModName(), "Naming items...");
		MTPCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		MTPCore.addRecipes();
	}

	public static int getDamageValue(IBlockAccess blockaccess, int x, int y,
			int z) {
		TileEntity tileentity = blockaccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTPPlate) {
			TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate) tileentity;
			return tileentitymtpplate.getTextureValue();
		}
		return 0;
	}

	public static int getTextureFromMetaData(int i) {
		return 0;
	}
}
