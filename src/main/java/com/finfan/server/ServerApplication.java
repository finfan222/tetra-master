package com.finfan.server;

import io.netty.channel.Channel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableScheduling
public class ServerApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);
		context.getBean(Channel.class).closeFuture().sync();
	}

}
