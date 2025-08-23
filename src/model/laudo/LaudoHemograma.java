package model.laudo;
import model.enums.Sexo;
import model.exame.Exame;
import model.exame.Hemograma;
import reports.template.LaudoTemplate;
import validators.ValidadorHemograma;

public class LaudoHemograma extends Laudo {
    private ValidadorHemograma validador = new ValidadorHemograma();

    public LaudoHemograma(LaudoTemplate modelo) {
        super(modelo);
    }
    
    @Override
    protected String getNomeExame() {
        return "Hemograma";
    }

    @Override
    protected String gerarCorpo(Exame exame) {
        if (!(exame instanceof Hemograma)) {
            throw new IllegalArgumentException("Esse Exame nao e um Hemograma!");
        }

        Hemograma h = (Hemograma) exame;

        if (!validador.validar(exame).isEmpty()) {
            return "Dados invalidos para o exame de Hemograma.";
        }

        StringBuilder corpo = new StringBuilder();
        corpo.append("RESULTADOS DO HEMOGRAMA:\n");
        corpo.append("------------------------------------------------------------\n\n");

        corpo.append("HEMOGLOBINA: ").append(h.getHemoglobina()).append(" g/dL\n");
        corpo.append("Valores de referência: ");
        corpo.append(h.getPaciente().getSexo() == Sexo.MASCULINO ? "13.8 a 17.2 g/dL (adultos)\n" : "12.1 a 15.1 g/dL (adultos)\n");
        corpo.append("Status: ").append(validador.getStatusHemoglobina(h.getHemoglobina(), h.getPaciente().getSexo())).append("\n\n");

        corpo.append("LEUCÓCITOS: ").append(h.getLeucocitos()).append(" células/mm³\n");
        corpo.append("Valores de referência: 4.500 a 11.000 células/mm³\n");
        corpo.append("Status: ").append(validador.getStatusLeucocitos(h.getLeucocitos())).append("\n\n");

        corpo.append("HEMATÓCRITO: ").append(h.getHematocrito()).append(" %\n");
        corpo.append("Valores de referência: ");
        corpo.append(h.getPaciente().getSexo() == Sexo.MASCULINO ? "40% a 54% (adultos)\n" : "36% a 48% (adultos)\n");
        corpo.append("Status: ").append(validador.getStatusHematocrito(h.getHematocrito(), h.getPaciente().getSexo())).append("\n\n");

        corpo.append("PLAQUETAS: ").append(h.getPlaquetas()).append(" plaquetas/mm³\n");
        corpo.append("Valores de referência: 150.000 a 450.000 plaquetas/mm³\n");
        corpo.append("Status: ").append(validador.getStatusPlaquetas(h.getPlaquetas())).append("\n");

        return corpo.toString();
    }
}