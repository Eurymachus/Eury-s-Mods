package net.minecraft.src.MultiTexturedLevers;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_MultiTexturedLevers;
import net.minecraft.src.EurysMods.ClientCore;
import net.minecraft.src.EurysMods.ClientProxy;
import net.minecraft.src.EurysMods.EurysCore;
import net.minecraft.src.EurysMods.core.ICore;
import net.minecraft.src.MultiTexturedLevers.network.PacketHandles;
import net.minecraft.src.forge.MinecraftForgeClient;

public class MultiTexturedLevers {
	public static String minecraftDir = Minecraft.getMinecraftDir().toString();
	private static Minecraft mc = ModLoader.getMinecraftInstance();
	public static int mtLeverBlockRenderID;
	public static ICore Core;
	private static boolean initialized = false;

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;
		Core = new ClientCore(new ClientProxy(), new PacketHandles());
		Core.setModName("MultiTexturedLevers");
		Core.setModChannel("MTL");
		load();
	}

	public static void load() {
		MinecraftForgeClient.preloadTexture(Core.getItemSheet());
		mtLeverBlockRenderID = ModLoader.getUniqueBlockModelID(
				mod_MultiTexturedLevers.instance, true);
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
		if (mc.objectMouseOver != null) {
			int xPosition = mc.objectMouseOver.blockX;
			int yPosition = mc.objectMouseOver.blockY;
			int zPosition = mc.objectMouseOver.blockZ;
			return getDamageValue(mc.theWorld, xPosition, yPosition, zPosition);
		}
		return 0;
	}

	public static int getBelowPlayer(EntityPlayer player) {
		int playerX = (int) player.posX;
		int playerY = (int) player.posY;
		int playerZ = (int) player.posZ;
		return getDamageValue(mc.theWorld, playerX, playerY - 1, playerZ);
	}

	public static int getAtPlayer(EntityPlayer player) {
		int playerX = (int) player.posX;
		int playerY = (int) player.posY;
		int playerZ = (int) player.posZ;
		return getDamageValue(mc.theWorld, playerX, playerY, playerZ);
	}

	public static EntityPlayer getPlayer() {
		return mc.thePlayer;
	}
}
