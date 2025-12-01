package school.sptech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Main.class);

        S3Provider s3Provider = new S3Provider();
        S3Client s3Client = s3Provider.getS3Client();
        ApachePOI apachepoi = new ApachePOI(s3Client);
        BancoRepositorio bancoRepositorio = new BancoRepositorio();


        Integer contador = 0;

        List<Escola> escolas = apachepoi.extrairEscolas();

        logger.info("[{}] == INSERINDO ESCOLAS ==", LocalDateTime.now());

        for (Escola escola : escolas) {
            Boolean adicionarEscola = true;

            List<Escola> listEscolasDB = bancoRepositorio.getJdbcTemplate().query("SELECT * FROM TB_Escolas", new BeanPropertyRowMapper<>(Escola.class));

            for (Escola listEscola : listEscolasDB) {
                if (escola.getCodigoInep().equals(listEscola.getCodigoInep())) {
                    logger.info("[{}] Escola já existe: {}", LocalDateTime.now(), escola.getNome());
                    adicionarEscola = false;
                }
            }


            if (adicionarEscola) {
                String sql = "INSERT INTO TB_Escolas (nome, codigo_inep, data_processamento) VALUES (?, ?, ?)";
                bancoRepositorio.getJdbcTemplate().update(sql, escola.getNome(), escola.getCodigoInep(), LocalDate.now());
                logger.info("[{}] Inserindo escola: {}", LocalDateTime.now(), escola.getNome());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Escola inserida: " + escola.getNome(), "Main");
            }

        }

        List<Endereco> enderecos = apachepoi.getListaEnderecos();

        logger.info("[{}] == INSERÇÃO DE ESCOLAS FINALIZADA ==", LocalDateTime.now());
        logger.info("[{}] == INSERINDO ENDEREÇOS ==", LocalDateTime.now());

        for (Endereco endereco : enderecos) {

            Integer escolaId;

            List<Escola> allEscolasDB = new ArrayList<>();

            try {
                escolaId = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT id FROM TB_Escolas WHERE codigo_inep = ?", Integer.class, endereco.getCodigoInep().trim());
            } catch (EmptyResultDataAccessException e) {
                continue;
            }

            try {
                allEscolasDB = bancoRepositorio.getJdbcTemplate().query("SELECT * FROM TB_Escolas", new BeanPropertyRowMapper<>(Escola.class));
            } catch (EmptyResultDataAccessException e) {

            }

            List<Endereco> listEnderecosDB = bancoRepositorio.getJdbcTemplate().query("SELECT * FROM TB_Enderecos WHERE cep = ? and logradouro = ? and numero = ? and bairro = ? and id_regiao = ?", new BeanPropertyRowMapper<>(Endereco.class), endereco.getCep(), endereco.getLogradouro(), endereco.getNumero(), endereco.getBairro(), endereco.getRegiao().getId());

            if (!listEnderecosDB.isEmpty()) {
                logger.info("[{}] Endereço já existe: {}", LocalDateTime.now(), endereco.getLogradouro());
                continue;
            }

            if (escolaId != null) {
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Enderecos (logradouro, numero, cep, bairro, id_regiao, data_processamento) VALUES (?, ?, ?, ?, ?, ?)", endereco.getLogradouro(), endereco.getNumero(), endereco.getCep(), endereco.getBairro(), endereco.getRegiao().getId(), LocalDate.now());
                logger.info("[{}] Inserindo endereço: {}", LocalDateTime.now(), endereco.getLogradouro());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Endereço inserido: " + endereco.getLogradouro(), "Main");
            }

            for (Escola escola : allEscolasDB) {
                if (escola.getCodigoInep().equals(endereco.getCodigoInep())) {
                    Integer enderecoId = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT id FROM TB_Enderecos WHERE cep = ? and logradouro = ? and numero = ? and bairro = ? and id_regiao = ?", Integer.class, endereco.getCep(), endereco.getLogradouro(), endereco.getNumero(), endereco.getBairro(), endereco.getRegiao().getId());
                    bancoRepositorio.getJdbcTemplate().update("UPDATE TB_Escolas SET id_endereco = ? WHERE codigo_inep = ?", enderecoId, endereco.getCodigoInep());
                    logger.info("[{}] Atualizando endereço: {}", LocalDateTime.now(), endereco.getLogradouro());
                    bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Endereço atualizado: " + endereco.getLogradouro(), "Main");
                    break;
                }
            }

        }

        List<Ideb> listIdeb = apachepoi.extrairIdeb();

        logger.info("[{}] === INSERÇÃO DE ENDEREÇOS FINALIZADA ===", LocalDateTime.now());
        logger.info("[{}] === INSERINDO NOTAS IDEB ===", LocalDateTime.now());

        for (Ideb ideb : listIdeb) {

            if (ideb.getIdeb() == null) {
                continue;
            }

            try {
                Integer escolaId = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT id FROM TB_Escolas WHERE codigo_inep = ?", Integer.class, ideb.getCodigoInep());

                List<Ideb> listIdebDB = bancoRepositorio.getJdbcTemplate().query("SELECT * FROM TB_Ideb WHERE id_escola = ? and ano_emissao = ?", new BeanPropertyRowMapper<>(Ideb.class), escolaId, ideb.getAnoEmissao());

                if (!listIdebDB.isEmpty()) {
                    logger.info("[{}] IDEB já existe: {}", LocalDateTime.now(), ideb.getIdeb());
                    continue;
                }

                logger.info("[{}] Inserindo ideb: {}", LocalDateTime.now(), ideb.getIdeb());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Ideb (id_escola, nota, ano_emissao, data_processamento) VALUES (?, ?, ?, ?)", escolaId, ideb.getIdeb(), ideb.getAnoEmissao(), LocalDate.now());
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", LocalDateTime.now(), "INFO", "Ideb inserido: " + ideb.getIdeb(), "Main");
                contador++;
            } catch (EmptyResultDataAccessException e) {
                continue;
            }
        }

        List<Verba> verbas = apachepoi.extrairVerbas();

        logger.info("[{}] === INSERÇÃO DO IDEB FINALIZADA ===", LocalDateTime.now());

        logger.info("[{}] === INSERINDO VERBAS ===", LocalDateTime.now());

        for (Verba verba : verbas) {
            try {
                Boolean valorInserido = false;
                List<Escola> escolasDB = bancoRepositorio.getJdbcTemplate().query("SELECT * FROM TB_Escolas", new BeanPropertyRowMapper<>(Escola.class));

                for (Escola escola : escolasDB) {
                    if (escola.getNome().equalsIgnoreCase(verba.getNomeEscola().trim())) {
                        Integer escolaId = escola.getId();

                        List<Verba> listVerbasDB = bancoRepositorio.getJdbcTemplate().query(
                            "SELECT * FROM TB_Verbas WHERE id_escola = ? AND ano = ?",
                            new BeanPropertyRowMapper<>(Verba.class),
                            escolaId,
                            verba.getAno()
                        );

                        if (!listVerbasDB.isEmpty()) {
                            bancoRepositorio.getJdbcTemplate().update(
                                "UPDATE TB_Verbas SET valor_primeira_parcela = ?, " +
                                "valor_segunda_parcela = ?, " +
                                "valor_terceira_parcela = ?, " +
                                "valor_vulnerabilidade = ?, " +
                                "valor_extraordinario = ?, " +
                                "valor_gremio = ? " +
                                "WHERE id_escola = ? AND ano = ?",
                                verba.getValorPrimeiraParcela(),
                                verba.getValorSegundaParcela(),
                                verba.getValorTerceiraParcela(),
                                verba.getValorVulnerabilidade(),
                                verba.getValorExtraordinario(),
                                verba.getValorGremio(),
                                escolaId,
                                verba.getAno()
                            );
                            logger.info("[{}] Verba atualizada para a escola: {} (ID: {})", LocalDateTime.now(), escola.getNome(), escolaId);
                        } else {
                            bancoRepositorio.getJdbcTemplate().update(
                                "INSERT INTO TB_Verbas (id_escola, ano, valor_primeira_parcela, " +
                                "valor_segunda_parcela, valor_terceira_parcela, valor_vulnerabilidade, " +
                                "valor_extraordinario, valor_gremio, data_processamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                escolaId,
                                verba.getAno(),
                                verba.getValorPrimeiraParcela(),
                                verba.getValorSegundaParcela(),
                                verba.getValorTerceiraParcela(),
                                verba.getValorVulnerabilidade(),
                                verba.getValorExtraordinario(),
                                verba.getValorGremio(),
                                LocalDate.now()
                            );
                            logger.info("[{}] Nova verba inserida para a escola: {} (ID: {})", LocalDateTime.now(), escola.getNome(), escolaId);
                        }

                        valorInserido = true;
                        contador++;
                        break;
                    }
                }

                if (!valorInserido) {
                    String nomeEscolaSimilar = verba.encontrarEscola(escolasDB);
                    if (nomeEscolaSimilar != null && !nomeEscolaSimilar.isEmpty()) {
                        try {
                            Escola escolaSimilar = bancoRepositorio.getJdbcTemplate().queryForObject(
                                "SELECT * FROM TB_Escolas WHERE nome = ?",
                                new BeanPropertyRowMapper<>(Escola.class),
                                nomeEscolaSimilar
                            );

                            if (escolaSimilar != null) {
                                List<Verba> listVerbasDB = bancoRepositorio.getJdbcTemplate().query(
                                    "SELECT * FROM TB_Verbas WHERE id_escola = ? AND ano = ?",
                                    new BeanPropertyRowMapper<>(Verba.class),
                                    escolaSimilar.getId(),
                                    verba.getAno()
                                );

                                if (listVerbasDB.isEmpty()) {
                                    bancoRepositorio.getJdbcTemplate().update(
                                        "INSERT INTO TB_Verbas (id_escola, ano, valor_primeira_parcela, " +
                                        "valor_segunda_parcela, valor_terceira_parcela, valor_vulnerabilidade, " +
                                        "valor_extraordinario, valor_gremio, data_processamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                        escolaSimilar.getId(),
                                        verba.getAno(),
                                        verba.getValorPrimeiraParcela(),
                                        verba.getValorSegundaParcela(),
                                        verba.getValorTerceiraParcela(),
                                        verba.getValorVulnerabilidade(),
                                        verba.getValorExtraordinario(),
                                        verba.getValorGremio(),
                                        LocalDate.now()
                                    );
                                    logger.info("[{}] Nova verba inserida por similaridade para a escola: {} | Similar: {} (ID: {})",
                                        LocalDateTime.now(), escolaSimilar.getNome(), verba.getNomeEscola(), escolaSimilar.getId());
                                    contador++;
                                } else {
                                    logger.info("[{}] Verba já existe para a escola similar: {} (ID: {})",
                                        LocalDateTime.now(), escolaSimilar.getNome(), escolaSimilar.getId());
                                }
                            }
                        } catch (EmptyResultDataAccessException e) {
                            logger.info("[{}] Nenhuma escola similar encontrada para: {}", LocalDateTime.now(), verba.getNomeEscola());
                        }
                    }
                }

            } catch (EmptyResultDataAccessException e) {
                logger.info("[{}] Verba não encontrada: {}", LocalDateTime.now(), verba.getNomeEscola());
            }
        }

        SlackNotifier slackNotifier = new SlackNotifier();

        logger.info("[{}] === INSERÇÃO DE VERBAS FINALIZADA ===", LocalDateTime.now());

        slackNotifier.notificar(String.format("[%s] === NOVOS DADOS PRESENTES EM NOSSA APLICAÇÂO ===", LocalDateTime.now()));


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
