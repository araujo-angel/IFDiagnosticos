package core;
import java.util.List;
import model.exame.Exame;

public class SistemaDiagnosticos {
    public static void main(String[] args) {
        CargaDadosFacade carga = new CargaDadosFacade();
        List<Exame> exames = carga.carregarDados("src/resources/dados.csv");

        ExameFacade exameFacade = new ExameFacade();
        ProcessamentoFacade procFacade = new ProcessamentoFacade();
        LaudoFacade laudoFacade = new LaudoFacade();
        NotificacaoFacade notificacaoFacade = new NotificacaoFacade();

        // fluxo
        for (Exame exame : exames) {
            exameFacade.pagarExame(exame);
            procFacade.enfileirarExame(exame);
        }

        procFacade.processarExames((exameProcessado) -> {
            String caminhoLaudo = laudoFacade.gerarLaudo(exameProcessado, "pdf", true);
            String msg = "Olá " + exameProcessado.getPaciente().getNome() +
                         ", seu laudo (" + exameProcessado.getCodigo() + 
                         ") já está disponível em: " + caminhoLaudo;
            notificacaoFacade.notificarPaciente(exameProcessado, msg);
        });
    }
}
