package net.minecraft.src.MultiTexturedLevers;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import net.minecraft.src.mod_MultiTexturedLevers;
import net.minecraft.src.forge.MinecraftForge;

public class MTLCore
{
	public static String version = "v1.3";

	public static void initialize()
	{
       	new MultiTexturedLevers().initialize();
	}
}
