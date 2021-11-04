import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class Reindeer extends Thread {
  private Semaphore santaSem, reindeerSem, mutex;
  private AtomicInteger reindeer;
  private int id;

  public Reindeer(AtomicInteger reindeer, Semaphore santaSem, Semaphore reindeerSem, Semaphore mutex, int id) {
    this.reindeer = reindeer;
    this.santaSem = santaSem;
    this.reindeerSem = reindeerSem;
    this.mutex = mutex;
    this.id = id;
  }

  private void getHitched() {
    System.out.println("Reindeer " + this.id + " is hitched");
  }

  public void run() {
    while (true) {
      try {
        mutex.acquire();

        int currentReindeers = reindeer.incrementAndGet();
        if (currentReindeers == 9) {
          santaSem.release();
        }

        mutex.release();

        reindeerSem.acquire();

        this.getHitched();
      } catch (Exception e) {}
    }
  }
}
