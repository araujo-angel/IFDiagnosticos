

import java.util.Date;

import core.SistemaDiagnosticosFacade;
import factories.ExameFactoryRegistry;
import factories.FabricaHemograma;
import factories.FabricaRessonancia;
import maneger.GerenciadorDeProcessamentoDeExames;
import model.Exame;
import model.Medico;
import model.Paciente;
import model.Prioridade;
import notifiers.EmailNotifier;
import validators.ValidadorExame;
import validators.ValidadorFactory;

import payments.DescontoConvenio;

public class SistemaDiagnosticos {
    public static void main(String[] args) {
        // --- Inicializa registro de fábricas ---
        ExameFactoryRegistry.registerFactory("hemograma", new FabricaHemograma());
        ExameFactoryRegistry.registerFactory("ressonancia", new FabricaRessonancia());

        // --- Instancia fachada ---
        SistemaDiagnosticosFacade facade = new SistemaDiagnosticosFacade();

        // --- Cria pacientes e médico ---
        Paciente paciente1 = new Paciente("João Silva", "12345678900", new Date(), "joaosilva@gmail.com", true);
        Paciente paciente2 = new Paciente("Carlos Souza", "98765432100", new Date(), "carlossouza@gmail.com", false);
        Medico medico = new Medico("Dra. Maria", "CRM-12345");

        // --- AGENDAR EXAMES ---
        Exame exame1 = facade.agendarExame("hemograma", "H123", 120.0, new Date(), Prioridade.ALTA, paciente1, medico);
        Exame exame2 = facade.agendarExame("ressonancia", "R456", 350.0, new Date(), Prioridade.BAIXA, paciente2, medico);

        System.out.println("Exames agendados: " + exame1.getCodigo() + ", " + exame2.getCodigo());

        // --- VALIDAR EXAME ---
        ValidadorExame validador1 = ValidadorFactory.criarValidador(exame1);
        System.out.println("Hemograma válido? " + validador1.validar(exame1));

        ValidadorExame validador2 = ValidadorFactory.criarValidador(exame2);
        System.out.println("Ressonância válida? " + validador2.validar(exame2));

        // --- PAGAR EXAMES ---
        facade.pagarExame(exame1, new DescontoConvenio());
        facade.pagarExame(exame2, new DescontoConvenio());

        // --- GERENCIADOR DE PROCESSAMENTO ---
        GerenciadorDeProcessamentoDeExames gerenciador = new GerenciadorDeProcessamentoDeExames();

        // Adiciona notificadores (nesse caso, e-mail para cada paciente)
        gerenciador.adicionarNotificador(new EmailNotifier(paciente1.getEmail()));
        gerenciador.adicionarNotificador(new EmailNotifier(paciente2.getEmail()));

        // Adiciona exames na fila
        gerenciador.adicionarExame(exame1);
        gerenciador.adicionarExame(exame2);

        // --- PROCESSAR FILA ---
        while (true) {
            Exame processado = gerenciador.processarProximoExame();
            if (processado == null) break;

            System.out.println("Exame processado: " + processado.getCodigo());
            System.out.println("Estado final: " + processado.getEstado().getClass().getSimpleName());
            System.out.println("----");
        }

        // --- GERAR LAUDO (se tiver implementado) ---
       /*  
        String laudo1 = facade.gerarLaudo(exame1, "texto");
        System.out.println("Laudo Hemograma:\n" + laudo1);

        String laudo2 = facade.gerarLaudo(exame2, "texto");
        System.out.println("Laudo Ressonância:\n" + laudo2);
        */
    }
}
