public class MatrizRandom {
    
    private int[][] matriz;
    private int[][] tamMatriz;
    private final int maxSize = 1000;
    private final int minSize = 1;

    public MatrizRandom(int matriz) {
        this.matriz = matriz;
        this.tamMatriz = new int[matriz][matriz];
    }

    public int Matriz() {
        for (int i= 0; i < matriz.length; i++) {
            for (int j=0; j < matriz.length-1; j++) {
                this.matriz[i][j] = (int) Math.ceil();
            }
        }
    }

    public int[][] getMatriz() {
        return matriz;    
    }

    public int getprintMatriz(int iterator) {
        System.out.println("\n");
        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz[i].length; j++) {
                System.out.print("\t"+this.matriz[i][j] + " | ");
            }  
        }
        System.out.println();
        return iterator;  
    }

    public class PrintMatriz extends Thread {

        private Matriz matriz;
        private Barreira  barreira;
        private int count = 0;
        private int nrInteracoes;

        public PrintMatriz(Matriz matriz, Barreira barreira, int numInteracoes) {
            this.matriz = matriz;
            this. barreira = barreira;
            this.numInteracoes = numInteracoes;
        }
    
        @Override
        public void run(){
            
            while(this.count <= nrInteracoes) {
                //FASE 1 ****** INICIO
                try{ 
                    barreira.arrive();
                    this.matriz.exibeMatriz(this.count);
                }
                catch(InterruptedException ie) {
    
                }
                try { 
                    barreira.leave();
                    this.cont++;
                }
                catch(InterruptedException ie){}
            }
        }
    }
}