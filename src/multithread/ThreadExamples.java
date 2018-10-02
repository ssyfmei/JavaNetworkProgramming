package multithread;

import java.util.concurrent.TimeUnit;

public class ThreadExamples implements Runnable{
	
	private String greeting;
	
	public ThreadExamples(String greeting) {
		this.greeting = greeting;
	}
	
	@Override
	public void run() {
		while(true) {
			System.out.println(Thread.currentThread().getName() + ": " + greeting);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new Thread(new ThreadExamples("Hello")).start();
		new Thread(new ThreadExamples("Aloha")).start();
		new Thread(new ThreadExamples("Ciao")).start();
	}
}
