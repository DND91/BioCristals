<summary>
<bold>Token Eater<end>
- Crafting Recipe -
<tab>
<body><bold>Token Eater<end>
The Eater, obvious to it's name, will eat resources from The Hunger. Note that it will always divide what resources it drains evenly between connected banks. It won't care to fill the given quota; it will just try to drain a certain amount from each bank. This means that if an insect or block requests 100 resources, the Eater will not care what type of resource the insect or block uses, and will just grab whatever is available. In general, the Eater functions as follows; blocks or insects will request resources from the Eater, and the Eater pulls resources from connected banks in the network. The resources pulled from the banks will take the shortest path in the network to get to the Eater. Any Tokens that are on the path the resources are traveling can act on the resources being pulled. Resources that are stopped by filters will bounce back to the bank. Any resources that are pulled from the bank and not used will leak into the atmosphere. Unfortunately, when resources vanish in this way, it can cause negative consequences... (details on how and why this happens are mentioned in <bold>Tutorial: the Hunger<end>)
Standard costs:
<ITEMSTACK name=itemChitinPlate size=4 domain=Hivetera><ITEMSTACK name=itemKraKenBug size=4 domain=Hivetera><ITEMSTACK name=wheat size=4 domain=minecraft>