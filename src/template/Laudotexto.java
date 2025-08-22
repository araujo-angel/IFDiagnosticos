package template;

public class Laudotexto implements LaudoTemplate {
    @Override
    public void gerarLaudo(String cabecalho, String corpo, String rodape, String nomeArquivo) {
        System.out.println("=== [TXT] " + nomeArquivo + ".txt ===");
        System.out.println(cabecalho);
        System.out.println("---");
        System.out.println(corpo);
        System.out.println("---");
        System.out.println(rodape);
        System.out.println("==============================\n");
    }
}
