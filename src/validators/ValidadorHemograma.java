package validators;

import model.Exame;
import model.Hemograma;

public class ValidadorHemograma implements ValidadorExame {
    @Override
    public boolean validar(Exame exame) {
        if (!(exame instanceof Hemograma)) {
            return false;
        }
        Hemograma hemograma = (Hemograma) exame;
        return hemograma.getHemoglobina() > 0 && hemograma.getLeucocitos() > 0;
    }
}
