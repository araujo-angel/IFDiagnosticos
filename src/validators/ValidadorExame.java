package validators;

import java.util.List;

import model.Exame;

public interface ValidadorExame {
    List<String> validar(Exame exame);

}