package hok.chompzki.hivetera.research.logic.settlement;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import hok.chompzki.hivetera.api.ArticleContent;
import hok.chompzki.hivetera.api.ArticleContent.EnumContent;
import hok.chompzki.hivetera.client.gui.GuiCraft;
import hok.chompzki.hivetera.client.gui.GuiCraftingHelper;
import hok.chompzki.hivetera.client.gui.KnowledgeDescriptions;
import hok.chompzki.hivetera.recipes.RecipeContainer;
import hok.chompzki.hivetera.registrys.BlockRegistry;
import hok.chompzki.hivetera.registrys.ItemRegistry;
import hok.chompzki.hivetera.registrys.RecipeRegistry;

public class HungerPortal extends ArticleContent {

	private RecipeContainer con = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		//Basic stuff Attuner, Biological Reagent & Biomass TUTORIAL RESEARCH!
		if(con == null)
			con = RecipeRegistry.getRecipreFor(new ItemStack(ItemRegistry.hiveBrain));
		String s = "";
		switch(p){
		case 0:
			s += "In the cold and dark nights of midwinter, family members gathered around fire pits. "
				+"Often they filled the hours of enclosure with games and stories, often revolving around "
				+"their insects. One of this games is called 'Kraken barrel'. The rules are simple, one "
				+"barrel, one feed Kraken bug and lastly one hungry Croot Beetle. I think you can figure out "
				+"the rest now (the hungry eats the feed). It is said that one family trained krakens to be";
			break;
		case 1:
			s += "impossible to catch and another trained croot beetles to be dangerous predators. "
				+"These familes often competed for the title of best insect breeder and trainer. "
				+"One day a argument broke out between as there where a Kraken that never get chaught. "
				+"One side belived in cheating and the other in the glorious success of breeding. "
				+"The darker side wanted to prove that they where right and poured 8 Croot Beetels "
				+"into the same barrels as a single Kraken. ";
			break;
		case 2: 
			s += "The Croot Beetels soon encirceled the lonely Kraken. As it was shaking in fear "
				+"the beetels bit each of the creatures tentacles and started pulling. "
				+"Then it all was over in a flash and left was only a green circle containing darkness. "
				+"Facinated by their discovery the families looked into the portal and saw the deeper darkness "
				+"and feelt The Hunger. Everything they fed was eaten and they thought they found gods maw. "
				+"As more portals where created more stars appered inside the darkness. END.";
			break;
		case 3:
			s += "Rules for Steve are quite simple. The portal by righ-clicking can be bound to the players main "
				+"network. If the portal is crafted with an unbound portal it will bind the other. "
				+"Inside the portal, Steve will find the darkness and a place to store his/hers feeding network. "
				+"Only thing that can be placed inside of it are tokens, that can be made in the Token Assembler. "
				+"Bound portals can be bought from villagers and other players can access these networks. So be careful.";
			break;
		case 4: //Sodium acetate, Cara Rot (Carrot), Elle D'berry (Elderberry), Rabarberpaj... Carla & Fleur (Cauliflower)
			s += KnowledgeDescriptions.getDisplayName(con) + "\n\n";
			s += "       ~ Structure ~\n";
			s += KnowledgeDescriptions.getStructure(con);
			s += "       ~ Creation ~\n\n";
			s += KnowledgeDescriptions.getResult(con);
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 5;
	}

}
