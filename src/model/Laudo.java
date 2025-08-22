package model;

import java.time.LocalDate;

import template.LaudoTemplate;

public abstract class Laudo {
    public static final String gerarNomeArquivo = null;
    protected LaudoTemplate modelo;

    public Laudo(LaudoTemplate modelo) {
        this.modelo = modelo;
    }

    public void gerar(Exame exame, Medico medico) {
        String nomeArquivo = gerarNomeArquivo(exame.getPaciente(), exame);
        String cabecalho = getCabecalho(exame);
        String corpo = gerarCorpo(exame); // <- cada subclasse implementa
        String rodape = "Medico Responsável: " + medico.getNome() + " (" + medico.getCrm() + ")";
        modelo.gerarLaudo(cabecalho, corpo, rodape, nomeArquivo);
    }

    // obrigatório na subclasse
    protected abstract String gerarCorpo(Exame exame);

    protected String gerarNomeArquivo(Paciente paciente, Exame exame) {
        String nome = paciente.getNome().toLowerCase().replace(" ", "_");
        String data = LocalDate.now().toString();
        return "laudo_" + nome + "_" + data;
    }

    protected String getCabecalho(Exame exame) {
        Paciente paciente = exame.getPaciente();
        return "Exame Nº: " + exame.getCodigo() + "\n" +
               "Paciente: " + paciente.getNome() + "\n" +
               "Convenio: " + paciente.getConvenio() + "\n" +
               "Medico Solicitante: " + exame.getMedico().getNome() + "\n" +
               "Data: " + exame.getDataSolicitacao();
    }
}
