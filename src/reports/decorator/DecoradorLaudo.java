package reports.decorator;

import reports.template.LaudoTemplate;

public abstract class DecoradorLaudo implements LaudoTemplate {
    protected final LaudoTemplate laudo;

    public DecoradorLaudo(LaudoTemplate laudo) {
        this.laudo = laudo;
    }

    public void gerarLaudo(String cabecalho, String corpo, String rodape, String nomeArquivo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gerarLaudo'");
    }
}