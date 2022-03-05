package com.media.notifier.air.alarm.impl.config.annotation.slices;

import com.media.notifier.air.alarm.impl.config.annotation.ModuleProfiles;
import com.media.notifier.integratoin.test.config.annotation.EnableSqlLogging;
import com.media.notifier.integratoin.test.config.annotation.MySqlTestConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DataJpaTest(showSql = false)
@MySqlTestConfig
@EnableSqlLogging
@ModuleProfiles
public @interface PersistenceTest {
}
