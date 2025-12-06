package school.sptech;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class BancoRepositorio {

//  private static final String URL = System.getenv("DB_URL");
//   private static final String USER = System.getenv("DB_USER");
//   private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private static final String URL = "jdbc:mysql://localhost:3306/SchoolMapping?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";



    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public BancoRepositorio() {

        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl(URL);
        basicDataSource.setUsername(USER);
        basicDataSource.setPassword(PASSWORD);

//        basicDataSource.setUrl(System.getenv("DB_URL"));
//        basicDataSource.setUsername(System.getenv("DB_USER"));
//        basicDataSource.setPassword(System.getenv("DB_PASSWORD"));

        this.dataSource = basicDataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
