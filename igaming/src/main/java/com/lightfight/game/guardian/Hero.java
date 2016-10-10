package com.lightfight.game.guardian;

import java.util.List;

public class Hero implements Refreshor {
	
	int[] totalAttributes;
	
	@Override
	public void refresh() {
		RefreshUtil.refresh(this);
	}
	
	public void refreshBaseAttributes(PlayerVO pvo) { // PlayerVO pvo
		// TODO base
		
		refreshTotalAttributes(pvo);
	}
	
	public void refreshTotalAttributes(PlayerVO pvo){
		// PlayerVO pvo
		
		List<Gem> gems = pvo.getGems();
		for (int i = 0; i < gems.size(); i++) {
			int[] gemAttributes = gems.get(i).getAttributes();
			totalAttributes[i] += gemAttributes[i];
		}
	}
	
	public void refreshConstructionAttributes(){
		
	}

}
