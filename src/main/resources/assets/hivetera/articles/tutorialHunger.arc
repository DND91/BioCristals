<summary>
I have no clue how you did this mate... good job!

<body><bold>Tutorial For Hunger Network<end>
<bold> ~ Introduction ~ <end>
The Hunger Portal opens into a pocket space inside The Hunger (The Hunger is not the same as the player's hunger).
As a player, you uses these pockets for different kinds of networks.
In the beginning it's only used a power network.
These networks are used to sustains everything from nesting blocks to insects like the Void Crawler.
<NP><bold> ~ Resources ~ <end>
Inside if The Hunger there exist six resources. These resources 
functions to power diffrent kinds of insects. Diffrent insects 
eats diffrent things so to speak. These resources are...
<tab><bold>Raw Food<end>
A first tier resource that is dirrectly extracted from food and waste. 
Insects that eat this resource can also extract it, but are very inefficent.
Use the Sacrifice Pit for efficiency. The resource is consumed by basic insects.
<tab><bold>Biomass<end>
A second tier resource that is transformed from 
raw food. It's consumed by diligent insects. For example Fruit Spider
<tab><bold>Waste<end>
A none tier resource that is comes from the transormation of 
anything as a byproduct. The token transformer can be set to 
only produce waste from biomass. It's consumed by really desperate insects. 
For example Krakens. 
<tab><bold>Psychic Energy<end>
A third tier resource that is extracted from biomass. One of 
the more expensive resources to work with. It's is consumed by 
insects that uses pschic powers, for example Void Crawler.
<tab><bold>Life Fluids<end>
A third tier resource that is extracted from biomass. 
It's is consumed by insects that either lives in the water or 
focuses on production. For example Kitteh Beetle.
<tab><bold>Nuritment<end>
A third tier resource that is extracted from biomass. 
It's is consumed by insects that do not fit into any 
other of the above categories. For example UNKOWN.
<NP><bold> ~ Tokens ~ <end>
Tokens that are banks, bridges (conduits), filters, 
transformers, feeders (input) and eaters (output). 
They are the basic parts of a Hunger Network and will 
in many ways function as a central aspect.
Inside a Hunger Portal you will find two areas. The 
one with darker background is for your network, while 
the right side is for storing tokens.
Tokens can only connect to other tokens horizontally and vertically 
placed in a network. 
<NP><bold> ~ Sacrifice Pit & Bank ~ <end>
The basic network consists of two parts: Sacrifice pit & 
a bank that can store raw food. You place the bank in the 
bottom slot of the sacrifice pit and then food in the top. 
Over time, food will be converted into raw food and stored 
in the bank.
When the bank have some raw food it can be used inside of 
diffrent blocks as a power source, if that block eats raw food. 
It can also be stored in the players inventory to feed any insects. 
<NP><bold> ~ Simple Network ~ <end>
A simple network consists of five parts: Sacrifice pit, feeder, bank, 
eater and a consumer block (Ex. Nest). Inside the sacrifice pit 
place one feeder (remmember to bind it) in the bottom slot. 
Inside the Hunger Portal, place a feeder, a bank and a eater 
next to eachother. Like this:
<ITEMSTACK name=itemFeeder domain=Hivetera><ITEMSTACK name=itemBank domain=Hivetera><ITEMSTACK name=itemEater domain=Hivetera>
Then place a eater bound to you inside a consumer block. The 
block will drain resources as needed.
<NP><bold> ~ Network ~ <end>
Resources will pass from feeder to bank and transformer will 
act on the resources. Changing one resource type to the other and 
create a byproduct. Eater will drain resources from the bank and 
pass the filter on the way. The filter will send back a filtired 
resource to the bank. 
<bold>Note:<end> Feeders and eaters don't look over banks! 
This won't work:
<ITEMSTACK name=itemFeeder domain=Hivetera><ITEMSTACK name=itemBank domain=Hivetera><ITEMSTACK name=itemBank domain=Hivetera><ITEMSTACK name=itemEater domain=Hivetera>
The feeder will take from the closest bank and so does the eater. Resources in this setup 
dosn't flow from feeder to eater. Only feeder to the first bank and eater will drain from 
second bank. Feeders dump into banks and eaters drain from banks.
<NP><bold> ~ Even drain and feed ~ <end>
Feeders and eaters will drain and feed banks evenly. This means that 
if you connect three banks to a feeder, it will evenly divde 
resources over those banks. It can pass diffrent paths to reach each 
of the banks. This means that if you feed the system 90 it will divide 
over the three paths leading to each bank. Each bank will gain 30.
If you connect three banks to a eater (that contains 100, 20, 100 of a resource), 
it will drain evenly. This means that if something drains 90. 
30 will be drained from each bank. One of the banks only have 20. 
The eater will drain it all. This means that we drain 90, but get 80. 
<NP><bold> ~ Example Network ~ <end>
EXAMPLE!
<NP><bold> ~ Channeles ~ <end>
Tokens can be attuned to diffrent channels by coloring. 
When a token is attuned it will only connect to its own color 
and tokens without color. Colorless tokens will connect to any 
token. Example:
EXAMPLE
<NP><bold> ~ Leakage & Drawback ~ <end>
Leakage happens when either a eater drains resources that are not used or 
when a feeder has resources sent back that it cannot handle. 
The more that leaks the worse are the effects. First players around a leak 
will be subject to potion effects, if it gets worse mobs and animals will start 
to spawn. Best way to find leaks are to study resource flows and see where resources 
can go. Filters do not effect things that bounce back. 




