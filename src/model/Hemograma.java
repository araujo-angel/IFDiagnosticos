package model;

import java.util.Date;

public class Hemograma extends Exame {
    private double hemoglobina;
    private double leucocitos;

    public Hemograma(String codigo, double valorBase, Date dataSolicitacao, Prioridade prioridade, Paciente paciente, Medico medico) {
        super(codigo, valorBase, dataSolicitacao, prioridade, paciente, medico);
        
    }
    
    public void setHemoglobina(double hemoglobina) {
        this.hemoglobina = hemoglobina;
    }

    public double getHemoglobina() {
        return hemoglobina;
    }
    
    public void setLeucocitos(double leucocitos) {
        this.leucocitos = leucocitos;
    }

    public double getLeucocitos() {
        return leucocitos;
    }
}
