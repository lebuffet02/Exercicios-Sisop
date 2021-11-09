import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class Elf extends Thread {
  private Semaphore elfTex, mutex, santaSem;
  private AtomicInteger elves;
  private int id;

  public Elf(AtomicInteger elves, Semaphore elfTex, Semaphore mutex, Semaphore santaSem, int id) {
    this.elfTex = elfTex;
    this.elves = elves;
    this.mutex = mutex;
    this.santaSem = santaSem;
    this.id = id;
  }

  private void getHelp() {
    System.out.println("Elf " + this.id + " is being helped");
  }

  public void run() {
    try {
      elfTex.acquire();
      mutex.acquire();

      int currentElves = elves.incrementAndGet();
      if (currentElves == 3) {
        santaSem.release();
      } else {
        elfTex.release();
      }
      mutex.release();

      this.getHelp();

      mutex.acquire();

      currentElves = elves.decrementAndGet();
      if (currentElves == 0) {
        elfTex.release();
      }

      mutex.release();
    } catch (Exception e) {
    }
  }
}
