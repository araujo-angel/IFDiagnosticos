package core;

import model.exame.Exame;
import observer.notifiers.EmailNotifier;

public class NotificacaoFacade {

    public void notificarPaciente(Exame exame, String mensagem) {
        String email = exame.getPaciente().getEmail();
        EmailNotifier notifier = new EmailNotifier(email);

        notifier.notificar(mensagem);
        System.out.println("Notificação enviada para: " + email);
    }
}
