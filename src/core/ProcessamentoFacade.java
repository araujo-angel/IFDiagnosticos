package core;

import maneger.GerenciadorDeProcessamentoDeExames;
import model.exame.Exame;
import observer.notifiers.EmailNotifier;
import validators.ValidadorExame;
import validators.ValidadorFactory;

import java.util.function.Consumer;

public class ProcessamentoFacade {

    private final GerenciadorDeProcessamentoDeExames gerenciador;

    public ProcessamentoFacade() {
        this.gerenciador = new GerenciadorDeProcessamentoDeExames();
    }

    public void enfileirarExame(Exame exame) {
        gerenciador.adicionarNotificador(new EmailNotifier(exame.getPaciente().getEmail()));
        gerenciador.adicionarExame(exame);
    }

    public void processarExames(Consumer<Exame> callback) {
        while (true) {
            Exame processado = gerenciador.processarProximoExame();
            if (processado == null) break;
            ValidadorExame validador = ValidadorFactory.criarValidador(processado);
            System.out.println("Validação: " + validador.validar(processado));

            callback.accept(processado);
        }
    }
}
