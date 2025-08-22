package notifiers;

import model.Exame;
import observers.NotificadorObserver;

public class EmailNotifier implements NotificadorObserver {

    private String destinatario;

    public EmailNotifier(String destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public void atualizar(Exame exame) {
        System.out.println("📧 [Simulação de envio de E-MAIL]");
        System.out.println("Para: " + destinatario);
        System.out.println("Assunto: Laudo disponível - Exame " + exame.getCodigo());
        System.out.println("Mensagem: O exame " + exame.getCodigo() + " do paciente " 
                           + exame.getPaciente().getNome() + " foi processado e o laudo está pronto.");
    }
}

