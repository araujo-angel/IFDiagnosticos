# **Sistema de Gerenciamento de Exames Médicos**

## **Visão Geral**

O sistema é uma solução completa para gestão de exames médicos, utilizando **padrões de projeto** para garantir:

* **Processamento prioritário** de exames (urgentes, prioritários, rotina)
* **Geração de laudos** em múltiplos formatos (PDF, HTML, texto)
* **Aplicação de descontos** dinâmicos (convênio, idoso)
* **Criação flexível** de diferentes tipos de exames (hemograma, ressonância)

---

## **Diagrama de Fluxo do Sistema**

```mermaid
flowchart TD
    Paciente --> Medico
    Medico --> FabricaExame[Criação de Exame e Laudo]
    FabricaExame --> Exame
    Exame --> Gerenciador[Gerenciador (PriorityQueue)]
    Gerenciador --> Ordenacao[Ordena por Prioridade]
    Ordenacao --> Finalizacao[Exame Pronto]
    Finalizacao --> Laudo[Laudo Gerado]
    Laudo --> Notificacao[Notificação (se aplicável)]
    Finalizacao --> Pagamento
    Pagamento --> ProcessadorPagamento
    ProcessadorPagamento --> Desconto[Aplica Desconto (Strategy)]
```

---

## **Padrões de Projeto e Suas Aplicações**

### **1. Abstract Factory (`FabricaExame`)**

**Problema**: Criar famílias de objetos relacionados (exame + laudo) de forma consistente.
**Solução**:

```java
FabricaExame fabrica = new FabricaHemograma();  
Exame exame = fabrica.criarExame();  
LaudoTemplate laudo = fabrica.criarLaudo("PDF");  
```

**Vantagens**:

* Isolamento da criação de objetos
* Facilidade para adicionar novos tipos de exames

---

### **2. Factory Method (`criarLaudo()`)**

**Problema**: Permitir que subclasses decidam qual implementação de laudo criar.
**Solução**:

```java
public class FabricaRessonancia implements FabricaExame {  
    @Override  
    public LaudoTemplate criarLaudo(String formato) {  
        return switch (formato) {  
            case "PDF" -> new LaudoPDF();  
            case "HTML" -> new LaudoHTML();  
            default -> new LaudoTexto();  
        };  
    }  
}  
```

**Vantagens**:

* Flexibilidade na criação de objetos
* Baixo acoplamento

---

### **3. Strategy (`DescontoStrategy`)**

**Problema**: Variar algoritmos de desconto sem modificar a classe principal.
**Solução**:

```java
public interface DescontoStrategy {  
    double aplicarDesconto(double valor);  
}  

public class DescontoConvenio implements DescontoStrategy {  
    @Override  
    public double aplicarDesconto(double valor) {  
        return valor * 0.85; // 15% de desconto  
    }  
}  
```

**Vantagens**:

* Algoritmos intercambiáveis
* Fácil adição de novos descontos

---

### **4. State (`StatusExame`)**

**Problema**: Gerenciar transições de estado (espera, processando, pronto, cancelado).
**Solução**:

```java
public interface StatusExame {  
    void marcarComoPronto(Exame exame);  
    void cancelar(Exame exame);  
}  

public class Pronto implements StatusExame {  
    @Override  
    public void marcarComoPronto(Exame exame) {  
        // Já está pronto, não faz nada  
    }  
}  
```

**Vantagens**:

* Lógica de estado centralizada
* Fácil manutenção

---

### **5. Template Method (`LaudoTemplate`)**

**Problema**: Garantir estrutura consistente para laudos.
**Solução**:

```java
public abstract class LaudoTemplate {  
    public final String gerarLaudoCompleto() {  
        return cabecalho() + corpo() + rodape();  
    }  
    protected abstract String corpo();  
}  
```

**Vantagens**:

* Evita duplicação de código
* Flexibilidade na implementação

---

### **6. Priority Queue (`GerenciadorDeProcessamento`)**

**Problema**: Processar exames por ordem de prioridade.
**Solução**:

```java
PriorityQueue<Exame> fila = new PriorityQueue<>(  
    Comparator.comparing(Exame::getPrioridade).reversed()  
);  
```

**Vantagens**:

* Exames urgentes são processados primeiro
* Eficiência na gestão de filas


**Prioridades**:
| Nível        | Valor | Descrição          |
|--------------|-------|--------------------|
| URGENTE      | 3     | Casos de emergência|
| PRIORITARIO  | 2     | Pacientes especiais|
| ROTINA       | 1     | Exames comuns      |

---


## Resumo dos Padrões Implementados

| Padrão            | Componente-Chave             | Aplicação                                                                 | Benefícios-Chave                                                                 |
|-------------------|------------------------------|---------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| **Abstract Factory** | `FabricaExame` e implementações | Criação de famílias de objetos (exame + laudo correspondente)            | - Isola regras de criação<br>- Garante compatibilidade entre produtos           |
| **Factory Method**  | `criarLaudo()` nas fábricas    | Instanciação de diferentes formatos de laudo (PDF, HTML, texto)           | - Flexibilidade na criação<br>- Delega responsabilidade às subclasses           |
| **Strategy**       | `DescontoStrategy`            | Cálculo dinâmico de descontos (convênio, idade)                          | - Algoritmos intercambiáveis<br>- Fácil adição de novas políticas               |
| **State**          | `StatusExame`                 | Controle do ciclo de vida (espera → processando → pronto → cancelado)     | - Simplifica transições de estado<br>- Elimina condicionais complexos           |
| **Template Method**| `LaudoTemplate`               | Estrutura padronizada para geração de laudos                              | - Reúso de código<br>- Flexibilidade na implementação de partes variáveis      |
| **Priority Queue** | `GerenciadorDeProcessamento`  | Processamento ordenado por prioridade (urgente > prioritário > rotina)    | - Eficiência operacional<br>- Atendimento conforme criticidade                  |


## **Estrutura do Projeto**

```plaintext
src/  
├── core/              # Lógica principal  
├── model/             # Entidades (Paciente, Médico, Exame)  
├── factories/         # Abstract Factory e Factory Method  
├── strategies/        # Strategy (Descontos)  
├── states/            # State (Status do Exame)  
└── templates/         # Template Method (Laudos)  
```

## **Como Executar**

1. Clone o repositório
2. Execute `SistemaDiagnosticos.main()`
3. Use o menu interativo para agendar exames

---

## **Benefícios do Sistema**

* **Escalável** (novos exames, formatos, descontos)
* **Manutenível** (padrões bem definidos)
* **Eficiente** (processamento por prioridade)
---
## **Dev**
- **Tecnologias**: Java, Padrões de Projeto
- **Licença**: [MIT](LICENSE)
- **Mermaid**: [Diagrama](https://www.mermaidchart.com/app/projects/5fe31175-96dd-4c3f-b4db-515028f1cfeb/diagrams/bdcaaad5-177a-4085-8695-a40b25135174/share/invite/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkb2N1bWVudElEIjoiYmRjYWFhZDUtMTc3YS00MDg1LTg2OTUtYTQwYjI1MTM1MTc0IiwiYWNjZXNzIjoiVmlldyIsImlhdCI6MTc1MTU1MDc1M30.DR11f8DgZZk35l2Pu9_BsORUpEhSauKz4Dhe1x_xvl4)
---
