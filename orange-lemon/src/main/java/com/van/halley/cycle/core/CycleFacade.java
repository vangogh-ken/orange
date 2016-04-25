package com.van.halley.cycle.core;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CycleFacade {
	private static Logger LOG = LoggerFactory.getLogger(CycleFacade.class);
	private CycleStore cycleStore;
	private CycleConsumer cycleConsumer;
	
	public CycleFacade(){
		cycleStore = new CycleStore();
		cycleConsumer = new CycleConsumer();
	}

	public CycleFacade(int consumerThreadCount, int thresholdJobCount){
		cycleStore = new CycleStore();
		cycleConsumer = new CycleConsumer(consumerThreadCount, thresholdJobCount);
	}
	
	/**
	 * 获取队列中的jobs
	 * @return
	 */
	public List<CycleJob> getActiveJobs(){
		BlockingQueue<CycleJob> queue = this.cycleStore.getQueue();
		return Arrays.asList(queue.toArray(new CycleJob[queue.size()]));
	}

	@PostConstruct
	public void init() {
		cycleStore.start();
		cycleConsumer.setCycleStore(cycleStore);
		cycleConsumer.start();
	}

	@PreDestroy
	public void close() {
		cycleConsumer.stop();
		cycleStore.stop();
	}
	
	public void run(CycleJob cycleJob){
		cycleStore.put(cycleJob);
		if(LOG.isDebugEnabled()){
			LOG.info("Catch a job");
		}
	}
}
