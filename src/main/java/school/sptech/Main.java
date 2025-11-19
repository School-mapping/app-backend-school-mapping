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

        Logger logger = LoggerFactory.getLogger(Main.class);

        S3Provider s3Provider = new S3Provider();
        S3Client s3Client = s3Provider.getS3Client();
        ApachePOI apachepoi = new ApachePOI(s3Client);
        BancoRepositorio bancoRepositorio = new BancoRepositorio();

        List<Escola> escolas = apachepoi.extrairEscolas();
        List<Ideb> listIdeb = apachepoi.extrairIdeb();
        List<Endereco> enderecos = apachepoi.getListaEnderecos();
        List<Verba> verbas = apachepoi.extrairVerbas();

        Integer contador = 0;


        logger.info("[{}] == INSERINDO ESCOLAS ==", LocalDateTime.now());

        for (Escola escola : escolas) {
            Boolean adicionarEscola = true;

            List<Escola> listEscolasDB = bancoRepositorio.getJdbcTemplate().query("SELECT * FROM TB_Escolas", new BeanPropertyRowMapper<>(Escola.class));

            for (Escola listEscola : listEscolasDB) {
                if (escola.getCodigoInep().equals(listEscola.getCodigoInep())) {
                    adicionarEscola = false;
                }
            }


            if (adicionarEscola) {
                String sql = "INSERT INTO TB_Escolas (nome, codigo_inep) VALUES (?, ?)";
                bancoRepositorio.getJdbcTemplate().update(sql, escola.getNome(), escola.getCodigoInep());
                logger.info("[{}] Inserindo escola: {}", LocalDateTime.now(), escola.getNome());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Escola inserida: " + escola.getNome(), "Main");
            }

        }

        logger.info("[{}] == INSERÇÃO DE ESCOLAS FINALIZADA ==", LocalDateTime.now());
        logger.info("[{}] == INSERINDO ENDEREÇOS ==", LocalDateTime.now());

        for (Endereco endereco : enderecos) {

            Integer escolaId;

            try {
                escolaId = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT id FROM TB_Escolas WHERE codigo_inep = ?", Integer.class, endereco.getCodigoInep().trim());
            } catch (EmptyResultDataAccessException e) {
                continue;
            }

            if (escolaId != null) {
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Enderecos (logradouro, numero, cep, bairro, id_regiao) VALUES (?, ?, ?, ?, ?)", endereco.getLogradouro(), endereco.getNumero(), endereco.getCep(), endereco.getBairro(), endereco.getRegiao().getId());
                logger.info("[{}] Inserindo endereço: {}", LocalDateTime.now(), endereco.getLogradouro());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Endereço inserido: " + endereco.getLogradouro(), "Main");
            }

        }

        logger.info("[{}] === INSERÇÃO DE ENDEREÇOS FINALIZADA ===", LocalDateTime.now());
        logger.info("[{}] === INSERINDO IDEB ===", LocalDateTime.now());

        for (Ideb ideb : listIdeb) {
            try {
                Integer escolaId = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT id FROM TB_Escolas WHERE codigo_inep = ?", Integer.class, ideb.getCodigoInep());
                logger.info("[{}] Inserindo ideb: {}", LocalDateTime.now(), ideb.getIdeb());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Ideb (id_escola, nota, ano_emissao) VALUES (?, ?, ?)", escolaId, ideb.getIdeb(), ideb.getAnoEmissao());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Ideb inserido: " + ideb.getIdeb(), "Main");
            } catch (EmptyResultDataAccessException e) {
                continue;
            }
            contador++;
        }

        logger.info("[{}] === INSERÇÃO DO IDEB FINALIZADA ===", LocalDateTime.now());

        logger.info("[{}] === INSERINDO VERBAS ===", LocalDateTime.now());

        for (Verba verba : verbas) {
            try {
                Integer escolaId = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT id FROM TB_Escolas WHERE nome = ?", Integer.class, verba.getNomeEscola().trim());
                logger.info("[{}] Inserindo verba: {}", LocalDateTime.now(), verba.getNomeEscola());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Verbas (id_escola, ano, valor_primeira_parcela, valor_segunda_parcela, valor_terceira_parcela, valor_vulnerabilidade, valor_extraordinario, valor_gremio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", escolaId, verba.getAno(), verba.getValorPrimeiraParcela(), verba.getValorSegundaParcela(), verba.getValorTerceiraParcela(), verba.getValorVulnerabilidade(), verba.getValorExtraordinario(), verba.getValorGremio());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Verba inserido: " + verba.getValorPrimeiraParcela(), "Main");
            } catch (EmptyResultDataAccessException e) {
                continue;
            }
            contador++;
        }

        logger.info("[{}] === INSERÇÃO DE VERBAS FINALIZADA ===", LocalDateTime.now());

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
