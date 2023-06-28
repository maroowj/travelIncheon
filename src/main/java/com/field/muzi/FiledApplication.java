package com.field.muzi;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManager;
import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableJpaAuditing(dateTimeProviderRef = "localDateTimeProvider")
@Slf4j
@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class FiledApplication implements ApplicationListener {

	private final ApplicationContext applicationContext;

	public int port;
	public String ip;

	public static void main(String[] args) {
		SpringApplication.run(FiledApplication.class, args);
	}

	public String hostAddress() {
		return "http://" + ip + ":" + port;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		try {
			this.ip = InetAddress.getLocalHost().getHostAddress();
			this.port = applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class, 8080);
//			this.port = 8080;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
