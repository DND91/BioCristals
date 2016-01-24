package hok.chompzki.hivetera.research.logic.settlement;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
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

public class TutorialHunger extends ArticleContent {

	ItemStack hungerPortal = null;
	ItemStack bank = null;
	ItemStack bridge = null;
	ItemStack eater = null;
	ItemStack feeder = null;
	ItemStack filter = null;
	ItemStack transformer = null;
	ItemStack tokenAssembler = null;
	ItemStack sacrificePit = null;
	
	@Override
	public String textOnPage(EnumContent content, int p){
		if(hungerPortal == null){
			hungerPortal = new ItemStack(ItemRegistry.hiveBrain);
			bank = new ItemStack(ItemRegistry.tokenBank);
			bridge = new ItemStack(ItemRegistry.tokenBridge);
			eater = new ItemStack(ItemRegistry.tokenEater);
			feeder = new ItemStack(ItemRegistry.tokenFeeder);
			filter = new ItemStack(ItemRegistry.tokenFilter);
			transformer = new ItemStack(ItemRegistry.tokenTransformer);
			tokenAssembler = new ItemStack(BlockRegistry.tokenAssembler);
			sacrificePit = new ItemStack(BlockRegistry.sacrificePit);
		}
		String s = "";
		switch(p){
		case 0: 
			s = "" + ((char)167) + "l ~ Introduction ~ " + ((char)167) + "r\n";
			s +="Introduction p.2\n";
			s +="Resources p.3\n";
			s +="Tokens p.7\n";
			s +="Sacrifice Pit & Bank p.8\n";
			s +="Simple Network p.9\n";
			s +="Network p.10\n";
			s +="Even drain and feed p.12\n";
			s +="Channels p.15\n";
			s +="Leakage & Drawback p.16\n";
			s +="Advanced Network p.17\n";
			break;
		case 1:
			s = "" + ((char)167) + "l ~ Introduction ~ " + ((char)167) + "r\n";
			s +="The Hunger Portal opens into a pocket inside of The Hunger "
			  + "(The Hunger is not the same as player hunger). As a player, "
			  + "you uses these pockets for power networks. These networks are used to "
			  + "sustaine everything from nesting blocks to insects like the Void "
			  + "Cralwer.\n";
			break;
		case 2:
			s = "" + ((char)167) + "l ~ Resources ~ " + ((char)167) + "r\n";
			s +="Inside if The Hunger there exist six resources. These resources "
			  + "functions to power diffrent kinds of insects. Diffrent insects "
			  + "eats diffrent things so to speak. These resources are...\n\n";
			s +="" + ((char)167) + "lRaw Food" + ((char)167) + "r: ";
			s += "A first tier resource that is dirrectly extracted from food and waste. "
			  + "Insects that eat this resource can also extract it, but are very inefficent. "
			  + "Use the Sacrifice Pit for efficiency. The resource is consumed by basic insects.\n";
			break;
		case 3:
			s +="" + ((char)167) + "lBiomass" + ((char)167) + "r: ";
			s += "A second tier resource that is transformed from "
				+"raw food. It's consumed by diligent insects. For example Fruit Spider\n\n";
			s +="" + ((char)167) + "lWaste" + ((char)167) + "r: ";
			s += "A none tier resource that is comes from the transormation of "
				+"anything as a byproduct. The token transformer can be set to "
				+"only produce waste from biomass. It's consumed by really desperate insects. "
				+"For example Krakens. \n";
			break;
		case 4:
			s +="" + ((char)167) + "lPsychic Energy" + ((char)167) + "r: ";
			s += "A third tier resource that is extracted from biomass. One of "
				+"the more expensive resources to work with. It's is consumed by "
				+"insects that uses pschic powers, for example Void Crawler.\n\n";
			s +="" + ((char)167) + "lLife Fluids" + ((char)167) + "r: ";
			s += "A third tier resource that is extracted from biomass. "
			  +  "It's is consumed by insects that either lives in the water or "
			  +  " focuses on production. For example Kitteh Beetle.\n";
			break;
		case 5:
			s +="" + ((char)167) + "lNuritment" + ((char)167) + "r: ";
			s += "A third tier resource that is extracted from biomass. "
			  +  "It's is consumed by insects that do not fit into any "
			  +  "other of the above categories. For example UNKOWN.\n";
			break;
		case 6:
			s = "" + ((char)167) + "l ~ Tokens ~ " + ((char)167) + "r\n";
			s +="Tokens that are banks, bridges (conduits), filters, "
			+   "transformers, feeders (input) and eaters (output). "
			+   "They are the basic parts of a Hunger Network and will "
			+   "in many ways function as a central aspect.\n";
			s +="Inside a Hunger Portal you will find two areas. The "
			+   "one with darker background is for your network, while "
			+   "the right side is for storing tokens.\n";
			s +="Tokens can only connect to other tokens horizontally and vertically "
			   +"placed in a network. \n";
			break;
		case 7:
			s = "" + ((char)167) + "l ~ Sacrifice Pit & Bank ~ " + ((char)167) + "r\n";
			s += "The basic network consists of two parts: Sacrifice pit & "
				+"a bank that can store raw food. You place the bank in the "
				+"bottom slot of the sacrifice pit and then food in the top. "
				+"Over time, food will be converted into raw food and stored "
				+"in the bank.\n";
			s +="When the bank have some raw food it can be used inside of "
			  + "diffrent blocks as a power source, if that block eats raw food. "
			  + "It can also be stored in the players inventory to feed any insects. ";
			break;
		case 8:
			s = "" + ((char)167) + "l ~ Simple Network ~ " + ((char)167) + "r\n";
			s +="A simple network consists of five parts: Sacrifice pit, feeder, bank, "
			+   "eater and a consumer block (Ex. Nest). Inside the sacrifice pit "
			+ 	"place one feeder, bound to the network you want to use, in the bottom slot. "
			+ 	"Inside the Hunger Portal, place a feeder, a bank and a eater "
			+ 	"next to eachother. Like this:\n\n";
			s += "\t\f";
			s += KnowledgeDescriptions.transformStrictItemStack(feeder, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bank, false);
			s += KnowledgeDescriptions.transformStrictItemStack(eater, false);
			s += "\t\n";
			s += "Then place a eater bound to you inside a consumer block. The "
				+"block will drain resources as needed.";
			break;
		case 9:
			s = "" + ((char)167) + "l ~ Network ~ " + ((char)167) + "r\n";
			s += "To the simple network we can add filters, bridges and transformers. "
				+"Like this: \n\n";
			s += "\t<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(feeder, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += KnowledgeDescriptions.transformStrictItemStack(transformer, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bank, false);
			s += KnowledgeDescriptions.transformStrictItemStack(filter, false);
			s += KnowledgeDescriptions.transformStrictItemStack(eater, false);
			s += "\t\n";
			s += "Resources will pass from feeder to bank and transformer will "
				+"act on the resources. Changing one resource type to the other and "
				+"create a byproduct. Eater will drain resources from the bank and "
				+"pass the filter on the way. The filter will send back a filtired "
				+"resource to the bank. ";
			break;
		case 10:
			s = "Note: Feeders and eaters don't look over banks ! Like this:\n\n";
			s += "\t<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(feeder, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bank, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bank, false);
			s += KnowledgeDescriptions.transformStrictItemStack(eater, false);
			s += "\t\n";
			s += "The feeder will take from the closest bank and so does the eater. Resources in this setup "
				+"dosn't flow from feeder to eater. Only feeder to the first bank and eater will drain from "
				+"second bank.\n";
			break;
		case 11:
			s = "" + ((char)167) + "l ~ Even drain and feed ~ " + ((char)167) + "r\n";
			s +="Feeders and eaters will drain and feed banks evenly. This means that "
			+   "if you connect three banks to a feeder, it will evenly divde "
			+   "resources over those banks. It can pass diffrent paths to reach each "
			+   "of the banks. This means that if you feed the system 90 it will divide "
			+   "over the three paths leading to each bank. Each bank will gain 30.\n";
			break;
		case 12:
			s="If you connect three banks to a eater (that contains 100, 20, 100 of a resource), "
			 + "it will drain evenly. This means that if something drains 90. "
			 +  "30 will be drained from each bank. One of the banks only have 20. "
			 +  "The eater will drain it all. This means that we drain 90, but get 80. "
			 +  "\n";
			break;
		case 13:
			s = "Example network:\n\n";
			s += "\t<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(feeder, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += "<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += KnowledgeDescriptions.transformStrictItemStack(eater, false);
			s += "\t\n\n";
			
			s += "\t<empty>";
			s += "<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(transformer, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bank, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += "\t\n\n";
			
			s += "\t<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(feeder, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += "<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += "\t\n\n";
			
			s += "\t<empty>";
			s += "<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(transformer, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bank, false);
			s += KnowledgeDescriptions.transformStrictItemStack(transformer, false);
			s += "\t\n\n";
			
			s += "\t<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(feeder, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += "<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += "\t\n\n";
			
			s += "\t<empty>";
			s += "<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(transformer, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bank, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += "\t\n\n";
			
			s += "\t<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(feeder, false);
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += "<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(bridge, false);
			s += KnowledgeDescriptions.transformStrictItemStack(eater, false);
			s += "\t\n\n";
			
			break;
		case 14:
			s = "" + ((char)167) + "l ~ Channeles ~ " + ((char)167) + "r\n";
			s+= "Tokens can be attuned to diffrent channels by coloring. "
			+   "When a token is attuned it will only connect to its own color "
			+   "and tokens without color. Colorless tokens will connect to any "
			+   "token. Example: \n}\n";
			
			s += "\t<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(new ItemStack(ItemRegistry.tokenFeeder, 1, 1), false);
			s += KnowledgeDescriptions.transformStrictItemStack(new ItemStack(ItemRegistry.tokenBridge, 1, 1), false);
			s += KnowledgeDescriptions.transformStrictItemStack(new ItemStack(ItemRegistry.tokenBank, 1, 1), false);
			s += "\t\n\n";
			
			s += "\t<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(new ItemStack(ItemRegistry.tokenFeeder, 1, 2), false);
			s += KnowledgeDescriptions.transformStrictItemStack(new ItemStack(ItemRegistry.tokenBridge, 1, 2), false);
			s += KnowledgeDescriptions.transformStrictItemStack(new ItemStack(ItemRegistry.tokenBank, 1, 2), false);
			s += "\t\n\n";
			
			s += "\t<empty>";
			s += KnowledgeDescriptions.transformStrictItemStack(new ItemStack(ItemRegistry.tokenFeeder, 1, 3), false);
			s += KnowledgeDescriptions.transformStrictItemStack(new ItemStack(ItemRegistry.tokenBridge, 1, 3), false);
			s += KnowledgeDescriptions.transformStrictItemStack(new ItemStack(ItemRegistry.tokenBank, 1, 3), false);
			s += "\t\n\n";
			
			break;
		case 15:
			s = "" + ((char)167) + "l ~ Leakage & Drawback ~ " + ((char)167) + "r\n";
			s +="Leakage happens when either a eater drains resources that are not used or "
			+   "when a feeder has resources sent back that it cannot handle. "
			+   "The more that leaks the worse are the effects. First players around a leak "
			+   "will be subject to potion effects, if it gets worse mobs and animals will start "
			+ 	"to spawn. Best way to find leaks are to study resource flows and see where resources "
			+ 	"can go. Filters do not effect things that bounce back. ";
			break;
		case 16:
			s = "" + ((char)167) + "l ~ Advanced Network ~ " + ((char)167) + "r\n";
			s += "Comming soon! :)";
			break;
		}
		return s;
	}
	
	
	@Override
	public int numberOfPages(EnumContent content){
		return 17;
	}

}
