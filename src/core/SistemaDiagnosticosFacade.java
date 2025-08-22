package core;
import java.util.Date;

import factories.ExameFactoryRegistry;
import factories.FabricaExame;
import model.Medico;
import model.Paciente;
import model.enums.Prioridade;
import model.exame.Exame;
import payments.DescontoStrategy;
import payments.ProcessadorPagamento;


public class SistemaDiagnosticosFacade {

    // Agendar exame
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

    // Processar exame
    public void processarExame(Exame exame) {
        exame.avancarEstado(); // solicitado -> processando
        exame.avancarEstado(); // processando -> concluído
    }

    // Pagar exame
    public void pagarExame(Exame exame, DescontoStrategy estrategia) {
        ProcessadorPagamento processador = new ProcessadorPagamento(exame, estrategia);
        processador.processarPagamento();
    } /* 
    //Gerar laudo
    public String gerarLaudo(Exame exame, String formato) {
        GeradorLaudo gerador = new GeradorLaudo();
        return gerador.gerarLaudo(exame, formato).gerarLaudoCompleto();
    }*/
}