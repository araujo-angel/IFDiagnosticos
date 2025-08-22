package validators;

import model.Exame;
import java.util.ArrayList;
import java.util.List;

public abstract class ValidadorBase implements ValidadorExame {

    protected List<String> validarExameBase(Exame exame) {
        List<String> erros = new ArrayList<>();
        if (exame.getCodigo() == null || exame.getCodigo().isBlank())
            erros.add("Código do exame não pode ser vazio");
        if (exame.getValorBase() <= 0)
            erros.add("Valor base do exame deve ser maior que 0");
        if (exame.getDataSolicitacao() == null)
            erros.add("Data de solicitação não pode ser nula");
        if (exame.getPaciente() == null)
            erros.add("Paciente não pode ser nulo");
        if (exame.getMedico() == null)
            erros.add("Médico não pode ser nulo");
        return erros;
    }
}

