package factories;
import java.util.Date;
import model.Exame;
import model.Medico;
import model.Paciente;
import model.Prioridade;
import model.Ressonancia;


public class FabricaRessonancia implements FabricaExame {
    @Override
    public Exame criarExame(String codigo, double valorBase, Date dataSolicitacao, 
                            Prioridade prioridade, Paciente paciente, Medico medico) {
        return new Ressonancia(codigo, valorBase, dataSolicitacao, prioridade, paciente, medico);
    }
}
