package com.xmxe.study_demo.designpattern.proxy.staticproxy;

public class BuyHouseProxy implements BuyHouse {

	private BuyHouse bh = new BuyHouseImpl();
	
	@Override
	public void buyHouse() {
		System.out.println("before staticproxy");
		bh.buyHouse();
		System.out.println("after staticproxy");
	}

}

class BuyHouseImpl implements BuyHouse {

	@Override
	public void buyHouse() {
		System.out.println("this is BuyHouseImpl");
	}

}

interface BuyHouse {
	void buyHouse();
}

