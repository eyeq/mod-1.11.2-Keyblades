package eyeq.keyblades.event;

import eyeq.keyblades.Keyblades;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KeybladesEventHandler {
    @SubscribeEvent
    public void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        Entity target = event.getTarget();
        String name = EntityList.getEntityString(target);
        if(name == null || !name.toLowerCase().contains("heartless")) {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        EnumHand hand = event.getHand();
        if(player.getHeldItem(hand).getItem() == Items.WOODEN_SWORD) {
            player.setHeldItem(hand, new ItemStack(Keyblades.kingdomKey));
        }
    }
}
