package model.laudo;

import java.time.LocalDate;

import model.Medico;
import model.Paciente;
import model.exame.Exame;
import reports.template.LaudoTemplate;

public abstract class Laudo {
    public static final String gerarNomeArquivo = null;
    protected LaudoTemplate modelo;

    public Laudo(LaudoTemplate modelo) {
        this.modelo = modelo;
    }

    public void gerar(Exame exame, Medico medico) {
        String nomeArquivo = gerarNomeArquivo(exame.getPaciente(), exame);
        String cabecalho = getCabecalho(exame);
        String corpo = gerarCorpo(exame); // implementado por cada subclasse
        String rodape = "Medico Responsável: " + medico.getNome() + " (" + medico.getCrm() + ")";
        modelo.gerar(cabecalho, corpo, rodape, nomeArquivo, true);
    }

    protected abstract String gerarCorpo(Exame exame);

    protected String gerarNomeArquivo(Paciente paciente, Exame exame) {
        String nome = paciente.getNome().toLowerCase().replace(" ", "_");
        String data = LocalDate.now().toString();
        return "laudo_" + nome + "_" + data;
    }

    protected String getCabecalho(Exame exame) {
        Paciente paciente = exame.getPaciente();
        return "============================================\n" +
               "IF DIAGNOSTICOS - LAUDO MÉDICO\n" +
               "============================================\n" +
               "Exame: " + getNomeExame() + "\n" +
               "Código do Exame: " + exame.getCodigo() + "\n" +
               "Data do Exame: " + exame.getDataSolicitacao() + "\n" +
               "------------------------------------------------------------\n" +
               "PACIENTE: " + paciente.getNome() + "\n" +
               "Idade: " + paciente.getIdade() + " anos | Sexo: " + paciente.getSexo() + "\n" +
               "Convênio: " + paciente.getConvenio() + "\n" +
               "Médico Solicitante: " + exame.getMedico().getNome() + "\n" +
               "============================================\n";
    }

    protected String getRodape(Medico medicoResponsavel) {
        return "\n============================================\n" +
               "CONCLUSÃO:\n" +
               "------------------------------------------------------------\n" +
               "Exame realizado dentro dos parâmetros de normalidade.\n" +
               "============================================\n" +
               "Médico Responsável: " + medicoResponsavel.getNome() + "\n" +
               "CRM: " + medicoResponsavel.getCrm() + "\n" +
               "Data de Emissão: " + LocalDate.now() + "\n" +
               "============================================\n";
    }

    protected abstract String getNomeExame();
}
