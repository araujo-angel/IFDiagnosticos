package validators;

import model.Exame;
import model.Hemograma;
import model.Sexo;

import java.util.ArrayList;
import java.util.List;

public class ValidadorHemograma extends ValidadorBase {

    @Override
    public List<String> validar(Exame exame) {
        List<String> erros = new ArrayList<>();

        if (!(exame instanceof Hemograma)) {
            erros.add("Exame não é do tipo Hemograma");
            return erros;
        }

        Hemograma h = (Hemograma) exame;

        erros.addAll(validarExameBase(h));

        double hmin = 12, hmax = 18;
        if (h.getPaciente().getSexo() == Sexo.FEMININO) {
            hmax = 16;
        }
        double lmin = 4000, lmax = 11000;

        if (h.getHemoglobina() < hmin || h.getHemoglobina() > hmax)
            erros.add("Hemoglobina fora da faixa normal: " + h.getHemoglobina());
        if (h.getLeucocitos() < lmin || h.getLeucocitos() > lmax)
            erros.add("Leucocitos fora da faixa normal: " + h.getLeucocitos());

        return erros;
    }
}