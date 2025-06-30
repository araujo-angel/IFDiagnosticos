VISÃO GERAL DA APLICAÇÃO:

@startuml
skinparam nodesep 50
skinparam ranksep 60

package "Gerenciamento de Exames" {
  interface IExame <<interface>> {
    + gerarLaudo()
  }

  class Exame {
    - prioridade: EstadoPrioridade
    + dados: String
    + setPrioridade()
    + processar()
  }

  class LaudoAbstractFactory {
    + criarLaudo()
    + criarFormato()
  }

  class Laudo {
    + conteudo: String
    + exportar()
  }
}

package "Validação" {
  abstract class Validador {
    - próximo: Validador
    + validarExame()
  }

  class ValidadorChain {
    + adicionarValidador()
  }
}

package "Notificação" {
  class NotificacaoObserver {
    + adicionarPaciente()
    + notificarTodos()
  }

  class Paciente {
    + atualizar()
  }
}

package "Priorização" {
  interface EstadoPrioridade {
    + processar()
  }

  class Urgente
  class Rotina
}

package "Sistema Principal" {
  class SistemaFacade {
    + emitirLaudo()
    + validarExame()
    + notificarResultado()
  }
}

' Relacionamentos principais
Exame --> LaudoAbstractFactory
LaudoAbstractFactory --> Laudo

Exame --> Validador
Validador <|-- ValidadorChain

Exame --> EstadoPrioridade
EstadoPrioridade <|-- Urgente
EstadoPrioridade <|-- Rotina

NotificacaoObserver --> Paciente
SistemaFacade --> Exame
SistemaFacade --> NotificacaoObserver

' Estilos
class SistemaFacade <<Facade>> #FFAAAA
class LaudoAbstractFactory <<AbstractFactory>> #AAFFAA
class ValidadorChain <<Chain>> #FFFFAA
class NotificacaoObserver <<Observer>> #FFAAFF
class EstadoPrioridade <<State>> #AAAAFF

legend right
  <b>Padrões Relacionados</b>
  | Padrão | Componente |
  | Abstract Factory | LaudoAbstractFactory |
  | Chain of Resp. | ValidadorChain |
  | Observer | NotificacaoObserver |
  | State | EstadoPrioridade |
  | Facade | SistemaFacade |
endlegend
@enduml

ExameAbstractFactory:
@startuml
class ExameAbstractFactory {
    + criarLaudo(): Laudo
    + criarFormato(): Formato
}

class Laudo {
    + gerarConteudo()
}

class Formato {
    + exportar()
}

class LaudoHemograma {
    + gerarConteudo()
}

class FormatoPDF {
    + exportar()
}

ExameAbstractFactory <|-- HemogramaFactory
ExameAbstractFactory <|-- RessonanciaFactory

HemogramaFactory --> LaudoHemograma
HemogramaFactory --> FormatoPDF

Laudo <|-- LaudoHemograma
Laudo <|-- LaudoRessonancia

Formato <|-- FormatoPDF
Formato <|-- FormatoHTML

@enduml

Validador:
@startuml
abstract class Validador {
    - próximo: Validador
    + setProximo(Validador)
    + validar(Exame)
    # verificarRegra(Exame)
}

class ValidadorHemograma {
    # verificarRegra(Exame)
}

class ValidadorRessonancia {
    # verificarRegra(Exame)
}

Validador <|-- ValidadorHemograma
Validador <|-- ValidadorRessonancia
ValidadorHemograma --> ValidadorRessonancia : próximo

@enduml

Observador:
@startuml
interface Observador {
    + atualizar()
}

class Paciente {
    + telefone
    + email
    + atualizar()
}

class SistemaNotificacao {
    - observadores: List<Observador>
    + adicionarObservador()
    + notificarTodos()
}

class NotificacaoWhatsApp {
    + enviar()
}

SistemaNotificacao o-- Observador
Observador <|.. Paciente
SistemaNotificacao --> NotificacaoWhatsApp

@enduml

Exame:
@startuml
class Exame {
    - estado: EstadoPrioridade
    + setEstado()
    + processar()
}

interface EstadoPrioridade {
    + processar()
}

class Urgente {
    + processar()
}

class Rotina {
    + processar()
}

Exame *-- EstadoPrioridade
EstadoPrioridade <|.. Urgente
EstadoPrioridade <|.. Rotina

@enduml

SistemaFacade:
@startuml
class SistemaFacade {
    - laudoService: LaudoService
    - validacaoService: ValidacaoService
    - notificacaoService: NotificacaoService
    + emitirLaudo()
    + validarExame()
    + notificarPaciente()
}

class LaudoService {
    + gerarLaudo()
}

class ValidacaoService {
    + validarExame()
}

class NotificacaoService {
    + enviarNotificacao()
}

SistemaFacade --> LaudoService
SistemaFacade --> ValidacaoService
SistemaFacade --> NotificacaoService

@enduml



