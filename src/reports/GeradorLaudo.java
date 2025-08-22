package reports;

import reports.template.LaudoTemplate;

public class GeradorLaudo {

    private final LaudoTemplate template;

    public GeradorLaudo(LaudoTemplate template) {
        this.template = template;
    }

    public String gerar(String cabecalho, String corpo, String rodape, String nomeArquivo, boolean printConsole) {
        String conteudo = template.gerarConteudo(cabecalho, corpo, rodape);

        if (printConsole) {
            System.out.println("=== Laudo (" + nomeArquivo + ") ===");
            System.out.println(conteudo);
            System.out.println("==============================\n");
        }

        return template.salvarEmArquivo(conteudo, nomeArquivo);
    }
}
