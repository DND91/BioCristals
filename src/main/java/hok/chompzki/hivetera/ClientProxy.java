package hok.chompzki.hivetera;

import java.util.Random;

import hok.chompzki.hivetera.api.IArmorInsect;
import hok.chompzki.hivetera.client.gui.GuiInventoryOverlay;
import hok.chompzki.hivetera.client.renderer.RendererGhost;
import hok.chompzki.hivetera.client.renderer.SpeciallRenenderGhost;
import hok.chompzki.hivetera.entity.EntityFruitSpider;
import hok.chompzki.hivetera.entity.EntityWSB;
import hok.chompzki.hivetera.hunger.PlayerHungerStorage;
import hok.chompzki.hivetera.items.armor.ItemBioModArmor;
import hok.chompzki.hivetera.items.armor.insects.ItemSprinter;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.PotionRegistry;
import hok.chompzki.hivetera.research.data.PlayerResearchStorage;
import hok.chompzki.hivetera.research.events.GameEvents;
import hok.chompzki.hivetera.tile_enteties.TileGhost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {
	
	public static int idGhost = 0;
	public static final Random rand = new Random();
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
    	super.postInit(event);
    	
    	MinecraftForge.EVENT_BUS.register(new GuiInventoryOverlay());
    	
    	idGhost = RenderingRegistry.getNextAvailableRenderId();
    	
    	ClientRegistry.bindTileEntitySpecialRenderer(TileGhost.class, new SpeciallRenenderGhost());
    	
    	RenderingRegistry.registerEntityRenderingHandler(EntityWSB.class, new RenderSnowball(ItemRegistry.wsb));
    	RenderingRegistry.registerEntityRenderingHandler(EntityFruitSpider.class, new RenderSnowball(ItemRegistry.fruitSpider));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWSB.class, new RenderSnowball(ItemRegistry.ssb));
    	
    	FMLCommonHandler.instance().bus().register(new ClientEvents());
	}
	
	
	
	@Override
	public void initSaveHandling() {
		PlayerResearchStorage.instance(true);
		PlayerHungerStorage.instance(true);
	}
	
	class ClientEvents {
		
		@SubscribeEvent
		public void fovEvent(FOVUpdateEvent event){
			if(event.entity == null || !(event.entity instanceof EntityPlayerSP))
				return;
			EntityPlayerSP player = event.entity;
			if(ItemBioModArmor.contains(player, (IArmorInsect) ItemRegistry.sprinter)){
				IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
				if(attribute == null)
					return;
				
				AttributeModifier mod = attribute.getModifier(ItemSprinter.speedBoostUUID);
				if(mod == null)
					return;
				
		        event.newfov = 1.0F;
			}
	        
		}
		
		@SubscribeEvent
	    public void onLivingUpdateEvent(LivingUpdateEvent event){
			if (event.entityLiving != null && event.entityLiving.getActivePotionEffect(PotionRegistry.swarm) != null)
	        {
				PotionEffect effect = event.entityLiving.getActivePotionEffect(PotionRegistry.swarm);
				
				for(int i = 0; i < (5 + 5 * effect.getAmplifier()); i++){
					float motionX = (float) (rand.nextGaussian() * 0.02D);
					float motionY = (float) (rand.nextGaussian() * 0.02D);
					float motionZ = (float) (rand.nextGaussian() * 0.02D);
				    double posX = event.entityLiving.posX + rand.nextFloat() * event.entityLiving.width * 2.0F - event.entityLiving.width;
				    double posY = event.entityLiving.posY + 0.5F + rand.nextFloat();
				    double posZ = event.entityLiving.posZ + rand.nextFloat() * event.entityLiving.width * 2.0F - event.entityLiving.width;
				    
				    EntityFX particle = new EntityReddustFX(event.entityLiving.worldObj, posX, posY, posZ, 1.0F, 2.0F, 1.0F);
					particle.setRBGColorF(0.01F, 0.01F, 0.01F);
					particle.setAlphaF(1.0F);
					Minecraft.getMinecraft().effectRenderer.addEffect(particle);
				}
	        }
		}
	}
}














