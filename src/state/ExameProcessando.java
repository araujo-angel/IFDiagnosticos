package state;

import model.Exame;

public class ExameProcessando implements StatusExameState {
    @Override
    public void mudarEstadoExame(Exame exame) {
        System.out.println("Exame processando");
        exame.setEstado(new ExameConcluido());
    }
    
    @Override 
    public void cancelarExame(Exame exame) {
        exame.setEstado(new ExameCancelado());
    }
}
