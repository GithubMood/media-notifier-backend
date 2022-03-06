package com.media.notifier.annotation.slices;

import com.github.database.rider.spring.api.DBRider;
import com.media.notifier.config.MockBeansConfiguration;
import com.media.notifier.integratoin.test.config.initializer.MySqlContainerInitializer;
import com.media.notifier.integratoin.test.config.initializer.RedisContainerInitializer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DBRider
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = NONE)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {MySqlContainerInitializer.class, RedisContainerInitializer.class})
@Import(value = {
        MockBeansConfiguration.class
})
public @interface ApplicationTest {
}
