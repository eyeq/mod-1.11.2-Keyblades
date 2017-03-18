package eyeq.keyblades.item;

import eyeq.keyblades.Keyblades;
import eyeq.util.item.IItemHasReach;
import eyeq.util.item.UItemSword;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemKeyblade extends UItemSword implements IItemHasReach {
    private float reach = 3.0F;

    public ItemKeyblade(ToolMaterial material) {
        super(material);
        this.setCreativeTab(Keyblades.TAB_KEYBLADES);
    }

    public ItemKeyblade setReach(float reach) {
        this.reach = reach;
        return this;
    }

    @Override
    public float getReach() {
        return reach;
    }

    @Override
    public float getReach(World world, EntityLivingBase entity, ItemStack itemStack) {
        return getReach();
    }
}
