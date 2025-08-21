package factories;

import model.Exame;
import model.Medico;
import model.Paciente;

public interface FabricaExame {
    Exame criarExame(Paciente paciente, Medico medico);
}
