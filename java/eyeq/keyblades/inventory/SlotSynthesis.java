package eyeq.keyblades.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SlotSynthesis extends Slot {
    private final List<IInventory> inputs;

    public SlotSynthesis(IInventory inventory, int index, int xPosition, int yPosition, List<IInventory> inputs) {
        super(inventory, index, xPosition, yPosition);
        this.inputs = inputs;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack itemStack) {
        for(IInventory input : inputs) {
            for(int i = 0; i < input.getSizeInventory(); i++) {
                ItemStack in = input.getStackInSlot(i);
                if(!in.isEmpty()) {
                    input.decrStackSize(i, 1);
                }
            }
        }
        return super.onTake(player, itemStack);
    }
}
