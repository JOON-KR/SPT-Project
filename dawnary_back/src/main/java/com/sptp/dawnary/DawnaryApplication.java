package com.sptp.dawnary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class DawnaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DawnaryApplication.class, args);
	}

}
