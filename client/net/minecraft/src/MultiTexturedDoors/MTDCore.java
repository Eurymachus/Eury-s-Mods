package net.minecraft.src.MultiTexturedDoors;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;


public class MTDCore
{
	public static String version = "v1.0";
	
	public static void initialize()
    {
		new MultiTexturedDoors().initialize();
    }
}
