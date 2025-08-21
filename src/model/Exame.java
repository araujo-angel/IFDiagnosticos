package model;

import java.util.Date;

import state.ExameSolicitado;
import state.StatusExameState;

public abstract class Exame {
    private String codigo;
    private double valorBase;
    private Date dataSolicitacao;
    private Prioridade prioridade;
    private Paciente paciente;
    private Medico medico;
    private StatusExameState estado;
    //private LaudoTemplate laudo;


    public Exame(String codigo, double valorBase, Date dataSolicitacao, Prioridade prioridade, Paciente paciente, Medico medico) {//, LaudoTemplate laudo) {
        this.codigo = codigo;
        this.valorBase = valorBase;
        this.dataSolicitacao = dataSolicitacao;
        this.prioridade = prioridade;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = new ExameSolicitado();
        //this.laudo = laudo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public StatusExameState getEstado() {
        return estado;
    }

    public void setEstado(StatusExameState estado) {
        this.estado = estado;
    }

    public void avancarEstado() {
        estado.mudarEstadoExame(this);
    }

    public void cancelarExame() {
        estado.cancelarExame(this);
    }

    // public LaudoTemplate getLaudo() {
    //     return laudo;
    // }

    // public void setLaudo(LaudoTemplate laudo) {
    //     this.laudo = laudo;
    // }

    

}
