package model;

import template.LaudoTemplate;
import validators.ValidadorHemograma;

public class LaudoHemograma extends Laudo {
    private ValidadorHemograma validador = new ValidadorHemograma();

    public LaudoHemograma(LaudoTemplate modelo) {
        super(modelo);
    }

    @Override
    protected String gerarCorpo(Exame exame) {
        if (!(exame instanceof Hemograma)) {
            throw new IllegalArgumentException("Esse Exame não é um Hemograma!");
        }

        // Validação
        if (!validador.validar(exame)) {
            return "❌ Dados inválidos para o exame de Hemograma.";
        }

        Hemograma h = (Hemograma) exame;

        StringBuilder corpo = new StringBuilder();
        corpo.append("Hemoglobina: ").append(h.getHemoglobina()).append(" g/dL\n");
        corpo.append("Valores de referência: 12 a 16 g/dL\n\n");

        corpo.append("Leucócitos: ").append(h.getLeucocitos()).append(" /mm³\n");
        corpo.append("Valores de referência: 4.000 a 11.000 /mm³\n");

        return corpo.toString();
    }
}

