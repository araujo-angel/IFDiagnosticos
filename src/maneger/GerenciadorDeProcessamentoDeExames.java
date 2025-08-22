package maneger;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import model.Exame;
import observers.NotificadorObserver;

public class GerenciadorDeProcessamentoDeExames {

    private PriorityQueue<Exame> filaExames;
    private List<NotificadorObserver> notificadores;

    public GerenciadorDeProcessamentoDeExames() {
        // PriorityQueue exige um comparador — vamos usar a prioridade do exame
        this.filaExames = new PriorityQueue<>(
            (e1, e2) -> e1.getPrioridade().compareTo(e2.getPrioridade())
        );
        this.notificadores = new ArrayList<>();
    }

    public void adicionarNotificador(NotificadorObserver notificador) {
        notificadores.add(notificador);
    }

    public void adicionarExame(Exame exame) {
        filaExames.add(exame);
    }


    public Exame processarProximoExame() {
        Exame exame = filaExames.poll();
        if (exame != null) {
            exame.avancarEstado(); // solicitado -> processando
            exame.avancarEstado(); // processando -> concluído
            marcarExameComoPronto(exame);
        }
        return exame;
    }

    public void marcarExameComoPronto(Exame exame) {
        // Aqui poderíamos integrar com GeradorLaudo
        notificarLaudoPronto(exame);
    }

    public void notificarLaudoPronto(Exame exame) {
        for (NotificadorObserver notificador : notificadores) {
            notificador.atualizar(exame);
        }
    }
}

