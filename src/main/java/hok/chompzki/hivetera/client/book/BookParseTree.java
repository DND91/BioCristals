package hok.chompzki.hivetera.client.book;

import hok.chompzki.hivetera.client.book.parseNodes.BodyParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.BoldParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.BreedingParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.CoreParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.Cursor;
import hok.chompzki.hivetera.client.book.parseNodes.EndParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.InsectParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.ItemStackParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.NewLineParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.NewPageParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.ParagraphParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.RecipeParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.SummaryParseNode;
import hok.chompzki.hivetera.client.book.parseNodes.TabParseNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class BookParseTree {
	
	public static HashMap<String, Article> articles = new HashMap<String, Article>();
	
	public void init(FMLPostInitializationEvent event) {
		
		new CoreParseNode();
		new SummaryParseNode();
		new BodyParseNode();
		new ParagraphParseNode();
		new ItemStackParseNode();
		new TabParseNode();
		new NewLineParseNode();
		new RecipeParseNode();
		new InsectParseNode();
		new BreedingParseNode();
		new BoldParseNode();
		new EndParseNode();
		new NewPageParseNode();
		
		for(TokenCollection collection : BookTokenizer.collections){
			Article arc = new Article();
			arc.name = collection.name;
			
			arc.mainBody = new CoreParseNode();
			arc.mainBody.parse(new Cursor(), arc, collection);
			arc.mainBody.caulculate(new Cursor(), arc, collection);
			
			articles.put(arc.name, arc);
			
			//arc.mainBody.print();
		}
	}
	
	
}








