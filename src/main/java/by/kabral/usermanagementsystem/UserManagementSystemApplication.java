package by.kabral.usermanagementsystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Class which starts application
 *
 * @author Vladislav Kabral
 */
@SpringBootApplication
public class UserManagementSystemApplication {

	/**
	 * Method which represents start point of application
	 *
	 * @param args arguments for starting application
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserManagementSystemApplication.class, args);
	}

	/**
	 * Bean for creation ModelMapper object
	 *
	 * @return ModelMapper object
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
