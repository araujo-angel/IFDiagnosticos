package state;

import model.Exame;

public interface StatusExameState {
    public void mudarEstadoExame(Exame exame);
    public void cancelarExame(Exame exame);
} 
