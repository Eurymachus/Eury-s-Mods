package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.ServerCore;
import net.minecraft.src.EurysMods.ServerProxy;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedSigns.network.PacketHandles;
import net.minecraft.src.MultiTexturedSigns.network.PacketOpenGui;

public class MultiTexturedSigns {
	public static String minecraftDir = EurysCore.getMinecraftDir();
	public static ICore MTS;
	private static boolean initialized = false;

	public static void displaymtsGuiEditSign(EntityPlayer entityplayer,
			TileEntityMTSign tileentitymtsign) {
		EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
		PacketOpenGui gui = new PacketOpenGui(tileentitymtsign.xCoord,
				tileentitymtsign.yCoord, tileentitymtsign.zCoord);
		entityplayermp.playerNetServerHandler.netManager.addToSendQueue(gui
				.getPacket());
	}

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		MTS = new ServerCore(new ServerProxy(), new PacketHandles());
		MTS.setModName("MultiTexturedSigns");
		MTS.setModChannel("MTS");
		load();
	}

	public static void load() {
		EurysCore.console(MTS.getModName(), "Registering items...");
		ModLoader.registerTileEntity(TileEntityMTSign.class, "mtSign");
		MTSCore.addItems();
		EurysCore.console(MTS.getModName(), "Naming items...");
		MTSCore.addNames();
		EurysCore.console(MTS.getModName(), "Registering recipes...");
		MTSCore.addRecipes();
	}

	public static int getDamageValue(IBlockAccess blockAccess, int x, int y,
			int z) {
		TileEntity tileentity = blockAccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTSign) {
			TileEntityMTSign tileentitymtsign = (TileEntityMTSign) tileentity;
			return tileentitymtsign.getTextureValue();
		}
		return 0;
	}

	public static int getBlockTextureFromMetadata(int par2) {
		return 0;
	}
}