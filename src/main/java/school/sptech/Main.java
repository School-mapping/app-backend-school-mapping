package school.sptech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(ApachePOI.class);

        S3Provider s3Provider = new S3Provider();
        S3Client s3Client = s3Provider.getS3Client();
        ApachePOI apachepoi = new ApachePOI(s3Client);
        BancoRepositorio bancoRepositorio = new BancoRepositorio();

        List<Escola> escolas = apachepoi.extrairEscolas();
        List<Ideb> listIdeb = apachepoi.extrairIdeb();
        List<Endereco> enderecos = apachepoi.getListaEnderecos();

        Integer contador = 0;

        for (Escola escola : escolas) {
            Boolean adicionarEscola = true;

            List<Escola> listEscolasDB = bancoRepositorio.getJdbcTemplate().query("SELECT * FROM TB_Escolas", new BeanPropertyRowMapper<>(Escola.class));

            for (Escola listEscola : listEscolasDB) {
                if (escola.getCodigoInep().equals(listEscola.getCodigoInep())) {
                    adicionarEscola = false;
                }
            }

            System.out.println("Inserido escolas...");

            if (adicionarEscola) {
                String sql = "INSERT INTO TB_Escolas (nome, codigo_inep) VALUES (?, ?)";
                bancoRepositorio.getJdbcTemplate().update(sql, escola.getNome(), escola.getCodigoInep());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Escola inserida: " + escola.getNome(), "Main");
            }

        }

        for (Endereco endereco : enderecos) {
            System.out.println("Inserindo endereços...");

            Integer escolaId;

            try {
                escolaId = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT id FROM TB_Escolas WHERE codigo_inep = ?", Integer.class, endereco.getCodigoInep().trim());
            } catch (EmptyResultDataAccessException e) {
                continue;
            }

            if (escolaId != null) {
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Enderecos (logradouro, numero, cep, bairro, id_regiao) VALUES (?, ?, ?, ?, ?)", endereco.getLogradouro(), endereco.getNumero(), endereco.getCep(), endereco.getBairro(), endereco.getRegiao().getId());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Endereço inserido: " + endereco.getLogradouro(), "Main");
            }

        }

        for (Ideb ideb : listIdeb) {
            try {
                Integer escolaId = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT id FROM TB_Escolas WHERE codigo_inep = ?", Integer.class, ideb.getCodigoInep());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Ideb (id_escola, nota, ano_emissao) VALUES (?, ?, ?)", escolaId, ideb.getIdeb(), ideb.getAnoEmissao());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Ideb inserido: " + ideb.getIdeb(), "Main");
            } catch (EmptyResultDataAccessException e) {
                continue;
            }
            contador++;
        }

        LocalDateTime agora = LocalDateTime.now();

        logger.info("-------------------- Logs -------------------");
        logger.info("[{}] INFO - Leitura concluída: {} linhas processadas.", agora, apachepoi.getLinhasLidas());
        bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", agora, "INFO", "Leitura concluída: " + apachepoi.getLinhasLidas() + " linhas processadas.", "Main");
        logger.info("[{}] INFO - Linhas puladas: {} .", agora, apachepoi.getLinhasPuladas());
        bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", agora, "INFO", "Linhas puladas: " + apachepoi.getLinhasPuladas(), "Main");
        logger.info("[{}] INFO - Dados cruzados pelo codigoInep: {}  linhas cruzadas.", agora, contador);
        bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", agora, "INFO", "Dados cruzados pelo codigoInep: " + contador + " linhas processadas.", "Main");
    }
}
