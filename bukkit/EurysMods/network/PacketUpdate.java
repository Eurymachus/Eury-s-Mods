package EurysMods.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class PacketUpdate extends EurysPacket
{
    private int packetId;
    public PacketPayload payload;

    public PacketUpdate() {}

    public PacketUpdate(int var1, PacketPayload var2)
    {
        this(var1);
        this.payload = var2;
    }

    public PacketUpdate(int var1)
    {
        this.packetId = var1;
        this.isChunkDataPacket = true;
    }

    public void writeData(DataOutputStream var1) throws IOException
    {
        var1.writeInt(this.xPosition);
        var1.writeInt(this.yPosition);
        var1.writeInt(this.zPosition);

        if (this.payload == null)
        {
            var1.writeInt(0);
            var1.writeInt(0);
            var1.writeInt(0);
        }
        else
        {
            var1.writeInt(this.payload.intPayload.length);
            var1.writeInt(this.payload.floatPayload.length);
            var1.writeInt(this.payload.stringPayload.length);
            int[] var2 = this.payload.intPayload;
            int var3 = var2.length;
            int var4;

            for (var4 = 0; var4 < var3; ++var4)
            {
                int var5 = var2[var4];
                var1.writeInt(var5);
            }

            float[] var6 = this.payload.floatPayload;
            var3 = var6.length;

            for (var4 = 0; var4 < var3; ++var4)
            {
                float var8 = var6[var4];
                var1.writeFloat(var8);
            }

            String[] var7 = this.payload.stringPayload;
            var3 = var7.length;

            for (var4 = 0; var4 < var3; ++var4)
            {
                String var9 = var7[var4];
                var1.writeUTF(var9);
            }
        }
    }

    public void readData(DataInputStream var1) throws IOException
    {
        this.xPosition = var1.readInt();
        this.yPosition = var1.readInt();
        this.zPosition = var1.readInt();
        this.payload = new PacketPayload();
        this.payload.intPayload = new int[var1.readInt()];
        this.payload.floatPayload = new float[var1.readInt()];
        this.payload.stringPayload = new String[var1.readInt()];
        int var2;

        for (var2 = 0; var2 < this.payload.intPayload.length; ++var2)
        {
            this.payload.intPayload[var2] = var1.readInt();
        }

        for (var2 = 0; var2 < this.payload.floatPayload.length; ++var2)
        {
            this.payload.floatPayload[var2] = var1.readFloat();
        }

        for (var2 = 0; var2 < this.payload.stringPayload.length; ++var2)
        {
            this.payload.stringPayload[var2] = var1.readUTF();
        }
    }

    public boolean targetExists(World var1)
    {
        return var1.isLoaded(this.xPosition, this.yPosition, this.zPosition);
    }

    public TileEntity getTarget(World var1)
    {
        return var1.getTileEntity(this.xPosition, this.yPosition, this.zPosition);
    }

    public int getID()
    {
        return this.packetId;
    }
}
