package com.example.TP2_Guilda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
/*
	Comeco Habilitando o Cache para armazerar em memoria o resultado das querryes
 	e o Scheaduling para limpeza automatica a cada 1 minuto, para garantir atualizacao dos dados
 */


@SpringBootApplication
public class Tp2GuildaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tp2GuildaApplication.class, args);

	}

}
