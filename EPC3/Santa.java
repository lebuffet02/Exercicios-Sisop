import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class Santa extends Thread {
  private Semaphore santaSem, reindeerSem, mutex;
  private AtomicInteger elves, reindeer;

  public Santa(AtomicInteger elves, AtomicInteger reindeer, Semaphore santaSem, Semaphore reindeerSem,
      Semaphore mutex) {
    this.elves = elves;
    this.reindeer = reindeer;
    this.santaSem = santaSem;
    this.reindeerSem = reindeerSem;
    this.mutex = mutex;
  }

  private void prepareSleigh() {
    reindeer.set(0);
    System.out.println("The sleigh is ready");
  }

  private void helpElves() {
    System.out.println("The elves are being helped");
  }

  public void run() {
    try {
      while (true) {
        santaSem.acquire();
        mutex.acquire();

        if (reindeer.get() == 9) {
          this.prepareSleigh();
          reindeerSem.release(9);
        } else if (elves.get() == 3) {
          this.helpElves();
        }

        mutex.release();
      }
    } catch (Exception e) {}
  }
}
