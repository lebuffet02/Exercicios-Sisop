/*
   Implementacao do Jantar dos Filosofos com Semaforo
   PUCRS - Escola Politecnica
   Prof: Fernando Dotti
*/
import java.util.concurrent.Semaphore;

// ============= Filosofo ==============
class Filosofo extends Thread {

    private int i;
    private Semaphore g1, g2;
    private String espaco;

    public Filosofo(int _i, Semaphore _g1, Semaphore _g2){
        i = _i;   g1 = _g1;    g2 = _g2;
        espaco = "  ";
        for (int k=0; k<i; k++){
            espaco = espaco + "                       ";
        }
    }

    public void run() {
        while (true) {
            // pensa
            System.out.println(espaco+ i + ": Pensa ");

            // pega um garfo
            try{g1.acquire();
            }catch(InterruptedException ie){}
            System.out.println(espaco+ i + ": Pegou um ");

            // pega outro garfo
            try{g2.acquire();
            }catch(InterruptedException ie){}

            System.out.println(espaco+ i + ": Pegou dois, come ");
            // come
            // solta garfos
            g1.release();
            g2.release();
        }
    }
}

class FilosofoDestroCanhoto extends Thread {

    private int i;
    private boolean isDestro;
    private Semaphore garfoDireito, garfoEsquerdo;
    private String espaco;

    public FilosofoDestroCanhoto(int _i, Semaphore _g1, Semaphore _g2, boolean _isDestro){
        i = _i;   garfoDireito = _g1;    garfoEsquerdo = _g2; this.isDestro = _isDestro;
        espaco = "  ";
        for (int k=0; k<i; k++){
            espaco = espaco + "                       ";
        }
    }

    private void pegaGarfosDestro() {
        try{garfoDireito.acquire();
        }catch(InterruptedException ie){}
        System.out.println(espaco+ i + ": Pegou um ");

        // pega outro garfo
        try{
            garfoEsquerdo.acquire();
        }catch(InterruptedException ie){}
    }

    private void pegaGarfosCanhoto() {
        try{garfoEsquerdo.acquire();
        }catch(InterruptedException ie){}
        System.out.println(espaco+ i + ": Pegou um ");

        // pega outro garfo
        try{
            garfoDireito.acquire();
        }catch(InterruptedException ie){}
    }

    public void run() {
        while (true) {
            // pensa
            System.out.println(espaco+ i + ": Pensa ");

           //pega garfos
            if(this.isDestro){
                pegaGarfosDestro();
            } else {
                pegaGarfosCanhoto();
            }

            System.out.println(espaco+ i + ": Pegou dois, come ");
            // come
            // solta garfos
            garfoDireito.release();
            garfoEsquerdo.release();
        }
    }
}

class JantaFilosofos {

    private static void rodarComDeadlock() {
        int FIL = 5;

        Semaphore[] garfo = new Semaphore[FIL];
        for (int i=0; i< FIL; i++) {
            garfo[i]= new Semaphore(1);
        }
        for (int i = 0; i < FIL; i++) {
            boolean isDestro = i == FIL - 1;

            (new Filosofo(i,garfo[i],garfo[(i+1)%(FIL)])).start();
        }
    }

    // 4 Filósofos canhotos e 1 destro;
    private static void rodarPrimeiraSolucao() {
        int FIL = 5;

        Semaphore[] garfo = new Semaphore[FIL];
        for (int i=0; i< FIL; i++) {
            garfo[i]= new Semaphore(1);
        }
        for (int i = 0; i < FIL; i++) {
            boolean isDestro = i == FIL - 1;

            (new FilosofoDestroCanhoto(i,garfo[i],garfo[(i+1)%(FIL)], isDestro)).start();
        }
    }

    // Ao menos um destro e um canhoto
    private static void rodarSegundaSolucao() {
        int FIL = 5;

        Semaphore[] garfo = new Semaphore[FIL];
        for (int i=0; i< FIL; i++) {
            garfo[i]= new Semaphore(1);
        }

        boolean temFilosofoDestro = false;
        boolean temFilosofoCanhoto = false;

        for (int i = 0; i < FIL; i++) {
            boolean isDestro;
            if(!temFilosofoDestro) {
                isDestro = true;
                temFilosofoDestro = true;
            } else if (!temFilosofoCanhoto) {
                isDestro = false;
                temFilosofoCanhoto = true;
            }
            else {
                isDestro = Math.random() > 0.5;
            }
            (new FilosofoDestroCanhoto(i,garfo[i],garfo[(i+1)%(FIL)], isDestro)).start();
        }
    }

    // Só descomentar a solução desejada para rodar.
    public static void main(String[] args) {
//        rodarComDeadlock();
        rodarPrimeiraSolucao();
//        rodarSegundaSolucao();
    }
}

