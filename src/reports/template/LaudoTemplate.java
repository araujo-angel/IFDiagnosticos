package reports.template;

public interface LaudoTemplate {
    String gerarConteudo(String cabecalho, String corpo, String rodape);
    String salvarEmArquivo(String conteudo, String nomeArquivo);
}
