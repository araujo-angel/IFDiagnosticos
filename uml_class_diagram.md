---
config:
  theme: neo-dark
---

classDiagram
    %% =========================
    %% SISTEMA PRINCIPAL
    %% =========================
    class SistemaDiagnosticos {
        +main(args: String[]): void
    }

    class SistemaDiagnosticosFacade {
        +agendarExame(fabrica: FabricaExame, paciente: Paciente, medico: Medico): Exame
        +processarExame(exame: Exame): void
        +pagarExame(exame: Exame, estrategia: DescontoStrategy): void
        +gerarLaudo(exame: Exame, formato: String): String
    }

    %% =========================
    %% ENTIDADES
    %% =========================
    class Paciente {
        -nome: String
        -cpf: String
        -dataNascimento: Date
        -temConvenio: boolean
        -exames: List~Exame~
        +getIdade(): int
        +adicionarExame(exame: Exame): void
    }

    class Medico {
        -nome: String
        -CRM: String
        +solicitarExame(paciente: Paciente, tipoExame: String): Exame
    }

    %% =========================
    %% EXAMES E ESTADOS
    %% =========================
    class Exame {
        <<abstract>>
        -codigo: String
        -valorBase: double
        -dataSolicitacao: Date
        -prioridade: Prioridade
        -paciente: Paciente
        -medico: Medico
        -estado: StatusExameState
        -laudo: LaudoTemplate
        +getEstado(): String
        +avancarEstado(): void
    }

    class Prioridade {
        <<enumeration>>
        +ALTA
        +MEDIA
        +BAIXA
    }

    class StatusExameState {
        <<interface>>
        +mudarEstado(exame: Exame): void
    }

    class ExamePendente { +mudarEstado(exame: Exame): void }
    class ExameProcessando { +mudarEstado(exame: Exame): void }
    class ExameConcluido { +mudarEstado(exame: Exame): void }
    class ExameCancelado { +mudarEstado(exame: Exame): void }

    class Hemograma {
        -hemoglobina: double
        -leucocitos: double
    }

    class Ressonancia {
        -areaCorpo: String
        -comContraste: boolean
    }

    %% =========================
    %% FACTORY
    %% =========================
    class FabricaExame {
        <<interface>>
        +criarExame(): Exame
    }

    class FabricaHemograma { +criarExame(): Exame }
    class FabricaRessonancia { +criarExame(): Exame }

    %% =========================
    %% VALIDADORES
    %% =========================
    class ValidadorExame {
        <<interface>>
        +validar(exame: Exame): boolean
    }

    class ValidadorHemograma { +validar(exame: Exame): boolean }
    class ValidadorRessonancia { +validar(exame: Exame): boolean }
    class ValidadorFactory { +criarValidador(exame: Exame): ValidadorExame }

    %% =========================
    %% PAGAMENTO (STRATEGY)
    %% =========================
    class ProcessadorPagamento {
        -exame: Exame
        -descontoStrategy: DescontoStrategy
        +processarPagamento(): void
        +calcularCusto(estrategia: DescontoStrategy): double
    }

    class DescontoStrategy {
        <<interface>>
        +aplicarDesconto(valor: double): double
    }

    class DescontoConvenio {
        -PORCENTAGEM: double = 0.15
        +aplicarDesconto(valor: double): double
    }

    class DescontoIdoso {
        -PORCENTAGEM: double = 0.08
        +aplicarDesconto(valor: double): double
    }

    %% =========================
    %% LAUDOS (TEMPLATE METHOD)
    %% =========================
    class GeradorLaudo {
        +gerarLaudo(exame: Exame, formato: String): LaudoTemplate
    }

    class LaudoTemplate {
        <<abstract>>
        +gerarCabecalho(): String
        +gerarCorpo(): String
        +gerarRodape(): String
        +gerarLaudoCompleto(): String
    }

    class LaudoTexto { +gerarCabecalho(): String +gerarCorpo(): String +gerarRodape(): String }
    class LaudoHTML { +gerarCabecalho(): String +gerarCorpo(): String +gerarRodape(): String }
    class LaudoPDF { +gerarCabecalho(): String +gerarCorpo(): String +gerarRodape(): String }

    %% =========================
    %% DECORATOR DE LAUDOS
    %% =========================
    class DecoradorLaudo {
        <<abstract>>
        -laudoDecorado: LaudoTemplate
        +gerarLaudoDecorado(): String
    }

    class DecoradorCarimbo { +gerarLaudoDecorado(): String }

    %% =========================
    %% OBSERVER (NOTIFICAÇÕES)
    %% =========================
    class NotificadorObserver {
        <<interface>>
        +atualizar(exame: Exame): void
    }

    class NotificadorWhatsApp { +atualizar(exame: Exame): void }
    class NotificadorTelegram { +atualizar(exame: Exame): void }

    %% =========================
    %% GERENCIADOR DE EXAMES
    %% =========================
    class GerenciadorDeProcessamentoDeExames {
        -PriorityQueueExame filaExames
        +adicionarExame(exame: Exame): void
        +processarProximoExame(): void
        +notificarLaudoPronto(exame: Exame): void
        +marcarExameComoPronto(exame: Exame): void
    }

    %% =========================
    %% RELACIONAMENTOS
    %% =========================
    SistemaDiagnosticos --> SistemaDiagnosticosFacade
    SistemaDiagnosticosFacade --> FabricaExame
    SistemaDiagnosticosFacade --> GerenciadorDeProcessamentoDeExames
    SistemaDiagnosticosFacade --> ProcessadorPagamento
    SistemaDiagnosticosFacade --> GeradorLaudo
    SistemaDiagnosticosFacade --> DescontoStrategy
    SistemaDiagnosticosFacade --> ValidadorFactory

    GerenciadorDeProcessamentoDeExames --> Exame
    GerenciadorDeProcessamentoDeExames --> NotificadorObserver

    ValidadorFactory --> ValidadorExame
    ValidadorExame <|.. ValidadorHemograma
    ValidadorExame <|.. ValidadorRessonancia
    ValidadorHemograma ..> Hemograma
    ValidadorRessonancia ..> Ressonancia

    ProcessadorPagamento --> Exame
    ProcessadorPagamento --> DescontoStrategy

    GeradorLaudo --> LaudoTemplate
    LaudoTemplate <|-- LaudoTexto
    LaudoTemplate <|-- LaudoHTML
    LaudoTemplate <|-- LaudoPDF
    LaudoTemplate <|-- DecoradorLaudo
    DecoradorLaudo <|-- DecoradorCarimbo
    DecoradorLaudo o-- LaudoTemplate

    Exame --> StatusExameState
    Exame <|-- Hemograma
    Exame <|-- Ressonancia
    Exame --> Prioridade
    Exame --> LaudoTemplate

    StatusExameState <|.. ExamePendente
    StatusExameState <|.. ExameProcessando
    StatusExameState <|.. ExameConcluido
    StatusExameState <|.. ExameCancelado

    Paciente "1" *-- "*" Exame
    Medico "1" -- "*" Exame

    FabricaExame <|.. FabricaHemograma
    FabricaExame <|.. FabricaRessonancia

    DescontoStrategy <|.. DescontoConvenio
    DescontoStrategy <|.. DescontoIdoso

    NotificadorObserver <|.. NotificadorWhatsApp
    NotificadorObserver <|.. NotificadorTelegram
    NotificadorObserver --> Exame
    NotificadorObserver --> Paciente
