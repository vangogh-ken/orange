package com.van.halley.cycle.sample;

import com.van.halley.cycle.core.CycleFacade;

public class CycleTest {

	public static void main(String[] args) {
		CycleFacade cycle = new CycleFacade(10, 100);
		cycle.init();
		for (int i = 0; i < 20; i++) {
			CycleJobSample job = new CycleJobSample();
			cycle.run(job);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
