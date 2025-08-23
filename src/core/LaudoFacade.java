package core;

import reports.GeradorLaudo;
import reports.template.LaudoFactoryRegistry;
import reports.template.LaudoTemplate;
import model.exame.Exame;

public class LaudoFacade {

    public String gerarLaudo(Exame exame, String formato, boolean printConsole) {
        LaudoTemplate template = LaudoFactoryRegistry.getTemplate(formato);
        GeradorLaudo gerador = new GeradorLaudo(template);

        String cabecalho = "Paciente: " + exame.getPaciente().getNome() +
                "\nMédico: " + exame.getMedico().getNome();
        String corpo = "Exame: " + exame.getClass().getSimpleName() +
                "\nPrioridade: " + exame.getPrioridade();
        String rodape = "Data: " + exame.getDataSolicitacao();

        return gerador.gerar(cabecalho, corpo, rodape, exame.getCodigo(), printConsole);
    }
}

