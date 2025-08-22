package validators;

import model.Exame;
import model.Ressonancia;
import model.FaixaEtaria;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class ValidadorRessonancia extends ValidadorBase {

    private final List<String> areasValidas = Arrays.asList(
        "cerebro", "coluna", "joelho", "torax", "abdomen"
    );

    @Override
    public List<String> validar(Exame exame) {
        List<String> erros = new ArrayList<>();

        if (!(exame instanceof Ressonancia)) {
            erros.add("Exame não é do tipo Ressonância");
            return erros;
        }

        Ressonancia r = (Ressonancia) exame;

        // Validar atributos base
        erros.addAll(validarExameBase(r));

        // Área do corpo
        if (r.getAreaCorpo() == null || r.getAreaCorpo().isBlank()) {
            erros.add("Área do corpo não pode ser vazia");
        } else if (!areasValidas.contains(r.getAreaCorpo().toLowerCase())) {
            erros.add("Área do corpo inválida: " + r.getAreaCorpo());
        }

        // Contraste
        if (r.getComContraste() && r.getPaciente().getFaixaEtaria() == FaixaEtaria.CRIANCA) {
            erros.add("Pacientes crianças não podem fazer ressonância com contraste");
        }

        return erros;
    }
}
