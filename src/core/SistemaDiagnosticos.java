package core;

import java.util.Date;

import factories.ExameFactoryRegistry;
import factories.FabricaHemograma;
import factories.FabricaRessonancia;
import model.Exame;
import model.Medico;
import model.Paciente;
import model.Prioridade;
import payments.DescontoConvenio;
import validators.ValidadorExame;
import validators.ValidadorFactory;

public class SistemaDiagnosticos {
    public static void main(String[] args) {
        // Inicializa registro de fábricas
        ExameFactoryRegistry.registerFactory("hemograma", new FabricaHemograma());
        ExameFactoryRegistry.registerFactory("ressonancia", new FabricaRessonancia());

        // Instancia fachada
        SistemaDiagnosticosFacade facade = new SistemaDiagnosticosFacade();

        // Cria paciente e médico
        Paciente paciente = new Paciente("João Silva", "12345678900", new Date(), true);
        Medico medico = new Medico("Dra. Maria", "CRM-12345");

        // --- AGENDAR EXAME ---
        Exame hemograma = facade.agendarExame(
            "hemograma",
            "H123",
            120.0,
            new Date(),
            Prioridade.ALTA,
            paciente,
            medico
        );
        System.out.println("Exame agendado: " + hemograma.getCodigo());

        // --- VALIDAR EXAME ---
        ValidadorExame validador = ValidadorFactory.criarValidador(hemograma);
        if (validador.validar(hemograma)) {
            System.out.println("Exame válido.");
        } else {
            System.out.println("Exame inválido.");
        }

        // --- PAGAR EXAME ---
        facade.pagarExame(hemograma, new DescontoConvenio());

        /*--- GERENCIADOR DE EXAMES ---
        GerenciadorDeProcessamentoDeExames gerenciador = new GerenciadorDeProcessamentoDeExames();

        // adiciona notificadores dinamicamente
        gerenciador.adicionarNotificador(new NotificadorWhatsApp());
        gerenciador.adicionarNotificador(new NotificadorTelegram());

        // adiciona exame na fila
        gerenciador.adicionarExame(hemograma);

        // processa exame (a fila já respeita prioridade)
        Exame processado = gerenciador.processarProximoExame();
        System.out.println("Exame processado e notificado: " + processado.getCodigo());
        System.out.println("Estado final do exame: " + processado.getEstado().getClass().getSimpleName());

        /* --- GERAR LAUDO (opcional, se já tiver implementado GeradorLaudo) ---
        String laudo = facade.gerarLaudo(processado, "texto");
        System.out.println("Laudo:\n" + laudo);
        */
    }
}
