package reports.template;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.File;
import java.io.IOException;

public class LaudoPdf implements LaudoTemplate {

    @Override
    public String gerarConteudo(String cabecalho, String corpo, String rodape) {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append(cabecalho).append("\n\n");
        conteudo.append(corpo).append("\n\n");
        conteudo.append(rodape);
        return conteudo.toString();
    }

   @Override
    public String salvarEmArquivo(String conteudo, String nomeArquivo) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
    
            String[] lines = conteudo.split("\n");
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 700);
            
            for (String line : lines) {
                if (line.contains("=====") || line.contains("-----")) {
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                } else {
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                }
                contentStream.showText(line);
                contentStream.newLine();
            }
            
            contentStream.endText();
            contentStream.close();

            String dirPath = System.getProperty("user.home") + "/Documents/Laudos/";
            File directory = new File(dirPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File arquivo = new File(directory, nomeArquivo + ".pdf"); 
            document.save(arquivo);
            return "";
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
