package validators;

import model.Exame;
import model.Hemograma;
import model.Ressonancia;

public class ValidadorFactory {
    public static ValidadorExame criarValidador(Exame exame) {
        if (exame instanceof Hemograma) {
            return new ValidadorHemograma();
        } else if (exame instanceof Ressonancia) {
            return new ValidadorRessonancia();
        }
        throw new IllegalArgumentException("Tipo de exame nao suportado: " + exame.getClass().getSimpleName());
    }
}
