package reports.template;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;

public class LaudoPdf implements LaudoTemplate {

    private String cabecalho;
    private String corpo;
    private String rodape;

    @Override
    public String gerarConteudo(String cabecalho, String corpo, String rodape) {
        this.cabecalho = cabecalho;
        this.corpo = corpo;
        this.rodape = rodape;

        // console
        return "[PDF]\n" + cabecalho + "\n---\n" + corpo + "\n---\n" + rodape;
    }

    @Override
    public String salvarEmArquivo(String conteudo, String nomeArquivo) {
        String filePath = "laudos/" + nomeArquivo + ".pdf";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 700);

            for (String line : conteudo.split("\n")) {
                contentStream.showText(line);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
