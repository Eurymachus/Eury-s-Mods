package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;

public class MTSCore
{
	public static String version = "v3.0";

	public static void initialize()
	{
		new MultiTexturedSigns().initialize();
	}

	public static void displaymtsGuiEditSign(EntityPlayer entityplayer, TileEntityMTSign tileentitymtsign)
    {
        ModLoader.openGUI(entityplayer, handleGUI(tileentitymtsign));
    }

    public static GuiScreen handleGUI(TileEntityMTSign tileentitymtsign) 
    {
    	return new GuiEditMTSign(tileentitymtsign);
    }
}
