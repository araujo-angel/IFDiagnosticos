package model;

import template.LaudoTemplate;
import validators.ValidadorRessonancia;

public class LaudoRessonancia extends Laudo {
    private ValidadorRessonancia validador = new ValidadorRessonancia();

    public LaudoRessonancia(LaudoTemplate modelo) {
        super(modelo);
    }

    @Override
    protected String gerarCorpo(Exame exame) {
        if (!(exame instanceof Ressonancia)) {
            throw new IllegalArgumentException("Exame não é Ressonância!");
        }

        // Validação
        if (!validador.validar(exame)) {
            return " Dados inválidos para o exame de Ressonância.";
        }

        Ressonancia r = (Ressonancia) exame;

        StringBuilder corpo = new StringBuilder();
        corpo.append("Área examinada: ").append(r.getAreaCorpo()).append("\n");
        corpo.append("Com contraste: ").append(r.getComContraste() ? "Sim" : "Não").append("\n");

        if (r.getComContraste()) {
            corpo.append("Foi utilizado contraste para melhor definição da imagem.\n");
        } else {
            corpo.append("Sem uso de contraste.\n");
        }
        return corpo.toString();
    }
}
