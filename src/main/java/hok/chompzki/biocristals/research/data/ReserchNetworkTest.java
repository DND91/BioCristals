package hok.chompzki.biocristals.research.data;

import static org.junit.Assert.*;
import net.minecraft.client.Minecraft;

import org.junit.Test;

public class ReserchNetworkTest {

	@Test
	public void test() {
		/*ReserchDataNetwork.register(new Research("babySteps"));
		ReserchDataNetwork.register(new Research("raptorHeads"));
		ReserchDataNetwork.register(new Research("loneWolf"));
		
		ReserchDataNetwork.register(new Research("cristalization", "babySteps"));
		ReserchDataNetwork.register(new Research("salt", "babySteps"));
		ReserchDataNetwork.register(new Research("carp", "babySteps"));
		ReserchDataNetwork.register(new Research("boonzai", "cristalization", "raptorHeads"));
		ReserchDataNetwork.register(new Research("steel", "boonzai"));*/
		
		ReserchDataNetwork.build();
		
		ReserchDataNetwork.instance().printNetworkInfo();
		
	}

}
