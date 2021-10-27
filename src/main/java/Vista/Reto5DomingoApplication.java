package Vista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import Controlador.Controlador;
import Modelo.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan("modelo")
@EnableJdbcRepositories("modelo")


public class Reto5DomingoApplication {
   @Autowired
    RepositorioProducto repositorio;

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Reto5DomingoApplication.class);
                builder.headless(false);
                ConfigurableApplicationContext context = builder.run(args);
	}
         @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            Principal1 principal = new Principal1();
            Actualizar actualizar= new Actualizar();
            Controlador controlador = new Controlador(repositorio, principal, actualizar);
            
        };
    }
	

}
