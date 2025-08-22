package factories;

import java.util.Date;

import model.Exame;
import model.Hemograma;
import model.Medico;
import model.Paciente;
import model.Prioridade;

public class FabricaHemograma implements FabricaExame {
    @Override
    public Exame criarExame(String codigo, double valorBase, Date dataSolicitacao, 
                            Prioridade prioridade, Paciente paciente, Medico medico) {
        return new Hemograma(codigo, valorBase, dataSolicitacao, prioridade, paciente, medico);
    }
}
