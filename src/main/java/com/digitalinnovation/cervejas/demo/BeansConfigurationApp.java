package com.digitalinnovation.cervejas.demo;

import com.digitalinnovation.cervejas.demo.dto.CervejaDTO;
import com.digitalinnovation.cervejas.demo.entities.Cerveja;
import com.digitalinnovation.cervejas.demo.mapper.CervejaMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Configuration
public class BeansConfigurationApp {

    @Bean
    public CervejaMapper mapper() {
        return new CervejaMapper() {
            @Override
            public Cerveja toEntity(CervejaDTO dto) {
                Cerveja cerveja = new Cerveja();
                return null;
            }

            @Override
            public CervejaDTO toDTO(Cerveja cerveja) {
                return null;
            }

            @Override
            public CervejaDTO toDTOByOptional(Optional<Cerveja> cerveja) {
                return null;
            }

            @Override
            public List<CervejaDTO> toDTOList(List<Cerveja> list) {
                return null;
            }

            @Override
            public List<Cerveja> toEntityList(List<CervejaDTO> dtoList) {
                return null;
            }
        };
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //jdbc:mysql://localhost:port/bd_name?useTimezone=true&serverTimezone=UTC
        //jdbc:mysql://localhost:3306/gerenciadordecervejas
        dataSource.setUrl("jdbc:mysql://localhost:3306/gerenciadordecervejas?useTimezone=true&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("M@r2019bet");
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        //org.hibernate.dialect.MySQL5Dialect
        //org.hibernate.dialect.MySQLDialect
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        adapter.setPrepareConnection(true);
        return adapter;
    }
}
