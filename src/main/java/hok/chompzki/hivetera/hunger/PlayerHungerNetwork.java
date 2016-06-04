package hok.chompzki.hivetera.hunger;

import hok.chompzki.hivetera.api.IToken;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.croot.power.WorldCoord;
import hok.chompzki.hivetera.hunger.logic.Bank;
import hok.chompzki.hivetera.hunger.logic.Eater;
import hok.chompzki.hivetera.hunger.logic.EnumResource;
import hok.chompzki.hivetera.hunger.logic.EnumToken;
import hok.chompzki.hivetera.hunger.logic.Feeder;
import hok.chompzki.hivetera.hunger.logic.Path;
import hok.chompzki.hivetera.hunger.logic.ResourcePackage;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.logic.settlement.TokenBank;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.UUID;

import org.lwjgl.util.Point;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerHungerNetwork implements IInventory{
	
	protected String name = null;
	
	protected ItemStack[] contents = new ItemStack[9*9+4*9];
	
	protected ArrayList<Bank> banks = new ArrayList<Bank>();
	protected ArrayList<Feeder> feeders = new ArrayList<Feeder>();
	protected ArrayList<Eater> eaters = new ArrayList<Eater>();
	
	
	transient private PlayerHungerStorage currentStorage = null;
	
	public PlayerHungerNetwork(String id) {
		name = id;
	}
	
	public void setStorage(PlayerHungerStorage stor){
		currentStorage = stor;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int getSizeInventory() {
		return 9*9+4*9;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		return this.contents[p_70301_1_];
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		if (this.contents[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.contents[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.contents[p_70298_1_];
                this.contents[p_70298_1_] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.contents[p_70298_1_].splitStack(p_70298_2_);

                if (this.contents[p_70298_1_].stackSize == 0)
                {
                    this.contents[p_70298_1_] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		if (this.contents[p_70304_1_] != null)
        {
            ItemStack itemstack = this.contents[p_70304_1_];
            this.contents[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		this.contents[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "container.hunger";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize <= 0)
				contents[i] = null;
		}
		
		updateHunger();
		
		currentStorage.activate(this);
	}
	
	public void updateHunger() {
		banks.clear();
		feeders.clear();
		eaters.clear();
		
		for(int x = 0; x < 9; x++)
		for(int y = 0; y < 9; y++){
			ItemStack stack = contents[x + y * 9];
			if(stack == null)
				continue;
			IToken token = (IToken)stack.getItem();
			if(token.getType(stack) == EnumToken.BANK){
				Bank bank = new Bank();
				bank.x = x;
				bank.y = y;
				bank.channel = token.getChannel(stack);
				bank.owner = token.getOwner(stack);
				banks.add(bank);
			}
		}
		
		for(int x = 0; x < 9; x++)
		for(int y = 0; y < 9; y++){
			ItemStack stack = contents[x + y * 9];
			if(stack == null)
				continue;
			IToken token = (IToken)stack.getItem();
			if(token.getType(stack) == EnumToken.FEEDER){
				Feeder feeder = new Feeder();
				feeder.x = x;
				feeder.y = y;
				feeder.channel = token.getChannel(stack);
				feeder.owner = token.getOwner(stack);
				feeders.add(feeder);
			}
		}
		
		for(int x = 0; x < 9; x++)
		for(int y = 0; y < 9; y++){
			ItemStack stack = contents[x + y * 9];
			if(stack == null)
				continue;
			IToken token = (IToken)stack.getItem();
			if(token.getType(stack) == EnumToken.EATER){
				Eater eater = new Eater();
				eater.x = x;
				eater.y = y;
				eater.channel = token.getChannel(stack);
				eater.owner = token.getOwner(stack);
				eaters.add(eater);
			}
		}
		
		for(Feeder feeder : feeders){
			updateFeeder(feeder);
		}
		
		for(Eater eater : eaters){
			updateEater(eater);
		}
	}
	
	private class NodePoint{
		Point pos = null;
		NodePoint last = null;
		
		public NodePoint(Point p, NodePoint l){
			this.pos = p;
			this.last = l;
		}
		
		@Override
	    public boolean equals(Object obj){
	    	if(obj == null)
	    		return false;
	    	if(obj == this)
	    		return true;
	    	if(!(obj instanceof NodePoint))
	    			return false;
	    	NodePoint b = (NodePoint) obj;
	    	return pos.equals(b.pos);
	    }
	}
	
	public class DistanceComparator implements Comparator<NodePoint>
	{
		Point goal = null;
		
		public DistanceComparator(Point g){
			this.goal = g;
		}
		
	    @Override
	    public int compare(NodePoint x, NodePoint y)
	    {
	    	double dx = Point2D.distance(x.pos.getX(), x.pos.getY(), goal.getX(), goal.getY());
	    	double dy = Point2D.distance(y.pos.getX(), y.pos.getY(), goal.getX(), goal.getY());
	    	
	        if (dx < dy)
	        {
	            return -1;
	        }
	        if (dx > dy)
	        {
	            return 1;
	        }
	        return 0;
	    }
	}
	
	//TODO: Check if one of the slots are null or diffrent channel values...
	private List<Point> getPath(Point a, Point b){
		
		ArrayList<NodePoint> checkedPoints = new ArrayList<NodePoint>();
		PriorityQueue<NodePoint> upcommingPoints = new PriorityQueue<NodePoint>(9*9, new DistanceComparator(b));
		upcommingPoints.add(new NodePoint(a, null));
		
		while(!upcommingPoints.isEmpty()){
			NodePoint node = upcommingPoints.remove();
			
			if(checkedPoints.contains(node))
				continue;
			else
				checkedPoints.add(node);
			
			if(node.pos.equals(b)){
				ArrayList<Point> road = new ArrayList<Point>();
				NodePoint path = node;
				while(path != null){
					road.add(path.pos);
					path = path.last;
				}
				
				return Lists.reverse(road);
			}
			
			ItemStack stack = contents[node.pos.getX() + node.pos.getY() * 9];
			if(stack == null || !(stack.getItem() instanceof IToken))
				continue;
			
			IToken tokenA = (IToken)stack.getItem();
			if(!node.pos.equals(a) && tokenA.stop(stack))
					continue;
			
			String currentChannel = tokenA.getChannel(stack);
			
			List<Point> points = getNeightbours(node.pos.getX(), node.pos.getY());
			for(Point p : points){
				ItemStack step = contents[p.getX() + p.getY() * 9];
				if(step == null || !(step.getItem() instanceof IToken))
					continue;
				IToken tokenB = (IToken)step.getItem();
				
				if(currentChannel.equals("NONE") || tokenB.getChannel(step).equals(currentChannel) || tokenB.getChannel(step).equals("NONE")){
					upcommingPoints.add(new NodePoint(p, node));
				}
			}
		}
		
		return new ArrayList<Point>();
	}
	
	private void updateFeeder(Feeder feeder) {
		for(Bank bank : banks){
			List<Point> path = getPath(new Point(feeder.x, feeder.y), new Point(bank.x, bank.y));
			
			Path mod = new Path();
			mod.bank = bank;
			mod.points = path;
			if(0 < path.size())
				feeder.add(mod);
		}
	}
	
	private void updateEater(Eater eater) {
		for(Bank bank : banks){
			List<Point> path = getPath(new Point(eater.x, eater.y), new Point(bank.x, bank.y));
			path = Lists.reverse(path);
			Path mod = new Path();
			mod.bank = bank;
			mod.points = path;
			if(0 < path.size())
				eater.add(mod);
		}
	}
	
	private List<Point> getNeightbours(int dx, int dy){
		List list = new ArrayList<Point>();
		
		for(int x = -1; x < 2; x++)
		for(int y = -1; y < 2; y++){
			if(Math.abs(x) == Math.abs(y))
				continue;
			
			int tx = x + dx;
			int ty = y + dy;
			if(tx < 0 || 9 <= tx || ty < 0 || 9 <= ty)
				continue;
			
			ItemStack stack = contents[tx + ty * 9];
			
			if(stack == null || !(stack.getItem() instanceof IToken))
				continue;
			
			list.add(new Point(tx, ty));
		}
		
		return list;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}
	
	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}
	
	public void load(PlayerHungerNetwork hunger) {
		this.contents = hunger.contents;
		this.name = hunger.name;
	}
	
	//SELECTION and FEEDING alg
	public void feed(String channel, ResourcePackage amount, ISelection selection, IFeeding feeding){
		ResourcePackage cpy = amount.copy();
		
		//SELECTION ALGORITHM
		List<Feeder> feeds = new ArrayList<Feeder>();
		if(selection == null){
			for(Feeder f : feeders){
				if(f.channel.equals(channel)){
					feeds.add(f);
				}
			}
		} else
			feeds = selection.select(channel, cpy, feeders);
		
		//Return of no selections found
		if(feeds.size() <= 0)
			return;
		//ORDER ALGORITHM
		if(feeding != null)
			feeds = feeding.order(feeds);
		
		//FEEDING ALGORITHM
		if(feeding == null){
			List<ResourcePackage> packs = cpy.split(feeds.size());
			
			for(int i = 0; i < feeds.size(); i++){
				Feeder feeder = feeds.get(i);
				ResourcePackage p = packs.get(i);
				feed(feeder, p);
			}
			
			//CLEAN UP
			cpy.clear();
			cpy.combine(packs);
		} else
			feeding.feed(feeds, cpy);
		
		//HANDLES RESTS AND SUCH
		double per = (cpy.diffrance == 0.0D && cpy.total == 0.0D) || cpy.total == 0.0D ? 0.0D : cpy.diffrance / cpy.total;
		for(EnumResource res : EnumResource.values()){
			double v = amount.get(res) * per;
			amount.put(res, v);
		}
		
		amount.readBacklogClear(cpy);
		amount.normalize();
	}
	
	
	private void feed(Feeder feeder, ResourcePackage amount) {
		if(feeder.path.size() <= 0)
			return;
		
		List<ResourcePackage> packs = amount.split(feeder.path.size());
		
		for(int i = 0; i < feeder.path.size(); i++){
			Path path = feeder.path.get(i);
			ResourcePackage pack = packs.get(i);
			
			for(int j = 1; j < path.points.size(); j++){
				Point point = path.points.get(j);
				ItemStack stack = contents[point.getX() + point.getY() * 9];
				if(stack == null || !(stack.getItem() instanceof IToken))
					continue;
				IToken token = (IToken)stack.getItem();
				token.feed(stack, pack);
			}
		}
		
		amount.clear();
		amount.combine(packs);
	}

	public ResourcePackage drain(String channel, double amount){
		ResourcePackage pack = new ResourcePackage();
		
		List<Eater> eats = new ArrayList<Eater>();
		for(Eater e : eaters){
			if(e.channel.equals(channel)){
				eats.add(e);
			}
		}
		
		if(eats.size() <= 0)
			return pack;
		
		List<ResourcePackage> packs = new ArrayList<ResourcePackage>();
		
		for(int i = 0; i < eats.size(); i++){
			Eater eater = eats.get(i);
			packs.add(drain(eater, amount /((double)eats.size())));
		}
		
		pack.clear();
		pack.combine(packs);
		return pack;
	}
	
	private ResourcePackage drain(Eater eater, double amount) {
		ResourcePackage pack = new ResourcePackage();
		if(eater.path.size() <= 0)
			return pack;
		
		List<ResourcePackage> packs = new ArrayList<ResourcePackage>();
		
		for(int i = 0; i < eater.path.size(); i++){
			Path path = eater.path.get(i);
			ResourcePackage p = new ResourcePackage();
			
			for(int j = 0; j < (path.points.size()-1); j++){
				Point point = path.points.get(j);
				ItemStack stack = contents[point.getX() + point.getY() * 9];
				
				if(stack == null || !(stack.getItem() instanceof IToken))
					continue;
				
				IToken token = (IToken)stack.getItem();
				token.drain(stack, p, amount / ((double) eater.path.size()));
			}
			
			if(p.hasBacklog()){
				Point point = path.points.get(0);
				ItemStack stack = contents[point.getX() + point.getY() * 9];
				
				if(stack == null || !(stack.getItem() instanceof IToken))
					continue;
				
				IToken token = (IToken)stack.getItem();
				token.feed(stack, p.getBacklogClear());
			}
			
			packs.add(p);
		}
		
		//TODO ADD LOGIC FOR HANDELING UNEVEN DRAINING
		
		pack.clear();
		pack.combine(packs);
		return pack;
	}

	public PlayerHungerNetworkData toData(){
		PlayerHungerNetworkData data = new PlayerHungerNetworkData();
		data.name = this.getName();
		for(int i = 0; i < contents.length; i++){
			
			if(contents[i] == null){
				data.contents.add("NULL");
			} else {
				NBTTagCompound nbt = new NBTTagCompound();
				contents[i].writeToNBT(nbt);
				data.contents.add(nbt.toString());
			}
		}
		
		return data;
	}

	public boolean canFeed(String channel, ResourcePackage pack) {
		ResourcePackage cpy = pack.copy();
		
		List<Feeder> feeds = new ArrayList<Feeder>();
		for(Feeder f : feeders){
			if(f.channel.equals(channel)){
				feeds.add(f);
			}
		}
		
		if(feeds.size() <= 0)
			return false;
		
		List<ResourcePackage> packs = cpy.split(feeds.size());
		int successes = 0;
		
		for(int i = 0; i < feeds.size(); i++){
			Feeder feeder = feeds.get(i);
			ResourcePackage p = packs.get(i);
			if(canFeed(feeder, p))
				successes++;
		}
		
		return successes == feeds.size();
	}

	private boolean canFeed(Feeder feeder, ResourcePackage p) {
		if(feeder.path.size() <= 0)
			return false;
		
		List<ResourcePackage> packs = p.split(feeder.path.size());
		int success = 0;
		
		for(int i = 0; i < feeder.path.size(); i++){
			Path path = feeder.path.get(i);
			ResourcePackage pack = packs.get(i);
			
			for(int j = 1; j < path.points.size(); j++){
				Point point = path.points.get(j);
				ItemStack stack = contents[point.getX() + point.getY() * 9];
				if(stack == null || !(stack.getItem() instanceof IToken))
					continue;
				IToken token = (IToken)stack.getItem();
				if(token instanceof TokenBank && token.canFeed(stack, pack))
					success++;
				else
					token.canFeed(stack, pack);
			}
		}
		
		return success == feeder.path.size();
	}
	
	
}
