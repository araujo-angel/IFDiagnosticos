package notifiers;

import model.Exame;
import observer.NotificadorObserver;

public class EmailNotifier implements NotificadorObserver {

    private String destinatario;

    public EmailNotifier(String destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public void atualizar(Exame exame) {
        System.out.println("📧 [Simulacao de envio de E-MAIL]");
        System.out.println("Para: " + destinatario);
        System.out.println("Assunto: Laudo disponivel - Exame " + exame.getCodigo());
        System.out.println("Mensagem: O exame " + exame.getCodigo() + " do paciente " 
                           + exame.getPaciente().getNome() + " foi processado e o laudo esta pronto.");
    }
}

