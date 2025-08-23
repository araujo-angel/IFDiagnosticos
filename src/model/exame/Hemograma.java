package model.exame;

import java.util.Date;

import model.Medico;
import model.Paciente;
import model.enums.Prioridade;

public class Hemograma extends Exame {
    private double hemoglobina;
    private double leucocitos;
    private double hematocrito;
    private int plaquetas;

    public Hemograma(String codigo, double valorBase, Date dataSolicitacao, Prioridade prioridade, Paciente paciente, Medico medico) {
        super(codigo, valorBase, dataSolicitacao, prioridade, paciente, medico);
        
    }
    
    public void setHemoglobina(double hemoglobina) {
        this.hemoglobina = hemoglobina;
    }

    public double getHemoglobina() {
        return hemoglobina;
    }

    public void setHematocrito(double hematocrito) {
        this.hematocrito = hematocrito;
    }

    public double getHematocrito() {
        return hematocrito;
    }
    
    public void setLeucocitos(double leucocitos) {
        this.leucocitos = leucocitos;
    }

    public double getLeucocitos() {
        return leucocitos;
    }
    public void setPlaquetas(int plaquetas) {
        this.plaquetas = plaquetas;
    }

    public double getPlaquetas() {
        return plaquetas;
    }
}
