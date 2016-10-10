package com.lightfight.game.guardian;

public class Soldier implements Refreshor {

	@Override
	public void refresh() {
		RefreshUtil.refresh(this);
	}
}
