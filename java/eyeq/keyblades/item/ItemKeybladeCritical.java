package eyeq.keyblades.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemKeybladeCritical extends ItemKeyblade {
    private final float criticalDamage;
    private final float criticalProbability;

    public ItemKeybladeCritical(ToolMaterial material, float damage, float probability) {
        super(material);
        this.criticalDamage = damage;
        this.criticalProbability = probability;
    }

    @Override
    public float getDamageVsEntity() {
        return super.getDamageVsEntity() + (itemRand.nextFloat() < criticalProbability ? criticalDamage : 0);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if(equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 3.0F + this.getDamageVsEntity(), 0));
        }
        return multimap;
    }
}
