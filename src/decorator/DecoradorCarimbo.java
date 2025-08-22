package decorator;

import template.LaudoTemplate;

public class DecoradorCarimbo extends DecoradorLaudo {

    public DecoradorCarimbo(LaudoTemplate laudo) {
        super(laudo);
    }

    @Override
    public void gerarLaudo(String cabecalho, String corpo, String rodape, String nomeArquivo) {
        String rodapeComCarimbo = rodape + "\nCarimbo digital: IF Diagnosticos";
        laudo.gerarLaudo(cabecalho, corpo, rodapeComCarimbo, nomeArquivo);
    }
}
