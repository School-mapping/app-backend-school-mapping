package school.sptech;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        S3Provider s3Provider = new S3Provider();
        S3Client s3Client = s3Provider.getS3Client();
        ApachePOI apachepoi = new ApachePOI(s3Client);
        BancoRepositorio bancoRepositorio = new BancoRepositorio();

        List<Escola> escolas = apachepoi.extrairEscolas();
        List<Ideb> ideb = apachepoi.extrairIdeb();
        List<Endereco> enderecos = apachepoi.getListaEnderecos();

        Integer contador = 0;

        for (int i = 0; i < escolas.size(); i++) {

            Escola escolaAtual = escolas.get(i);

            for (int j = 0; j < ideb.size(); j++) {

                Ideb dadoAtual = ideb.get(j);

                if (dadoAtual.getCodigoInep().equalsIgnoreCase(escolaAtual.getCodigoInep())) {
                    escolaAtual.setIdeb(dadoAtual.getIdeb());
                    break;
                }
            }
        }

        for (Escola escola : escolas) {
            if (escola.getIdeb() != null) {

                Integer count = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM TB_Escolas WHERE cep = ?", Integer.class, escola.getCep());

                System.out.println("Inserido escolas...");

                if (count == 0) {
                    String sql = "INSERT INTO TB_Escolas (nome, codigo_inep, ideb, cep) VALUES (?, ?, ?, ?)";
                    bancoRepositorio.getJdbcTemplate().update(sql, escola.getNome(), escola.getCodigoInep(), escola.getIdeb(), escola.getCep());
                }
            }
        }

        for (Endereco endereco : enderecos) {
            System.out.println("Inserindo endereços...");

            Integer escolaId;

            try {
                 escolaId = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT id FROM TB_Escolas WHERE cep = ?", Integer.class, endereco.getCep().trim());
            } catch (EmptyResultDataAccessException e){
                continue;
            }

            if (escolaId != null) {
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Enderecos (escola_id, logradouro, numero, bairro, zona) VALUES (?, ?, ?, ?, ?)", escolaId, endereco.getLogradouro(), endereco.getNumero(), endereco.getBairro(), endereco.getZona());
            }

        }

        LocalDateTime agora = LocalDateTime.now();

        System.out.println("\n-------------------- Logs -------------------");
        System.out.println("[" + agora + "] INFO - Leitura concluída: " + apachepoi.getLinhasLidas() + " linhas processadas.");
        bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data, nivel, descricao, origem) VALUES (?, ?, ?, ?)", agora, "INFO", "Leitura concluída: " + apachepoi.getLinhasLidas() + " linhas processadas.", "Main");
        System.out.println("[" + agora + "] INFO - Linhas puladas: " + apachepoi.getLinhasPuladas() + ".");
        bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data, nivel, descricao, origem) VALUES (?, ?, ?, ?)", agora, "INFO", "Linhas puladas: " + apachepoi.getLinhasPuladas(), "Main");
        System.out.println("[" + agora + "] INFO - Dados cruzados pelo codigoInep: " + contador + " linhas processadas.");
        bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data, nivel, descricao, origem) VALUES (?, ?, ?, ?)", agora, "INFO", "Dados cruzados pelo codigoInep: " + contador + " linhas processadas.", "Main");
    }
}
