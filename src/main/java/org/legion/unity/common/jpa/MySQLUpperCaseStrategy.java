package org.legion.unity.common.jpa;


import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public class MySQLUpperCaseStrategy extends SpringPhysicalNamingStrategy {
    @Override
    protected boolean isCaseInsensitive(JdbcEnvironment jdbcEnvironment) {
        return false;
    }
/*
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return Identifier.toIdentifier(name.getText().toUpperCase());
    }*/
}
