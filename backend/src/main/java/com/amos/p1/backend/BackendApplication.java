package com.amos.p1.backend;
 
import com.amos.p1.backend.database.DatabaseConfig;
import com.amos.p1.backend.database.DatabaseHelper;
import com.amos.p1.backend.database.MyRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {
 
	public static void main(String[] args) {

		DatabaseConfig databaseConfig = new DatabaseConfig();
		DatabaseHelper databaseHelper = new DatabaseHelper(databaseConfig);
		databaseHelper.waitForDatabase(10000);

		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public ThreadPoolTaskScheduler taskScheduler(){
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(20);
		return taskScheduler;
	}
}