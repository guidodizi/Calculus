package calculus.backend;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"calculus.backend.service","calculus.backend.repository","calculus.backend.controller"})
public class CalculusBackendConfiguration {
    
}