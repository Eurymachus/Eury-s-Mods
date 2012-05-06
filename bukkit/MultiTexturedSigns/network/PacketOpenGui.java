package MultiTexturedSigns.network;

public class PacketOpenGui extends PacketMTS
{
    public PacketOpenGui()
    {
        super(1);
    }

    public PacketOpenGui(int var1, int var2, int var3)
    {
        super(1);
        this.xPosition = var1;
        this.yPosition = var2;
        this.zPosition = var3;
    }
}
