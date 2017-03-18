package eyeq.keyblades.network;

import eyeq.keyblades.Keyblades;
import eyeq.keyblades.client.gui.inventory.GuiSynthesis;
import eyeq.keyblades.inventory.ContainerSynthesis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class KeybladesGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Keyblades.GUI_ID) {
            return new ContainerSynthesis(player.inventory, world, new BlockPos(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Keyblades.GUI_ID) {
            return new GuiSynthesis(player.inventory, world, new BlockPos(x, y, z));
        }
        return null;
    }
}
