package com.entities.item;

import java.util.ArrayList;
import java.util.List;

public class StackInInv {

	public List<Item> items = new ArrayList<Item>();
	
	public Item item;
	
	public static StackInInv voidStack = new StackInInv(Item.voidItem);
	
	public StackInInv(Item item) {
		this.item = item;
		items.add(item);
	}
	
	public boolean addItem(Item item) {
		if(this.item == item) {
			items.add(item);
			return true;
		}
		return false;
	}

	public void removeItem(int amount) {
		if(amount < items.size()) {
			for(int i = 0; i < amount; i++) {
				items.remove(0);
			}
		} else if(amount == items.size()){
			item = Item.voidItem;
			items.add(item);
		}
	}
	
}
