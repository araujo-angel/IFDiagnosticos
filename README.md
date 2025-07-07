# **Sistema de Gerenciamento de Exames Médicos**

![Texto alternativo](V3_diagrama.png)

## **Visão Geral**

O sistema é uma solução completa para gestão de exames médicos, utilizando **padrões de projeto** para garantir:

* **Processamento prioritário** de exames (urgentes, prioritários, rotina)
* **Geração de laudos** em múltiplos formatos (PDF, HTML, texto)
* **Aplicação de descontos** dinâmicos (convênio, idoso)
* **Validação especializada** por tipo de exame (hemograma, ressonância)
* **Notificação automatizada** quando laudos estão prontos

---

## **Diagrama de Fluxo do Sistema (Atualizado)**

```mermaid
flowchart TD
    SistemaDiagnosticos -->|1. Agenda| FabricaExame
    FabricaExame -->|2. Cria| Exame
    Exame -->|3. Valida| ValidadorFactory
    ValidadorFactory -->|ValidadorEspecífico| Exame
    SistemaDiagnosticos -->|4. Envia| GerenciadorDeProcessamento
    GerenciadorDeProcessamento -->|5. Processa| ProcessadorPagamento
    ProcessadorPagamento -->|6. Aplica| DescontoStrategy
    GerenciadorDeProcessamento -->|7. Gera| GeradorLaudo
    GeradorLaudo -->|8. Formata| LaudoTemplate
    GerenciadorDeProcessamento -->|9. Notifica| NotificadorLaudoPronto
```

# Padrões de Projeto e Suas Aplicações

## 1. Abstract Factory (`FabricaExame`)

**Problema**: Criar famílias de objetos relacionados (exames + validadores) de forma consistente.

**Solução**:
```java
FabricaExame fabrica = new FabricaHemograma();
Exame exame = fabrica.criarExame(); // Retorna Hemograma
ValidadorExame validador = ValidadorFactory.criarValidador(exame);

**Componentes Relacionados**:

* FabricaHemograma

* FabricaRessonancia

* ValidadorFactory

```

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
public class ProcessadorPagamento {
    private DescontoStrategy estrategia;
    
    public void setEstrategia(DescontoStrategy estrategia) {
        this.estrategia = estrategia;
    }
}

```
**Vantagens**:

* Algoritmos intercambiáveis
* Fácil adição de novos descontos

**Estratégias Implementadas**:

* DescontoConvenio (15%)

* DescontoIdoso (8%)

---

### **4. State (`StatusExameState`)**

**Problema**: Gerenciar transições de estado (espera, processando, pronto, cancelado).
**Solução**:

```java
public class Exame {
    private StatusExameState estado;
    
    public void mudarEstado() {
        estado.mudarEstado(this);
    }
}

```

**Vantagens**:

* Lógica de estado centralizada
* Fácil manutenção

**Estados Implementados**:

* ExamePendente
* ExameProcessando
* ExameConcluido
* ExameCancelado

---

### **5. Template Method (`LaudoTemplate`)**

**Problema**:  Definir estrutura comum para laudos com partes variáveis.
**Solução**:

```java
public abstract class LaudoTemplate {  
    public final String gerarLaudoCompleto() {  
      return gerarCabecalho() + gerarCorpo() + gerarRodape();  
    }  
    // Métodos abstratos implementados nas subclasses
}  
```

**Vantagens**:

* Evita duplicação de código
* Flexibilidade na implementação

**Implementações**:

* LaudoPDF
* LaudoHTML
* LaudoTexto

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
| Nível        | Valor | Classe Correspondente |
|--------------|-------|--------------------|
| URGENTE      | 3     | Prioridade.URGENTE|
| PRIORITARIO  | 2     | Prioridade.PRIORITARIO|
| ROTINA       | 1     | Prioridade.ROTINA |

---

## **Estrutura do Projeto**

```plaintext
src/
├── core/
│   ├── SistemaDiagnosticos.java
│   └── GerenciadorDeProcessamentoDeExames.java
├── model/
│   ├── Paciente.java
│   ├── Medico.java
│   └── Exame.java
├── factories/
│   ├── FabricaExame.java
│   ├── FabricaHemograma.java
│   └── FabricaRessonancia.java
├── strategies/
│   ├── DescontoStrategy.java
│   ├── DescontoConvenio.java
│   └── DescontoIdoso.java
├── states/
│   ├── StatusExameState.java
│   ├── ExamePendente.java
│   └── ExameConcluido.java
├── templates/
│   ├── LaudoTemplate.java
│   ├── LaudoPDF.java
│   └── LaudoHTML.java
└── validators/
    ├── ValidadorFactory.java
    ├── ValidadorHemograma.java
    └── ValidadorRessonancia.java

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
