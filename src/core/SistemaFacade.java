package core;

import java.util.List;

import model.exame.Exame;

public class SistemaFacade {
    private CargaDadosFacade carga = new CargaDadosFacade();
    private ExameFacade exameFacade = new ExameFacade();
    private ProcessamentoFacade procFacade = new ProcessamentoFacade();
    private LaudoFacade laudoFacade = new LaudoFacade();
    private NotificacaoFacade notificacaoFacade = new NotificacaoFacade();

    public void executarFluxo(String caminhoCsv) {
        List<Exame> exames = carga.carregarDados(caminhoCsv);
        for (Exame exame : exames) {
            exameFacade.pagarExame(exame);
            procFacade.enfileirarExame(exame);
        }
        procFacade.processarExames(this::processarExame);
    }

    private void processarExame(Exame exameProcessado) {
        String caminhoLaudo = laudoFacade.gerarLaudo(exameProcessado, "pdf", true);
        String msg = "Olá " + exameProcessado.getPaciente().getNome() +
                    ", seu laudo (" + exameProcessado.getCodigo() + 
                    ") já está disponível em: " + caminhoLaudo;
        notificacaoFacade.notificarPaciente(exameProcessado, msg);
    }
}