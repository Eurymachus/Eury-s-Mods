package EurysMods;

import EurysMods.core.ICore;
import java.net.URISyntaxException;
import java.util.Arrays;
import net.minecraft.server.ModLoader;

public class EurysCore
{
    public static String version = "v1.0";
    public static ICore EurysCore;
    private static boolean initialized = false;

    public static String getMinecraftDir()
    {
        try
        {
            String var0 = ModLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            return var0.substring(0, var0.lastIndexOf(47));
        }
        catch (URISyntaxException var1)
        {
            return null;
        }
    }

    public static void console(String var0, String var1, int var2)
    {
        switch (var2)
        {
            case 0:
                ModLoader.getLogger().fine("[" + var0 + "] " + var1);
                break;

            case 1:
                ModLoader.getLogger().warning("[" + var0 + "] " + var1);
                break;

            case 2:
                ModLoader.getLogger().severe("[" + var0 + "] " + var1);
                break;

            default:
                ModLoader.getLogger().fine("[" + var0 + "] " + var1);
        }
    }

    public static void console(String var0, String var1)
    {
        console(var0, var1, 0);
    }

    public static Object[] concat(Object[] var0, Object[] var1)
    {
        Object[] var2 = Arrays.copyOf(var0, var0.length + var1.length);
        System.arraycopy(var1, 0, var2, var0.length, var1.length);
        return var2;
    }

    public static int[] concat(int[] var0, int[] var1)
    {
        int[] var2 = Arrays.copyOf(var0, var0.length + var1.length);
        System.arraycopy(var1, 0, var2, var0.length, var1.length);
        return var2;
    }

    public static float[] concat(float[] var0, float[] var1)
    {
        float[] var2 = Arrays.copyOf(var0, var0.length + var1.length);
        System.arraycopy(var1, 0, var2, var0.length, var1.length);
        return var2;
    }

    public static void load() {}

    public static void initialize()
    {
        if (!initialized)
        {
            initialized = true;
            EurysCore = new ServerCore(new ServerProxy());
            EurysCore.setModName("EurysMods");
        }
    }
}
