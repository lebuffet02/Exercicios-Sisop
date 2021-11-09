package EPC1-B;

import java.util.concurrent.Semaphore;

class Barreira {
	
	private Semaphore mutex;  private Semaphore catraca1; private Semaphore catraca2; private int cont; private int n; 
	              
	public Barreira(int nmax){ 	
		n = nmax; cont = 0;
        mutex = new Semaphore(1); catraca1 = new Semaphore(0); catraca2 = new Semaphore(1);	
	} 
		
	public void arrive() throws InterruptedException {
		mutex.acquire(); cont++;
		if (cont == n) {       
			catraca2.acquire();  catraca1.release();   
		}
		mutex.release();      
		catraca1.acquire();  catraca1.release();    
	}

	public void leave() throws InterruptedException {
		mutex.acquire(); cont--;
		if (cont == 0) {
			catraca1.acquire(); catraca2.release();	
		}
		mutex.release();
		catraca2.acquire(); catraca2.release();	
	}
}
