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
        return "[PDF]\n" + cabecalho + "\n---\n" + corpo + "\n---\n" + rodape;
    }

    @Override
    public String salvarEmArquivo(String conteudo, String nomeArquivo) {
        String userHome = System.getProperty("user.home");
        File pasta = new File(userHome + File.separator + "Documents" + File.separator + "Laudos");
        pasta.mkdirs();

        String filePath = new File(pasta, nomeArquivo + ".pdf").getAbsolutePath();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 700);

            for (String line : conteudo.split("\n")) {
                contentStream.showText(line);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();
            document.save(filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

}
