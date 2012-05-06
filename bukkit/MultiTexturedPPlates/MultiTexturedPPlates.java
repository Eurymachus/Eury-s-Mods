package MultiTexturedPPlates;

import EurysMods.EurysCore;
import EurysMods.ServerCore;
import EurysMods.ServerProxy;
import EurysMods.core.ICore;
import MultiTexturedPPlates.network.PacketHandles;
import net.minecraft.server.ModLoader;

public class MultiTexturedPPlates
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
            Core.setModName("MultiTexturedPlates");
            Core.setModChannel("MTP");
            load();
        }
    }

    public static void load()
    {
        EurysCore.console(Core.getModName(), "Registering items...");
        ModLoader.registerTileEntity(TileEntityMTPPlate.class, "mtPPlate");
        MTPCore.addItems();
        EurysCore.console(Core.getModName(), "Naming items...");
        MTPCore.addNames();
        EurysCore.console(Core.getModName(), "Registering recipes...");
        MTPCore.addRecipes();
    }
}
