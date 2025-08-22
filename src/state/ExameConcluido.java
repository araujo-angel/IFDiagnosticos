package state;

import model.Exame;

public class ExameConcluido implements StatusExameState {
    @Override
    public void mudarEstadoExame(Exame exame) {
        System.out.println("Exame concluído");
    }
        
    @Override 
    public void cancelarExame(Exame exame) {
        System.out.println("Exame já concluído, não pode ser finalizado!");
    }
}
