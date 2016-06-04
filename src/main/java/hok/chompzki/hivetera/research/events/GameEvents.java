package hok.chompzki.hivetera.research.events;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import hok.chompzki.hivetera.BioHelper;
import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.api.IInsect;
import hok.chompzki.hivetera.containers.BioArmor;
import hok.chompzki.hivetera.containers.BioArmor.BioArmorBase;
import hok.chompzki.hivetera.containers.InsectHunt;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.items.armor.ModifierInsect;
import hok.chompzki.hivetera.items.armor.insects.ItemHillSpider;
import hok.chompzki.hivetera.items.armor.insects.ItemSprinter;
import hok.chompzki.hivetera.items.armor.insects.ItemVoidFarer;
import hok.chompzki.hivetera.items.insects.ItemInsect;
import hok.chompzki.hivetera.potion.PotionHungersWrath;
import hok.chompzki.hivetera.recipes.CrootRecipeContainer;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.PotionRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;
import hok.chompzki.hivetera.registrys.ReserchRegistry;
import hok.chompzki.hivetera.research.data.EnumUnlock;
import hok.chompzki.hivetera.research.data.PlayerResearch;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.data.ResearchUnlocks;
import hok.chompzki.hivetera.research.data.ReserchDataNetwork;
import hok.chompzki.hivetera.research.logic.ResearchLogicNetwork;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class GameEvents {
	
	public static Random rand = new Random();
	
	@SubscribeEvent
	public void somethingPickedup(EntityItemPickupEvent event){
		if(event.entityPlayer == null)
			return;
		
		if(event.entityPlayer.worldObj.isRemote)
			return;
		
		ResearchUnlocks.unlock(event.entityPlayer, EnumUnlock.PICKUP, event.item.getEntityItem());
		
		ItemStack current = event.entityPlayer.inventory.getCurrentItem();
		if(current != null && current.getItem() == ItemRegistry.nomadSack){
			ItemStack floor = event.item.getEntityItem();
			if(ItemRegistry.nomadSack.canEat(current, floor)){
				ItemRegistry.nomadSack.eat(current, floor);
				if(floor.stackSize <= 0){
					
				}
				if(floor.stackSize <= 0){
					event.item.setDead();
					event.setResult(Result.ALLOW);
				}
			}
			
		}else if(current != null && current.getItem() == ItemRegistry.crootStick 
				&& event.item.getEntityItem().getItem() == ItemRegistry.crootBeetle){
			ItemStack floor = event.item.getEntityItem();
			if(floor.stackSize <= 0 || event.item.isDead)
				return;
			
			ItemRegistry.crootBeetle.onCreated(floor, event.entityPlayer.worldObj, event.entityPlayer);
			InsectHunt hunt = new InsectHunt(event.entityPlayer, event.entityPlayer.inventory.currentItem);
			BioHelper.addItemStackToInventory(floor, hunt, 0, 1);
			hunt.markDirty();
			if(floor.stackSize <= 0){
				event.item.setDead();
				event.setResult(Result.ALLOW);
			}
		}
	}
	
	@SubscribeEvent
	public void somethingCrafted(ItemCraftedEvent event)
	{
		if(event.player.worldObj.isRemote)
			return;
		
		ResearchUnlocks.unlock(event.player, EnumUnlock.CRAFT, event.crafting);
		
		
	}
	
	//PlayerDestroyItemEvent
	@SubscribeEvent
	public void playerDestroyItemEvent(PlayerDestroyItemEvent event)
	{
		if(event.entityPlayer == null || event.entityPlayer.worldObj.isRemote)
			return;
		
		if(event.original.getItem() == ItemRegistry.crootIronPickaxe){
			BioHelper.addItemStackToInventory(new ItemStack(ItemRegistry.crootStick), event.entityPlayer.inventory);
		} else if(event.original.getItem() == ItemRegistry.crootWoodHoe){
			BioHelper.addItemStackToInventory(new ItemStack(ItemRegistry.crootStick), event.entityPlayer.inventory);
		}
		
		
	}
	
	@SubscribeEvent
	public void itemTooltip(ItemTooltipEvent event)
	{
		ItemStack stack = event.itemStack;
		
		if(!event.showAdvancedItemTooltips && !(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)))
			return;
		
		if(stack.getItem() instanceof ItemFood){
			ItemFood input = (ItemFood)stack.getItem();
			DecimalFormat df = new DecimalFormat("0.00"); 
			double value = input.func_150905_g(stack) * input.func_150906_h(stack) * 2.0D * 0.125D;
			event.toolTip.add("Raw Food(Normal): " + df.format(value));
			value = input.func_150905_g(stack) * input.func_150906_h(stack) * 2.0D;
			event.toolTip.add("Raw Food(" + BlockRegistry.sacrificePit.getLocalizedName() + "): " + df.format(value));
		}
		
		if(stack.getItem() instanceof IArmorInsect){
			IArmorInsect insect = (IArmorInsect)stack.getItem();
			
			String str = event.toolTip.get(0);
			
			switch(insect.getType()){
			case OFFENSIVE:
				str = (char)167 + "4" + str;
				break;
			case DEFENSIVE:
				str = (char)167 + "9" + str;
				break;
			case EATER:
				str = (char)167 + "5" + str;
				break;
			case FUNC:
				str = (char)167 + "6" + str;
				break;
			case PASSIVE:
				str = (char)167 + "a" + str;
				break;
			}
			
			event.toolTip.set(0, str);
		}
	}
	
	@SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent event){
		if (event.entityLiving != null)
        {
            if(event.entityLiving instanceof EntityPlayer)
            {    
                EntityPlayer player = ((EntityPlayer)event.entityLiving);
                for(ModifierInsect mod : ItemBioModArmor.modifiers){
                	IAttributeInstance attribute = player.getEntityAttribute(mod.attribute);
                	if(attribute == null){
                		player.getAttributeMap().registerAttribute(mod.attribute);
                		attribute = player.getEntityAttribute(mod.attribute);
                		attribute.setBaseValue(mod.insect.getBaseModValue());
                	}
                	
                	AttributeModifier modifier = attribute.getModifier(mod.value.getID());
                	
                	if(ItemBioModArmor.contains(player, mod.insect)){
                		if(modifier == null && mod.insect.shouldMod(player.worldObj, player)){
                			attribute.applyModifier(mod.value);
                			mod.insect.applyModifier(attribute, mod.value, player);
                		}else if(modifier != null && !mod.insect.shouldMod(player.worldObj, player)){
                			mod.insect.removeModifier(attribute, mod.value, player);
                			attribute.removeModifier(mod.value);
                		}
                	} else if (!ItemBioModArmor.contains(player, mod.insect) && modifier != null){
                		mod.insect.removeModifier(attribute, mod.value, player);
                		attribute.removeModifier(mod.value);
                	}
                }
            }
            
            if(!event.entityLiving.worldObj.isRemote &&
            		event.entityLiving.getActivePotionEffect(PotionRegistry.hungersWrath) != null 
            		&& this.tickServer % 25 == 0){
            	EntityLiving entity = (EntityLiving) event.entityLiving;
            	Random rand = PotionHungersWrath.rand;
            	
            	PotionEffect effect = entity.getActivePotionEffect(PotionRegistry.hungersWrath);
        		if(effect == null)
        			return;
        		
        		effect.getCurativeItems().clear();
        		
        		if(entity.worldObj.isRemote)
        			return;
        		
        		int d = effect.getDuration();
        		int s = 20;
        		int m = 60 * s;
        		int h = 60 * m;
        		
        		
        		if(d < 2 * m){
        			setEffect(effect, 5);
        			
        			if(rand.nextInt() % 2 == 0){
        				entity.addPotionEffect(new PotionEffect(Potion.hunger.id, m, 5));
        			}
        			
        			if(rand.nextInt() % 2 == 0){
        				entity.attackEntityFrom(DamageSource.starve, 1.0F);
        			}
        			
        			if(rand.nextInt() % 50 == 0){
        				entity.worldObj.setBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ, BlockRegistry.blood);
        			}
        			
        			if(rand.nextInt() % 25 == 0){
        				entity.worldObj.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 1.0F, false);
        			}
        			
        			if(rand.nextInt() % 5 == 0){
        				entity.worldObj.setEntityState(entity, (byte)10);
        				entity.worldObj.playSoundAtEntity(entity, "mob.ghast.scream", 0.75F, 1.0F);
        			}
        			
        		} else if(d < 15 * m){
        			setEffect(effect, 4);
        			
        			if(rand.nextInt() % 25 == 0){
        				entity.attackEntityFrom(DamageSource.starve, 1.0F);
        			}
        			
        			if(rand.nextInt() % 25 == 0){
        				entity.addPotionEffect(new PotionEffect(Potion.hunger.id, m, 4));
        			}
        			
        			if(rand.nextInt() % 100 == 0 && entity.worldObj.isAirBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ)){
        				entity.worldObj.setBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ, BlockRegistry.blood);
        			}
        			
        			if(rand.nextInt() % 50 == 0 && entity.worldObj.isAirBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ)){
        				entity.worldObj.setBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ, Blocks.web);
        			}
        			
        			if(rand.nextInt() % 100 == 0){
        				entity.worldObj.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 1.0F, false);
        			}
        			
        			if(rand.nextInt() % 10 == 0){
        				entity.worldObj.setEntityState(entity, (byte)10);
        				entity.worldObj.playSoundAtEntity(entity, "mob.ghast.scream", 0.75F, 1.0F);
        			}
        			
        		} else if(d < 30 * m){
        			setEffect(effect, 3);
        			
        			if(rand.nextInt() % 100 == 0){
        				entity.attackEntityFrom(DamageSource.starve, 1.0F);
        			}
        			
        			if(rand.nextInt() % 50 == 0){
        				entity.addPotionEffect(new PotionEffect(Potion.hunger.id, m, 3));
        			}
        			
        			if(rand.nextInt() % 100 == 0 && entity.worldObj.isAirBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ)){
        				entity.worldObj.setBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ, Blocks.web);
        			}
        			
        			if(rand.nextInt() % 10 == 0){
        				entity.worldObj.setEntityState(entity, (byte)10);
        				entity.worldObj.playSoundAtEntity(entity, "mob.endermen.scream", 0.75F, 1.0F);
        			}
        			
        		} else if(d < 1 * h){
        			setEffect(effect, 2);
        			
        			if(rand.nextInt() % 250 == 0){
        				entity.attackEntityFrom(DamageSource.starve, 1.0F);
        			}
        			
        			if(rand.nextInt() % 100 == 0){
        				entity.addPotionEffect(new PotionEffect(Potion.hunger.id, m, 2));
        			}
        			
        			
        			if(rand.nextInt() % 10 == 0){
        				entity.worldObj.setEntityState(entity, (byte)10);
        				entity.worldObj.playSoundAtEntity(entity, "mob.endermen.scream", 0.75F, 1.0F);
        			}
        			
        		} else if(d < 1 * h + 30 * m){
        			setEffect(effect, 1);
        			
        			if(rand.nextInt() % 100 == 0){
        				entity.addPotionEffect(new PotionEffect(Potion.hunger.id, m, 1));
        			}
        			if(rand.nextInt() % 250 == 0){
        				entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 30*s, 1));
        			}
        			
        			if(rand.nextInt() % 10 == 0){
        				entity.worldObj.setEntityState(entity, (byte)10);
        				entity.worldObj.playSoundAtEntity(entity, "creeper.primed", 0.75F, 1.0F);
        			}
        			if(rand.nextInt() % 10 == 0){
        				entity.worldObj.setEntityState(entity, (byte)10);
        				entity.worldObj.playSoundAtEntity(entity, "mob.zombie.remedy", 0.75F, 1.0F);
        			}
        			
        		} else {
        			setEffect(effect, 0);
        			
        			if(rand.nextInt() % 250 == 0){
        				entity.addPotionEffect(new PotionEffect(Potion.hunger.id, m, 0));
        			}
        			if(rand.nextInt() % 500 == 0){
        				entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 10*s, 0));
        			}
        			
        			if(rand.nextInt() % 10 == 0){
        				entity.worldObj.setEntityState(entity, (byte)10);
        				entity.worldObj.playSoundAtEntity(entity, "game.tnt.primed", 0.75F, 1.0F);
        			}
        		}
            }
        }
	}
	
	public void setEffect(PotionEffect effect, int amp){
		if(effect.getAmplifier() == amp)
			return;
		
		int d = effect.getDuration();
		effect.combine(new PotionEffect(PotionRegistry.hungersWrath.id, d, amp));
	}
	
	@SubscribeEvent
	public void jumpMod(LivingJumpEvent event){
		if(event.entity == null || !(event.entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) event.entity;
		
		if(ItemBioModArmor.contains(player, (IArmorInsect) ItemRegistry.darter)){
			player.motionX *= 2.0F;
			player.motionY *= 1.5F;
			player.motionZ *= 2.0F;
		}
		
		NBTTagCompound nbt = player.getEntityData();
		if(!player.worldObj.isRemote && ItemBioModArmor.contains(player, (IArmorInsect) ItemRegistry.greenBlower)){
			nbt.setBoolean("HIVE_JUMP",  true);
			player.onGround = false;
		}else if (!player.worldObj.isRemote){
			nbt.setBoolean("HIVE_JUMP",  false);
		}
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
	    if (event.entity instanceof EntityPlayer)
	    {
	    	 EntityPlayer player = ((EntityPlayer)event.entity);
             for(ModifierInsect mod : ItemBioModArmor.modifiers){
            	 if(!mod.shouldAdd)
            		 continue;
             	IAttributeInstance attribute = player.getEntityAttribute(mod.attribute);
             	if(attribute == null){
             		player.getAttributeMap().registerAttribute(mod.attribute);
             		attribute = player.getEntityAttribute(mod.attribute);
             		attribute.setBaseValue(mod.insect.getBaseModValue());
             	}	
             }
	    }
	}
	
	public static int tickServer = 0;
	
	@SubscribeEvent
	public void tick(TickEvent event){
		
		if(event.phase == TickEvent.Phase.START && event.side == Side.SERVER && event.type == TickEvent.Type.SERVER){
			tickServer++;
		}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event){
		if(event.entityLiving != null && event.entityLiving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			
		}
		
		if(event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)event.source.getEntity();
			//BULLWORM
			if(ItemBioModArmor.contains(player, (IArmorInsect)ItemRegistry.bullWorm)){
				if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem().isItemTool(player.getCurrentEquippedItem())){
					BioArmor[] armors = ItemBioModArmor.getArmors(player);
					ItemStack stack = ItemBioModArmor.getInsect(player, (IArmorInsect)ItemRegistry.bullWorm);
					IInsect insect = (IInsect)stack.getItem();
					
					double currentFood = insect.getFood(stack);
					
					if(currentFood < insect.getCost(stack)){
						double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
						
						currentFood += value[insect.getFoodType(stack).toInt()];
						insect.setFood(stack, currentFood);
					}

					if(insect.getCost(stack) <= currentFood){
						currentFood -= insect.getCost(stack);
						insect.setFood(stack, currentFood);
						event.ammount += 6.0F;
					}
				}
			}
			//LEECH
			if(event.entityLiving != null && event.entityLiving instanceof EntityLiving
					&& ItemBioModArmor.contains(player, (IArmorInsect)ItemRegistry.blackLeech)){
				if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem().isItemTool(player.getCurrentEquippedItem())){
					BioArmor[] armors = ItemBioModArmor.getArmors(player);
					ItemStack stack = ItemBioModArmor.getInsect(player, (IArmorInsect)ItemRegistry.blackLeech);
					IInsect insect = (IInsect)stack.getItem();
					
					double currentFood = insect.getFood(stack);
					
					if(currentFood < insect.getCost(stack)){
						double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
						
						currentFood += value[insect.getFoodType(stack).toInt()];
						insect.setFood(stack, currentFood);
					}
					
					if(insect.getCost(stack) <= currentFood){
						currentFood -= insect.getCost(stack);
						insect.setFood(stack, currentFood);
						EntityLiving living = (EntityLiving)event.entityLiving;
						living.addPotionEffect(new PotionEffect(PotionRegistry.leech.id, 600, 1));
					}
				}
			}
			//SWARM
			if(event.entityLiving != null && event.entityLiving instanceof EntityLiving
					&& ItemBioModArmor.contains(player, (IArmorInsect)ItemRegistry.hungerSwarm)){
				if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem().isItemTool(player.getCurrentEquippedItem())){
					BioArmor[] armors = ItemBioModArmor.getArmors(player);
					ItemStack stack = ItemBioModArmor.getInsect(player, (IArmorInsect)ItemRegistry.hungerSwarm);
					IInsect insect = (IInsect)stack.getItem();
					
					double currentFood = insect.getFood(stack);
					
					if(currentFood < insect.getCost(stack)){
						double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
						
						currentFood += value[insect.getFoodType(stack).toInt()];
						insect.setFood(stack, currentFood);
					}
					
					if(insect.getCost(stack) <= currentFood){
						currentFood -= insect.getCost(stack);
						insect.setFood(stack, currentFood);
						EntityLiving living = (EntityLiving)event.entityLiving;
						living.addPotionEffect(new PotionEffect(PotionRegistry.swarm.id, 600, 2));
					}
				}
			}
			//SWARM
			if(event.entityLiving != null && event.entityLiving instanceof EntityLiving
					&& ItemBioModArmor.contains(player, (IArmorInsect)ItemRegistry.priestBeetle)){
				if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem().isItemTool(player.getCurrentEquippedItem())){
					BioArmor[] armors = ItemBioModArmor.getArmors(player);
					ItemStack stack = ItemBioModArmor.getInsect(player, (IArmorInsect)ItemRegistry.priestBeetle);
					IInsect insect = (IInsect)stack.getItem();
					
					double currentFood = insect.getFood(stack);
					
					if(currentFood < insect.getCost(stack)){
						double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
						
						currentFood += value[insect.getFoodType(stack).toInt()];
						insect.setFood(stack, currentFood);
					}
					
					if(insect.getCost(stack) <= currentFood){
						currentFood -= insect.getCost(stack);
						insect.setFood(stack, currentFood);
						EntityLiving living = (EntityLiving)event.entityLiving;
						living.addPotionEffect(new PotionEffect(PotionRegistry.hungersWrath.id, PotionHungersWrath.startTime, 0));
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event){
		
		if(event.entityLiving == null || !(event.entityLiving instanceof EntityPlayer))
				return;
		EntityPlayer player = (EntityPlayer)event.entityLiving;
		
		if(!player.worldObj.isRemote && ItemBioModArmor.contains(player, (IArmorInsect) ItemRegistry.voidFarer)){
			BioArmor[] armors = ItemBioModArmor.getArmors(player);
			
			ItemStack stack = null;
			for(BioArmor armor : armors){
				if(armor != null){
					for(BioArmorBase s : armor.inventory){
						if(s.slot != null && s.slot.getItem() == ItemRegistry.voidFarer){
							stack = s.slot;
							break;
						}
					}
				}
			}
			
			IInsect insect = (IInsect) ItemRegistry.voidFarer;
			
			double currentFood = insect.getFood(stack);
			
			if(currentFood < insect.getCost(stack)){
				double[] value = ItemInsect.drain(player, armors, insect.getDrain(stack), insect.getFoodType(stack));
				
				currentFood += value[insect.getFoodType(stack).toInt()];
				insect.setFood(stack, currentFood);
			}
			
			if(insect.getCost(stack) <= currentFood && player.getHealth() <= event.ammount){
				event.setCanceled(true);
				
				currentFood -= insect.getCost(stack);
				insect.setFood(stack, currentFood);
				
				double sX = player.posX;
				double sY = player.posY;
				double sZ = player.posZ;
		        for(int i = 0; i < 10; i++){
		        	double d0 = player.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
			        double d1 = player.posY + (double)(this.rand.nextInt(64) - 32);
			        double d2 = player.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
			       
		        	if(((ItemVoidFarer) ItemRegistry.voidFarer).teleportTo(player, d0, d1, d2)){
		        		double distance = Math.sqrt((player.posX-sX)*(player.posX-sX) + (player.posY-sY)*(player.posY-sY) + (player.posZ-sZ)*(player.posZ-sZ));
		        		if(distance <= 5.0D)
		        			continue;
		        		return;
		        	}
		        }
			}
		}
		
	}
}





















