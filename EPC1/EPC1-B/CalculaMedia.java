package EPC1-B;

public class CalculaMedia<Barreira> extends Barreira  implements Thread {
    
    private int[][] matriz;
    private int media;
    private Barreira barreira;
    private int nrCelula; 
    private int celulaAtual = 0; 
    private int count = 1; 
    private int nrInteracoes;

    public CalculaMedia(int[][] matriz, Barreira barreira, int nrCelula, int nrInteracoes) {
        this.matriz = matriz;
        this.barreira = barreira;
        this.nrCelula = nrCelula;
        this.nrInteracoes = nrInteracoes;
    }

    @Override
    public void run() {
        
        while(this.count <= this.nrInteracoes) {
            //FASE 1 ****** INICIO
            try{
                barreira.arrive();
                this.celulaAtual = 0;
                for (int linha = 0; linha < this.matriz.length; linha++) {
                    for (int coluna = 0; coluna < this.matriz[linha].length; coluna++) {
                        
                        if(celulaAtual == nrCelula){
                            //Verifica se a posição não está nas bordas: Possui os 4 vizinhos.
                            if ((linha > 0 && linha < this.matriz.length - 1) && (coluna > 0 && coluna < this.matriz[linha].length - 1)){
                                int oeste = this.matriz[linha][coluna-1];
                                int leste = this.matriz[linha][coluna+1];
                                int norte = this.matriz[linha-1][coluna];
                                int sul = this.matriz[linha+1][coluna];
                                this.media = (oeste + leste + norte + sul) / 4;
                            }
    
                            //Primeira linha
                            if(linha == 0){
                                if((coluna == 0)){
                                    int leste = this.matriz[linha][coluna+1];
                                    int sul = this.matriz[linha+1][coluna];
                                    this.media = (leste + sul) / 2;
                                }
        
                                if((coluna == this.matriz.length - 1)){
                                    int oeste = this.matriz[linha][coluna-1];
                                    int sul = this.matriz[linha+1][coluna];
                                    this.media = (oeste + sul) / 2;
                                }
    
                                if((coluna != 0 && coluna != this.matriz.length - 1)){
                                    int oeste = this.matriz[linha][coluna-1];
                                    int leste = this.matriz[linha][coluna+1];
                                    int sul = this.matriz[linha+1][coluna];
                                    this.media = (oeste + leste + sul) / 3;
                                }
                            }
    
                            //Ultima linha
                            if(linha == this.matriz.length - 1){
                                if((coluna == 0)){
                                    int leste = this.matriz[linha][coluna+1];
                                    int norte = this.matriz[linha-1][coluna];
                                    this.media = (leste + norte) / 2;
                                }
        
                                if((coluna == this.matriz.length - 1)){
                                    int oeste = this.matriz[linha][coluna-1];
                                    int norte = this.matriz[linha-1][coluna];
                                    this.media = (oeste + norte) / 2;
                                }
    
                                if((coluna != 0 && coluna != this.matriz.length - 1)){
                                    int oeste = this.matriz[linha][coluna-1];
                                    int leste = this.matriz[linha][coluna+1];
                                    int norte = this.matriz[linha-1][coluna];
                                    this.media = (oeste + leste + norte) / 3;
                                }
                            }
    
                            if(linha != 0 && linha != this.matriz.length - 1){
                                if((coluna == 0)){
                                    int leste = this.matriz[linha][coluna+1];
                                    int norte = this.matriz[linha-1][coluna];
                                    int sul = this.matriz[linha+1][coluna];
                                    this.media = (leste + norte + sul) / 3;
                                }
    
                                if((coluna == this.matriz.length - 1)){
                                    int oeste = this.matriz[linha][coluna-1];
                                    int norte = this.matriz[linha-1][coluna];
                                    int sul = this.matriz[linha+1][coluna];
                                    this.media = (oeste + norte + sul) / 3;
                                }
                            }
                        }
                        celulaAtual++;
                    }
                }
            }
            catch(InterruptedException ie) {

            }
            try{
                barreira.leave();
                this.celulaAtual = 0;
                for (int linha = 0; linha < this.matriz.length; linha++) {
                    for (int coluna = 0; coluna < this.matriz[linha].length; coluna++) {
                        if(this.celulaAtual == nrCelula){
                            this.matriz[linha][coluna] = this.media;
                        }
                        this.celulaAtual++;
                    }
                }
                this.count++;
            }
            catch(InterruptedException ie){

            }
        }
    }
}
