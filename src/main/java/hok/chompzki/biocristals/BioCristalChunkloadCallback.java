package hok.chompzki.biocristals;

import hok.chompzki.biocristals.croot.BlockCore;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class BioCristalChunkloadCallback implements OrderedLoadingCallback {

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
		for (Ticket ticket : tickets) {
			int coreX = ticket.getModData().getInteger("coreX");
			int coreY = ticket.getModData().getInteger("coreY");
			int coreZ = ticket.getModData().getInteger("coreZ");
			//TileEntityMainframeCore entity = (TileEntityMainframeCore) world.getTileEntity(coreX, coreY, coreZ);
			//entity.forceChunkLoading(ticket);
		}
	}

	@Override
	public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world,
			int maxTicketCount) {
		List<Ticket> validTickets = new ArrayList();
		for (Ticket ticket : tickets) {
			int coreX = ticket.getModData().getInteger("coreX");
			int coreY = ticket.getModData().getInteger("coreY");
			int coreZ = ticket.getModData().getInteger("coreZ");
			Block block = world.getBlock(coreX, coreY, coreZ);
			if (block instanceof BlockCore)
				validTickets.add(ticket);
		}
		return validTickets;
	}

}
