package notifier;

import model.exame.Exame;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class NotificadorEmail implements NotificadorObserver {

    @Override
    public void atualizar(Exame exame, String caminhoLaudo) {
        System.out.println("🔔 Iniciando notificação por e-mail para: " + exame.getPaciente().getEmail());

        String destinatario = exame.getPaciente().getEmail();
        String assunto = "Laudo disponível - Exame " + exame.getCodigo();
        String mensagem = "O exame " + exame.getCodigo() + " foi processado. Segue o laudo em anexo.";

        enviarEmail(destinatario, assunto, mensagem, caminhoLaudo);
    }

    private void enviarEmail(String destinatario, String assunto, String corpo, String caminhoAnexo) {
        final String remetente = "anandaguedesdoo@gmail.com";
        final String senha = ""; 

        System.out.println("📤 Preparando email...");
        System.out.println("Remetente: " + remetente);
        System.out.println("Destinatário: " + destinatario);
        System.out.println("Assunto: " + assunto);
        System.out.println("Caminho do laudo: " + caminhoAnexo);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.connectiontimeout", "10000");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(assunto);

            MimeBodyPart corpoParte = new MimeBodyPart();
            corpoParte.setText(corpo);

            MimeBodyPart anexoParte = new MimeBodyPart();
            File arquivo = new File(caminhoAnexo);
            if (arquivo.exists()) {
                System.out.println("📎 Arquivo de laudo encontrado, adicionando anexo.");
                anexoParte.attachFile(arquivo);
            } else {
                System.err.println("⚠️ Arquivo de laudo não encontrado: " + caminhoAnexo);
            }

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(corpoParte);
            if (arquivo.exists()) {
                multipart.addBodyPart(anexoParte);
            }

            message.setContent(multipart);

            System.out.println("🔗 Conectando ao servidor SMTP...");
            Transport.send(message);
            System.out.println("✅ Email enviado com sucesso para: " + destinatario);

        } catch (MessagingException me) {
            System.err.println("❌ Erro de MessagingException: " + me.getMessage());
            me.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado no envio de email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


