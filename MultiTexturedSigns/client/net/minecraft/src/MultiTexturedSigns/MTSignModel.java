package net.minecraft.src.MultiTexturedSigns;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class MTSignModel extends ModelBase
{
    public ModelRenderer mtsSignBoard;
    public ModelRenderer mtsSignStick;

    public MTSignModel()
    {
        mtsSignBoard = new ModelRenderer(this, 0, 0);
        mtsSignBoard.addBox(-12F, -14F, -1F, 24, 12, 2, 0.0F);
        mtsSignStick = new ModelRenderer(this, 0, 14);
        mtsSignStick.addBox(-1F, -2F, -1F, 2, 14, 2, 0.0F);
    }

    public void renderSign()
    {
        mtsSignBoard.render(0.0625F);
        mtsSignStick.render(0.0625F);
    }
}
