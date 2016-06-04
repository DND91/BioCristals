package hok.chompzki.hivetera.client.book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import hok.chompzki.hivetera.HiveteraMod;
import hok.chompzki.hivetera.client.book.tokens.NumberToken;
import hok.chompzki.hivetera.client.book.tokens.StringToken;
import hok.chompzki.hivetera.client.book.tokens.Token;
import hok.chompzki.hivetera.client.book.tokens.WordToken;

public class BookTokenizer {
	
	public static final Token lineEnd = new Token("LINE_END");
	public static final Token equals = new Token("EQUALS");
	public static final Token startCommand = new Token("START_COMMAND");
	public static final Token endCommand = new Token("END_COMMAND");
	
	
	public static final String standardPath = HiveteraMod.MODID + ":" + "articles/";
	
	public static HashMap<String, ResourceLocation> resources = new HashMap<String, ResourceLocation>();
	public static List<TokenCollection> collections = new ArrayList<TokenCollection>();
	
	public static void register(String code){
		resources.put(code, new ResourceLocation(standardPath + code + ".arc"));
	}
	
	public void init(FMLPostInitializationEvent event) {
		for(Entry<String, ResourceLocation> entry : resources.entrySet()){
			ResourceLocation loc = entry.getValue();
			IResource resource;
			try {
				resource = Minecraft.getMinecraft().getResourceManager().getResource(loc);
			
				if(resource == null){
					System.err.println("Missing resource for article file: " + loc.getResourceDomain() + ":" + loc.getResourcePath());
					continue;
				}
				
				
				tokenize(entry.getKey(), resource.getInputStream());
				
			} catch (IOException e) {
				System.err.println("Missing resource for article file: " + loc.getResourceDomain() + ":" + loc.getResourcePath());
			}
		}
	}

	private void tokenize(String name, InputStream inputStream) {
		TokenCollection collection = new TokenCollection();
		collection.name = name;
		Reader r = new BufferedReader(new InputStreamReader(inputStream));
		StreamTokenizer tokenizer = new StreamTokenizer(r);
		tokenizer.resetSyntax();
		tokenizer.commentChar(35);
		tokenizer.eolIsSignificant(true);
		tokenizer.quoteChar(34);
		tokenizer.wordChars(63, 126);
		tokenizer.wordChars(33, 33);
		tokenizer.wordChars(36, 47);
		tokenizer.wordChars(58, 59);
		tokenizer.whitespaceChars(28, 32);
		tokenizer.whitespaceChars(13, 13);
		tokenizer.parseNumbers();
		
		
		boolean eof = false;
		try {
        do {
        	int token = tokenizer.nextToken();
        	
        	switch (token) {
              case StreamTokenizer.TT_EOF:
                 eof = true;
                 break;
              case StreamTokenizer.TT_EOL:
            	  collection.stack.add(lineEnd);
                 break;
              case StreamTokenizer.TT_WORD:
            	  wordToken(collection.stack, tokenizer.sval);
                 break;
              case StreamTokenizer.TT_NUMBER:
            	  numberToken(collection.stack, tokenizer.nval);
                 break;
              case 60: //<
            	  collection.stack.add(this.startCommand);
            	  break;
              case 62: //>
            	  collection.stack.add(this.endCommand);
            	  break;
              case 61: //=
            	  collection.stack.add(this.equals);
            	  break;
              case 34: //"
            	  stringToken(collection.stack, tokenizer.sval);
            	  break;
              case 45: //-
            	  wordToken(collection.stack, "-");
                 break;
              default:
                 System.err.println(name + " => " + (char) token + "(" + (int)token + ")" + " encountered and is UNKOWN at line: " + tokenizer.lineno());
           }
        } while (!eof);
        } catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		collections.add(collection);
		for(Token token : collection.stack){
			//System.out.println(name + " => " + token.value);
		}
	}
	
	private void stringToken(Deque<Token> stack, String sval) {
		stack.add(new StringToken(sval));
	}

	private void numberToken(Deque<Token> stack, double nval) {
		stack.add(new NumberToken((Double)nval));
	}
	
	private void wordToken(Deque<Token> stack, String sval) {
		stack.add(new WordToken(sval));
	}
	
	
	
}









