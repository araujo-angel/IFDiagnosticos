package core;

import java.util.Date;

import factories.ExameFactoryRegistry;
import factories.FabricaExame;
import reports.template.*;
import model.Medico;
import model.Paciente;
import model.enums.Prioridade;
import model.exame.Exame;
import payments.DescontoStrategy;
import payments.ProcessadorPagamento;
import reports.GeradorLaudo;
import reports.template.LaudoTemplate;

public class SistemaDiagnosticosFacade {

    public Exame agendarExame(String tipoExame, String codigo, double valorBase,
                              Date dataSolicitacao, Prioridade prioridade,
                              Paciente paciente, Medico medico) {
        FabricaExame factory = ExameFactoryRegistry.getFactory(tipoExame);
        if (factory == null) {
            throw new IllegalArgumentException("Exame não suportado: " + tipoExame);
        }

        Exame exame = factory.criarExame(codigo, valorBase, dataSolicitacao, prioridade, paciente, medico);
        paciente.adicionarExame(exame);
        return exame;
    }

    public void processarExame(Exame exame) {
        exame.avancarEstado();
        exame.avancarEstado();
    }

    public void pagarExame(Exame exame, DescontoStrategy estrategia) {
        ProcessadorPagamento processador = new ProcessadorPagamento(exame, estrategia);
        processador.processarPagamento();
    }

    public String gerarLaudo(Exame exame, String formato, boolean printConsole) {
        LaudoTemplate template = LaudoFactoryRegistry.getTemplate(formato);
        GeradorLaudo gerador = new GeradorLaudo(template);

        String cabecalho = "Paciente: " + exame.getPaciente().getNome() + "\nMédico: " + exame.getMedico().getNome();
        String corpo = "Exame: " + exame.getClass().getSimpleName() + "\nPrioridade: " + exame.getPrioridade();
        String rodape = "Data: " + exame.getDataSolicitacao();

        return gerador.gerar(cabecalho, corpo, rodape, exame.getCodigo(), printConsole);
    }
}
