import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class SantaProblem {
  final static int MAX_REINDEERS = 9;
  final static int MAX_ELVES = 12;

  public static void main(String[] args) {
    AtomicInteger elves = new AtomicInteger(0);
    AtomicInteger reindeers = new AtomicInteger(0);
    Semaphore santaSem = new Semaphore(0);
    Semaphore reindeerSem = new Semaphore(0);
    Semaphore elfTex = new Semaphore(1);
    Semaphore mutex = new Semaphore(1);

    ArrayList<Thread> threads = new ArrayList<Thread>();

    Santa santa = new Santa(elves, reindeers, santaSem, reindeerSem, mutex);
    threads.add(santa);

    for (int i = 0; i < MAX_ELVES; i++) {
      Elf elf = new Elf(elves, elfTex, mutex, santaSem, i + 1);
      threads.add(elf);
    }

    for (int i = 0; i < MAX_REINDEERS; i++) {
      Reindeer reindeer = new Reindeer(reindeers, santaSem, reindeerSem, mutex, i + 1);
      threads.add(reindeer);
    }

    for (Thread t : threads) {
      t.start();
    }
  }
}