package MultiTexturedSigns;

import EurysMods.EurysCore;
import EurysMods.ServerCore;
import EurysMods.ServerProxy;
import EurysMods.core.ICore;
import MultiTexturedSigns.network.PacketHandles;
import MultiTexturedSigns.network.PacketOpenGui;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ModLoader;

public class MultiTexturedSigns
{
    public static String minecraftDir = EurysCore.getMinecraftDir();
    public static ICore MTS;
    private static boolean initialized = false;

    public static void displaymtsGuiEditSign(EntityHuman var0, TileEntityMTSign var1)
    {
        EntityPlayer var2 = (EntityPlayer)var0;
        PacketOpenGui var3 = new PacketOpenGui(var1.x, var1.y, var1.z);
        var2.netServerHandler.networkManager.queue(var3.getPacket());
    }

    public static void initialize()
    {
        if (!initialized)
        {
            initialized = true;
            MTS = new ServerCore(new ServerProxy(), new PacketHandles());
            MTS.setModName("MultiTexturedSigns");
            MTS.setModChannel("MTS");
            load();
        }
    }

    public static void load()
    {
        EurysCore.console(MTS.getModName(), "Registering items...");
        ModLoader.registerTileEntity(TileEntityMTSign.class, "mtSign");
        MTSCore.addItems();
        EurysCore.console(MTS.getModName(), "Naming items...");
        MTSCore.addNames();
        EurysCore.console(MTS.getModName(), "Registering recipes...");
        MTSCore.addRecipes();
    }
}
