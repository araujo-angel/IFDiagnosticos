package model.laudo;
import java.util.List;
import model.exame.Exame;
import model.exame.Ressonancia;
import reports.template.LaudoTemplate;
import validators.ValidadorRessonancia;

public class LaudoRessonancia extends Laudo {
    private ValidadorRessonancia validador = new ValidadorRessonancia();

    public LaudoRessonancia(LaudoTemplate modelo) {
        super(modelo);
    }

    @Override
    public String gerarCorpo(Exame exame) {
        if (!(exame instanceof Ressonancia)) {
            throw new IllegalArgumentException("Exame nao e Ressonancia!");
        }

        if (!validador.validar(exame).isEmpty()) {
            return " Dados invalidos para o exame de Ressonancia.";
        }

        Ressonancia r = (Ressonancia) exame;
        StringBuilder corpo = new StringBuilder();
        corpo.append("PROCEDIMENTO REALIZADO:\n");
        corpo.append("------------------------------------------------------------\n");
        corpo.append("Ressonância Magnética de ").append(r.getAreaCorpo()).append("\n");
        corpo.append("Uso de contraste: ").append(r.getComContraste() ? "SIM" : "NÃO").append("\n\n");
        
        corpo.append("DESCRIÇÃO:\n");
        corpo.append("------------------------------------------------------------\n");
        corpo.append("Foram obtidas imagens multiplanares e multisequenciais da região.\n");
        corpo.append("Técnica: Estudo realizado em equipamento de 1.5 Tesla.\n\n");
        
        corpo.append("ACHADOS:\n");
        corpo.append("------------------------------------------------------------\n");
        corpo.append("- Imagens sem evidências de alterações significativas\n");
        corpo.append("- Arquitetura preservada\n");
        corpo.append("- Sinais inflamatórios não identificados\n\n");
        
        corpo.append("OBSERVAÇÕES TÉCNICAS:\n");
        corpo.append("------------------------------------------------------------\n");
        corpo.append("Exame realizado conforme protocolo padrão da clínica.\n");
        corpo.append("Tempo de aquisição: aproximadamente 30 minutos.\n");

        return corpo.toString();
    }

    @Override
    protected String getNomeExame() {
        return "Ressonancia";
    }
}
