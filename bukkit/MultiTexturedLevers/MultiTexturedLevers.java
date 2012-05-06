package MultiTexturedLevers;

import EurysMods.EurysCore;
import EurysMods.ServerCore;
import EurysMods.ServerProxy;
import EurysMods.core.ICore;
import MultiTexturedLevers.network.PacketHandles;
import net.minecraft.server.ModLoader;

public class MultiTexturedLevers
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
            Core.setModName("MultiTexturedLevers");
            Core.setModChannel("MTL");
            load();
        }
    }

    public static void load()
    {
        EurysCore.console(Core.getModName(), "Registering items...");
        ModLoader.registerTileEntity(TileEntityMTLever.class, "mtLever");
        MTLCore.addItems();
        EurysCore.console(Core.getModName(), "Naming items...");
        MTLCore.addNames();
        EurysCore.console(Core.getModName(), "Registering recipes...");
        MTLCore.addRecipes();
    }
}
