package gunn.modcurrency.handler;

import gunn.modcurrency.containers.ContainerVendor;
import gunn.modcurrency.guis.GuiVendor;
import gunn.modcurrency.tiles.TileVendor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * This class was created by <Brady Gunn>.
 * Distributed with the Currency-Mod for Minecraft.
 *
 * The Currency-Mod is open source and distributed
 * under the General Public License
 *
 * File Created on 2016-11-02.
 */
public class GuiHandler implements IGuiHandler{
    //Id 30 = BlockVendor [Closed]

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos xyz = new BlockPos(x,y,z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if(tileEntity instanceof TileVendor && ID == 30){
            TileVendor tilevendor = (TileVendor) tileEntity;
            return new ContainerVendor(player.inventory, tilevendor);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos xyz = new BlockPos(x,y,z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if(tileEntity instanceof TileVendor && ID == 30){
            TileVendor tilevendor = (TileVendor) tileEntity;
            return new GuiVendor(player.inventory, tilevendor);
        }
        return null;
    }
}