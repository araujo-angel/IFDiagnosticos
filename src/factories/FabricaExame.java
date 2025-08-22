package factories;

import model.Exame;
import model.Medico;
import model.Paciente;
import model.Prioridade;

import java.util.Date;

public interface FabricaExame {
    Exame criarExame(String codigo, double valorBase, Date dataSolicitacao, 
                     Prioridade prioridade, Paciente paciente, Medico medico);
}
