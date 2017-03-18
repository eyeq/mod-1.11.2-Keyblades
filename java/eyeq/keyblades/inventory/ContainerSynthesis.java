package eyeq.keyblades.inventory;

import com.google.common.collect.Lists;
import eyeq.keyblades.Keyblades;
import eyeq.keyblades.item.ItemKeyblade;
import eyeq.keyblades.item.crafting.SynthesisManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerSynthesis extends Container {
    private final World world;
    private final BlockPos pos;

    public IInventory inputSlots = new InventoryCrafting(this, 1, 2);
    public IInventory outputSlot = new InventoryCraftResult();

    public ContainerSynthesis(InventoryPlayer playerInventory, World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;

        this.addSlotToContainer(new SlotStackLimitOne(this.inputSlots, 0, 56, 17));
        this.addSlotToContainer(new SlotStackLimitOne(this.inputSlots, 1, 56, 53));
        this.addSlotToContainer(new SlotSynthesis(this.outputSlot, 2, 116, 35, Lists.newArrayList(inputSlots)));
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(int k = 0; k < 9; k++) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        ItemStack result;
        ItemStack key = inputSlots.getStackInSlot(0);
        if(key.getItem() instanceof ItemKeyblade) {
            ItemStack chain = inputSlots.getStackInSlot(1);
            result = SynthesisManager.getInstance().getResult(chain).copy();
            if(!result.isEmpty()) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(key), result);
            }
        } else {
            result = ItemStack.EMPTY;
        }
        outputSlot.setInventorySlotContents(0, result);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        if(world.isRemote) {
            return;
        }
        for(int i = 0; i < 2; i++) {
            ItemStack itemStack = inputSlots.removeStackFromSlot(i);
            if(!itemStack.isEmpty()) {
                player.dropItem(itemStack, false);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if(world.getBlockState(pos).getBlock() != Keyblades.synthesis) {
            return false;
        }
        return player.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64.0;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = this.inventorySlots.get(index);
        if(slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack slotStack = slot.getStack();
        ItemStack copy = slotStack.copy();
        if(index == 0 || index == 1) {
            if(!this.mergeItemStack(slotStack, 3, 39, false)) {
                return ItemStack.EMPTY;
            }
        } else if(index == 2) {
            if(!this.mergeItemStack(slotStack, 3, 39, true)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChange(slotStack, copy);
        } else if(slotStack.getItem() instanceof ItemKeyblade) {
            if(!this.mergeItemStack(slotStack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if(!SynthesisManager.getInstance().getResult(slotStack).isEmpty()) {
            if(!this.mergeItemStack(slotStack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if(3 <= index && index < 30) {
            if(!this.mergeItemStack(slotStack, 30, 39, false)) {
                return ItemStack.EMPTY;
            }
        } else if(30 <= index && index < 39) {
            if(!this.mergeItemStack(slotStack, 3, 30, false)) {
                return ItemStack.EMPTY;
            }
        }

        if(slotStack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }
        if(slotStack.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
        }
        ItemStack take = slot.onTake(player, slotStack);
        if(index == 2) {
            player.dropItem(take, false);
        }
        return copy;
    }

    @Override
    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return slot.inventory != outputSlot && super.canMergeSlot(itemStack, slot);
    }
}
