package reports.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LaudoHtml implements LaudoTemplate {

    @Override
    public String gerarConteudo(String cabecalho, String corpo, String rodape) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>\n");
        sb.append("<pre>").append(cabecalho).append("</pre>\n");
        sb.append("<hr/>\n");
        sb.append("<p>").append(corpo.replace("\n", "<br/>")).append("</p>\n");
        sb.append("<hr/>\n");
        sb.append("<small>").append(rodape).append("</small>\n");
        sb.append("</body></html>");
        return sb.toString();
    }

    @Override
    public String salvarEmArquivo(String conteudo, String nomeArquivo) {
        String filePath = "laudos/" + nomeArquivo + ".html";
        try {
            File dir = new File("laudos");
            if (!dir.exists()) dir.mkdirs();

            FileWriter writer = new FileWriter(filePath);
            writer.write(conteudo);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
