---
config:
  theme: neo-dark
---
classDiagram
    class SistemaDiagnosticos {
        +main(args: String[]): void
        +agendarExame(fabrica: FabricaExame, paciente: Paciente, medico: Medico): Exame
    }
    class GerenciadorDeProcessamentoDeExames {
        -filaExames: PriorityQueue~Exame~
        +adicionarExame(Exame exame)
        +processarProximoExame()
        +notificarLaudoPronto(Exame exame)
        +marcarExameComoPronto(Exame exame)
    }
    class NotificadorLaudoPronto {
        -Exame exame
        -Paciente paciente
        +notificar()
    }
    class ProcessadorPagamento {
        -Exame exame
        -DescontoStrategy descontoStrategy
        +processarPagamento()
        +calcularCusto(estrategia: DescontoStrategy): double
    }
    class LaudoTemplate {
        <<abstract>>
        +gerarCabecalho(): String
        +gerarCorpo(): String
        +gerarRodape(): String
        +gerarLaudoCompleto(): String
    }
    class LaudoTexto {
        +gerarCabecalho(): String
        +gerarCorpo(): String
        +gerarRodape(): String
    }
    class LaudoHTML {
        +gerarCabecalho(): String
        +gerarCorpo(): String
        +gerarRodape(): String
    }
    class LaudoPDF {
        +gerarCabecalho(): String
        +gerarCorpo(): String
        +gerarRodape(): String
    }
    class Paciente {
        -nome: String
        -cpf: String
        -dataNascimento: Date
        -temConvenio: boolean
        -exames: ListExame
        +getIdade(): int
        +adicionarExame(exame: Exame): void
    }
    class Medico {
        -nome: String
        -CRM: String
        +solicitarExame(paciente: Paciente, tipoExame: String): Exame
    }
    class Exame {
        <<abstract>>
        -codigo: String
        -valorBase: double
        -dataSolicitacao: Date
        -prioridade: Prioridade
        -paciente: Paciente
        -medico: Medico
        -laudo: LaudoTemplate
        -status: StatusExame
        -prioridade: Prioridade
        
        +validar(): boolean
        +gerarLaudo(): LaudoTemplate
        +marcarComoPronto()
        +mudarEstado(StatusExame estado)
        +getStatus(): String
        +getPrioridade() Prioridade
    }
    class Hemograma {
        -hemoglobina: double
        -leucocitos: double
        +validar(): boolean
    }
    class Ressonancia {
        -areaCorpo: String
        -comContraste: boolean
        +validar(): boolean
    }
    class FabricaExame {
        <<interface>>
        +criarExame(): Exame
        +criarLaudo(formato: String): LaudoTemplate
    }
    class FabricaHemograma {
        +criarExame(): Exame
        +criarLaudo(formato: String): LaudoTemplate
    }
    class FabricaRessonancia {
        +criarExame(): Exame
        +criarLaudo(formato: String): LaudoTemplate
    }
    class StatusExame {
        <<interface>>
        +marcarComoPronto()*
        +cancelar()*
        +processar()*
        +getStatus()* String
    }
    class Prioridade {
        <<enum>>
        URGENTE
        PRIORITARIO
        ROTINA
    }    
    class PriorityQueue~T~ {
        <<Structure>>
        +add(element: T)
        +poll() T
        +peek() T
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
    
    %% Relacionamentos principais
    SistemaDiagnosticos --> FabricaExame
    SistemaDiagnosticos --> GerenciadorDeProcessamentoDeExames
    SistemaDiagnosticos --> ProcessadorPagamento
    GerenciadorDeProcessamentoDeExames --> Exame : "1" processa "*"
    GerenciadorDeProcessamentoDeExames --> NotificadorLaudoPronto
    GerenciadorDeProcessamentoDeExames "1" *-- "1" PriorityQueue~Exame~ : contém
    GerenciadorDeProcessamentoDeExames "1" --> "*" Exame : processa ordenado por
    NotificadorLaudoPronto --> Exame
    NotificadorLaudoPronto --> Paciente
    ProcessadorPagamento --> Exame
    ProcessadorPagamento --> DescontoStrategy
    Exame "1" *-- "1" LaudoTemplate
    Exame "1" o-- "1" StatusExame
    Exame "1" --> "1" Prioridade : possui
    Exame <|-- Hemograma
    Exame <|-- Ressonancia
    FabricaExame <|.. FabricaRessonancia
    FabricaExame --> LaudoTemplate
    FabricaExame <|.. FabricaHemograma
    LaudoTemplate <|-- LaudoTexto
    LaudoTemplate <|-- LaudoHTML
    LaudoTemplate <|-- LaudoPDF
    Paciente "1" -- "*" Exame
    Medico "1" -- "*" Exame
    DescontoStrategy <|.. DescontoConvenio
    DescontoStrategy <|.. DescontoIdoso

    %% Relacionamentos de State
    StatusExame <|.. ExameEmEspera
    StatusExame <|.. ExameProcessando
    StatusExame <|.. ExamePronto