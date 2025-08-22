package template;

public class LaudoHtml implements LaudoTemplate {
    @Override
    public void gerarLaudo(String cabecalho, String corpo, String rodape, String nomeArquivo) {
        System.out.println("=== [HTML] " + nomeArquivo + ".html ===");
        System.out.println("<html><body>");
        System.out.println("<pre>" + cabecalho + "</pre>");
        System.out.println("<hr/>");
        System.out.println("<p>" + corpo.replace("\n","<br/>") + "</p>");
        System.out.println("<hr/>");
        System.out.println("<small>" + rodape + "</small>");
        System.out.println("</body></html>");
        System.out.println("==============================\n");
    }
}
