<summary>
<bold>WELP<end>
How did you get to here...?
<body><bold>Tutorial For Hunger Network<end>
<bold> ~ Introduction ~ <end>
The Hunger Portal opens into an isolated pocket of space inside The Hunger (The Hunger is not the same as the player's hunger). As a player, you uses these pockets for different kinds of networks. In the beginning it's only used a power network. These networks are used to sustain everything from nesting blocks to insects, such as the Void Crawler.
<NP><bold> ~ Resources ~ <end>
Inside of The Hunger there exist six resources. These resources function to power different kinds of insects. Different insects will eat different things; that is to say, they all prefer different types of resources. These resources are...
<tab><bold>Raw Food<end>
A first tier resource that is directly extracted from food and waste. Insects that eat this resource can also extract it from it's original source, but they are very inefficient at extracting it themselves. Use the Sacrifice Pit for efficiency. The resource is consumed by basic insects.
<tab><bold>Biomass<end>
A second tier resource that is transformed from raw food. It's consumed by diligent insects; an example being the Fruit Spider.
<tab><bold>Waste<end>
A non-tiered resource that is a byproduct of the transformation of anything into a resource. The token transformer can be set to only produce waste from biomass. It's consumed by really desperate insects, such as Krakens. 
<tab><bold>Psychic Energy<end>
A third tier resource that is extracted from biomass. One of the more expensive resources to work with. It's is consumed by insects that uses psychic powers; for example the Void Crawler.
<tab><bold>Life Fluids<end>
A third tier resource that is extracted from biomass. It's is consumed by insects that either live in the water or focuses on production. For example, the Kitteh Beetle.
<tab><bold>Nuritment<end>
A third tier resource that is extracted from biomass. It's is consumed by insects that do not fit into any of the above categories. For example UNKOWN.
<NP><bold> ~ Tokens ~ <end>
Tokens are split into various types; banks, bridges (conduits), filters, transformers, feeders (input) and eaters (output). They are the basic parts of a Hunger Network and will, in many ways, function as a central aspect. Inside a Hunger Portal you will find two areas. The one with a darker background, on the left, is for your network, while the section on the right is for storing tokens. Tokens can only connect to other tokens placed in a network horizontally or vertically. 
<NP><bold> ~ Sacrifice Pit & Bank ~ <end>
The basic network consists of two parts: the Sacrifice Pit and a Bank that can store raw food. You place the Bank in the bottom slot of the Sacrifice Pit, and then place food in the top.  Over time, food will be converted into raw food and stored in the bank. When the bank has stored some raw food, it can be used inside of different blocks as a power source, as long as that block eats raw food.  It can also be stored in the player's inventory to feed any insects that consume raw food. 
<NP><bold> ~ Simple Network ~ <end>
A simple network consists of five parts: the Sacrifice Pit, Feeder, Bank, Eater and some type of consumer block (Ex. Nest). Inside the Sacrifice Pit place one Feeder (remember to bind it) in the bottom slot. Inside the Hunger Portal, place a Feeder, a Bank and an Eater next to each other. Like this:
<ITEMSTACK name=itemFeeder domain=Hivetera><ITEMSTACK name=itemBank domain=Hivetera><ITEMSTACK name=itemEater domain=Hivetera>
Then place an Eater that is bound to you inside a consumer block. The block will drain resources as needed.
<NP><bold> ~ Network ~ <end>
Resources will pass from Feeder to Bank, and the Transformer will act on the resources; changing one resource type to the other, as well as creating a byproduct. The Eater will then drain resources from the bank and, if any Filters are on the route the resources travel, the Filter will send back any filtered resource to the bank. 
<bold>Note:<end> Feeders and Eaters don't look past the first bank they encounter in a line! 
This won't work:
<ITEMSTACK name=itemFeeder domain=Hivetera><ITEMSTACK name=itemBank domain=Hivetera><ITEMSTACK name=itemBank domain=Hivetera><ITEMSTACK name=itemEater domain=Hivetera>
The Feeder will take from the closest bank and so does the Eater. Resources in this setup don't flow from Feeder to Eater; the Feeder will only send resources to the first Bank, and the Eater will only drain from second Bank.
<NP><bold> ~ Even drain and feed ~ <end>
Feeders and Eaters will drain and feed banks evenly. This means that if you connect three Banks to a Feeder, it will evenly divide resources over those Banks. It can pass different paths to reach each of the Banks. This means that if you feed the system 90 resources, it will divide the resources over the three paths leading to each bank, and each bank will gain 30. If you connect three banks to an Eater (assuming these banks contain 100, 20, and 100 of a resource respectively), it will drain evenly. This means that if something requests 90 resources from the Eater, 30 will be drained from each bank. However, in this scenario, one of the banks only has 20 resources. Therefore, the Eater will drain all the resources in the bank with only 20 in it, and drain 30 from the other two banks. In the end, even though 90 resources were requested, you will only get 80 instead (20, 30 and 30).
<NP><bold> ~ Example Network ~ <end>
EXAMPLE!
<NP><bold> ~ Channels ~ <end>
Tokens can be attuned to different channels by coloring. When a Token is attuned, it will only connect to other Tokens its own color, as well as Tokens without color. Colorless tokens will connect to any Token. Example:
EXAMPLE
<NP><bold> ~ Leakage & Drawback ~ <end>
Leakage happens when either an Eater drains resources that are not used, or when a Feeder has resources sent back that it cannot handle. The more that leaks, the worse are the effects. First players around a leak will be subject to potion effects, but if it gets worse, mobs and animals will start to spawn. The best way to find leaks are to study resource flows and see where resources can go. A common case of leakage occurs when an insect that only eats Biomass requests 100 resources from an Eater, and because the Eater doesn't care what resources it grabs, it will just pull whatever is available. This means the Eater might end up pulling 60 Biomass and 40 Life Fluids. Because the insect that made the request only eats Biomass, the 40 Life Fluids will then be leaked. Filters can be used to help control leakage, just note that Filters do not effect things that bounce back. 