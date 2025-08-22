package reports.template;

import model.laudo.Laudo;

public class LaudoPdf implements LaudoTemplate {
    @Override
    public void gerarLaudo(String cabecalho, String corpo, String rodape,  String nomeArquivo) {
        System.out.println("=== [PDF] " + Laudo.gerarNomeArquivo + ".pdf ===");
        System.out.println("(PDF gerado via biblioteca escolhida)");
        System.out.println("Cabeçalho: " + cabecalho.split("\\n")[0] + " ...");
        System.out.println("==============================\n");
    }
}