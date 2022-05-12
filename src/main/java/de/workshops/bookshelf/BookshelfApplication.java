package de.workshops.bookshelf;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookshelfApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookshelfApplication.class, args);
	}

//	public static void main(String[] args) {
//		SpringApplication application = new SpringApplication(BookshelfApplication.class);
//		application.setBannerMode(Banner.Mode.OFF);
//		application.run(args);
//	}
}
