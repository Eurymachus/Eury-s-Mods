package net.minecraft.src.MultiTexturedBeds;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedBeds.network.PacketHandles;

public class MultiTexturedBeds {
	public static String minecraftDir = EurysCore.getMinecraftDir();
	public static int mtBedRenderID;
	public static ICore MTBed;
	private static boolean initialized = false;

	public static void initialize() {
		if (!initialized) {
			initialized = true;
			MTBed = new ServerCore(new ServerProxy(), new PacketHandles());
			MTBed.setModName("MultiTexturedBeds");
			MTBed.setModChannel("MTBed");
			load();
		}
	}

	public static void load() {
		EurysCore.console(MTBed.getModName(), "Registering items...");
		MTBedsCore.addItems();
		EurysCore.console(MTBed.getModName(), "Naming items...");
		MTBedsCore.addNames();
		EurysCore.console(MTBed.getModName(), "Registering recipes...");
		MTBedsCore.addRecipes();
	}

	public static int getDamageValue(IBlockAccess blockAccess, int x, int y,
			int z) {
		TileEntity tileentity = blockAccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTBed) {
			return ((TileEntityMTBed) tileentity).getMetaValue();
		}
		return 0;
	}
}
