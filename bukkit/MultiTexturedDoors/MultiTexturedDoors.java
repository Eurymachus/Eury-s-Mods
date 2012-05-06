package MultiTexturedDoors;

import EurysMods.EurysCore;
import EurysMods.ServerCore;
import EurysMods.ServerProxy;
import EurysMods.core.ICore;
import MultiTexturedDoors.network.PacketHandles;
import net.minecraft.server.ModLoader;

public class MultiTexturedDoors
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
            Core.setModName("MultiTexturedDoors");
            Core.setModChannel("MTD");
            load();
        }
    }

    public static void load()
    {
        EurysCore.console(Core.getModName(), "Registering Items...");
        ModLoader.registerTileEntity(TileEntityMTDoor.class, "mtDoor");
        MTDCore.addItems();
        EurysCore.console(Core.getModName(), "Naming Items...");
        MTDCore.addNames();
        EurysCore.console(Core.getModName(), "Registering Recipes...");
        MTDCore.addRecipes();
    }
}
