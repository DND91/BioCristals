package hok.chompzki.biocristals.croot.cristal;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;

public class MaterialGhost extends MaterialTransparent {
	
	public MaterialGhost() {
		super(MapColor.airColor);
		
		this.setReplaceable();
		this.setImmovableMobility();
	}
	
	public boolean isOpaque()
    {
        return false;
    }
}
