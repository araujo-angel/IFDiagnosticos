package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import factories.ExameFactoryRegistry;
import model.Medico;
import model.Paciente;
import model.enums.FaixaEtaria;
import model.enums.Prioridade;
import model.enums.Sexo;
import model.exame.Exame;

public class CargaDadosFacade {

    public List<Exame> carregarDados(String caminhoCsv) {
        List<Exame> exames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoCsv))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] campos = linha.split(",");

                String tipoExame = campos[0];
                String codigo = campos[1];
                double valorBase = Double.parseDouble(campos[2]);
                Date dataSolicitacao = new Date(Long.parseLong(campos[3]));
                Prioridade prioridade = Prioridade.valueOf(campos[4]);

                String nomePaciente = campos[5];
                String cpf = campos[6];
                String email = campos[7];
                Sexo sexo = Sexo.valueOf(campos[8]);
                FaixaEtaria faixaEtaria = FaixaEtaria.valueOf(campos[9]);
                boolean temConvenio = Boolean.parseBoolean(campos[10]);

                Paciente paciente = new Paciente(nomePaciente, cpf, new Date(), email, sexo, faixaEtaria, temConvenio);

                String nomeMedico = campos[11];
                String crm = campos[12];
                Medico medico = new Medico(nomeMedico, crm);

                exames.add(
                    ExameFactoryRegistry.getFactory(tipoExame)
                        .criarExame(codigo, valorBase, dataSolicitacao, prioridade, paciente, medico)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exames;
    }
}
