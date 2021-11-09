package EscritoresLeitores;

import java.util.concurrent.Semaphore;

class EscritoresLeitores2 {

    static Semaphore readLock = new Semaphore(1, true);
    static Semaphore writeLock = new Semaphore(1, true);
    volatile static int readCount = 0;

    static class Le implements Runnable {
        @Override
        public void run() {
            try {
                readLock.acquire();
                readCount++;
                if (readCount == 1) {
                    ;                   //EVITA A POSTERGAÇÃO
                }
                readLock.release();
                System.out.println(Thread.currentThread().getName() + " Lendo. ");
                Thread.sleep(1500);
                System.out.println(Thread.currentThread().getName() + " Terminou de Ler. ");
                readLock.acquire();
                readCount--;
                if(readCount == 0) {
                    ;                   //EVITA A POSTERGAÇÃO
                }
                readLock.release();
            } 
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Escreve implements Runnable {
        @Override
        public void run() {
            try {
                writeLock.acquire();
                System.out.println(Thread.currentThread().getName() + " Está Escrevendo");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " has finished WRITING");
                writeLock.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Le read = new Le();
        Escreve write = new Escreve();
        Thread t1 = new Thread(read);
        t1.setName("1 ");
        Thread t2 = new Thread(read);
        t2.setName("2 ");
        Thread t3 = new Thread(write);
        t3.setName("3 ");
        Thread t4 = new Thread(read);
        t4.setName("4 ");
        t1.start();
        t3.start();
        t2.start();
        t4.start();
    }
}