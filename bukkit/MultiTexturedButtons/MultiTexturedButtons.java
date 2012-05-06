package MultiTexturedButtons;

import EurysMods.EurysCore;
import EurysMods.ServerCore;
import EurysMods.ServerProxy;
import EurysMods.core.ICore;
import MultiTexturedButtons.network.PacketHandles;
import net.minecraft.server.ModLoader;

public class MultiTexturedButtons
{
    public static String minecraftDir = EurysCore.getMinecraftDir();
    public static ICore Core;
    private static boolean initialized = false;

    public static void initialize()
    {
        if (!initialized)
        {
            initialized = true;
            Core = new ServerCore(new ServerProxy(), new PacketHandles());
            Core.setModName("MultiTexturedButtons");
            Core.setModChannel("MTB");
            load();
        }
    }

    public static void load()
    {
        EurysCore.console(Core.getModName(), "Registering items...");
        ModLoader.registerTileEntity(TileEntityMTButton.class, "mtButton");
        MTBCore.addItems();
        EurysCore.console(Core.getModName(), "Naming items...");
        MTBCore.addNames();
        EurysCore.console(Core.getModName(), "Registering recipes...");
        MTBCore.addRecipes();
    }
}
