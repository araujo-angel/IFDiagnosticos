package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Paciente {
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private boolean temConvenio;
    private ArrayList<Exame> exames = new ArrayList<Exame>();

    public Paciente(String nome, String cpf, Date dataNascimento, boolean temConvenio) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.temConvenio = temConvenio;
    }

    public void setNome(String nome)  {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setCpf(String cpf)  {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setConvenio(boolean convenio)  {
        this.temConvenio = convenio;
    }

    public boolean getConvenio() {
        return temConvenio;
    }

    public int getIdade() {

        LocalDate nascimento = dataNascimento.toInstant()
                                             .atZone(ZoneId.systemDefault())
                                             .toLocalDate();

        LocalDate hoje = LocalDate.now();
        int idade = Period.between(nascimento, hoje).getYears();

        return idade;

    }

    public void adicionarExame(Exame exame) {
        exames.add(exame);
    }
}
