package com.media.notifier.author.impl.config.annotation.slices;

import com.media.notifier.author.impl.config.annotation.ModuleProfiles;
import com.media.notifier.integratoin.test.config.annotation.MySqlTestConfig;
import org.springframework.boot.test.context.SpringBootTest;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MySqlTestConfig
@ModuleProfiles
public @interface ApplicationTest {
}
