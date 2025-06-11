package dev.branches.config;

import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import(TestcontainersConfig.class)
@ActiveProfiles("integrationTest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {
}
