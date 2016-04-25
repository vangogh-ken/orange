package com.zen.junit.test;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 ReentrantLock 互斥锁，可以对操作进行锁定；
 * tryLock, tryLock(long L, TimeUtil timeUtil)可以选择一直等待获取锁还是设置一个等待的超时时间
 * 
 * @author anxinxx
 *
 */
public class TestConcurrent {
	final ReentrantLock lock = new ReentrantLock();
	public void write(){
		if(!lock.isLocked()){
			lock.lock();
			System.out.println(Thread.currentThread().getName() +  " 开始写入");
			System.out.println(Thread.currentThread().getName() + " " + new Date());
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() +  " 完成写入");
			System.out.println(Thread.currentThread().getName() + " " + new Date());
			lock.unlock();
		}
	}
	
	public void read(){
		/*try {
			lock.tryLock(11, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		//if(!lock.isLocked()){
		/*synchronized (lock) {
			System.out.println(Thread.currentThread().getName() + " 开始读取");
			System.out.println(Thread.currentThread().getName() + " " + new Date());
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " 完成读取");
			System.out.println(Thread.currentThread().getName() + " " + new Date());
		}*/
		//lock.tryLock();
		if(lock.isLocked()){
			System.out.println(Thread.currentThread().getName() +  " 有任务在处理");
		}
			lock.lock();
			System.out.println(Thread.currentThread().getName() + " 开始读取");
			System.out.println(Thread.currentThread().getName() + " " + new Date());
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " 完成读取");
			System.out.println(Thread.currentThread().getName() + " " + new Date());
			lock.unlock();
		//}
		
	}
	
	public static void main(String[] args) {
		TestConcurrent test = new TestConcurrent();
		new Thread(new Runnable() {
			@Override
			public void run() {
				test.read();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				test.read();
			}
		}).start();
	}
	
	class W implements Runnable{
		private TestConcurrent test;
		
		public W(TestConcurrent test){
			this.test = test;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			test.read();
		}
		
	}
}
