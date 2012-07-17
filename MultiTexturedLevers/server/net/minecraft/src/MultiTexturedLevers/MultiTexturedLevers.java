package net.minecraft.src.MultiTexturedLevers;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedLevers.network.PacketHandles;

public class MultiTexturedLevers {
	public static String minecraftDir = EurysCore.getMinecraftDir();
	public static ICore Core;
	private static boolean initialized = false;
	public static int mtLeverBlockRenderID;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ServerCore(new ServerProxy(), new PacketHandles());
		Core.setModName("MultiTexturedLevers");
		Core.setModChannel("MTL");
		load();
	}

	public static void load() {
		EurysCore.console(Core.getModName(), "Registering items...");
		ModLoader.registerTileEntity(TileEntityMTLever.class, "mtLever");
		MTLCore.addItems();
		EurysCore.console(Core.getModName(), "Naming items...");
		MTLCore.addNames();
		EurysCore.console(Core.getModName(), "Registering recipes...");
		MTLCore.addRecipes();
	}

	public static int getDamageValue(IBlockAccess blockaccess, int x, int y,
			int z) {
		TileEntity tileentity = blockaccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTLever) {
			TileEntityMTLever tileentitymtlever = (TileEntityMTLever) tileentity;
			return tileentitymtlever.getMetaValue();
		}
		return 0;
	}

	public static int getMouseOver() {
		return 0;
	}

	public static int getBelowPlayer(EntityPlayer player) {
		return 0;
	}

	public static int getAtPlayer(EntityPlayer player) {
		return 0;
	}

	public static EntityPlayer getPlayer() {
		return null;
	}
}
