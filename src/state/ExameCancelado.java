package state;

import model.Exame;

public class ExameCancelado implements StatusExameState {
    @Override
    public void mudarEstadoExame(Exame exame) {
        System.out.println("Exame cancelado!");
    }

    @Override
    public void cancelarExame(Exame exame) {
        System.out.println("O exame já foi cancelado!");
    }

}
