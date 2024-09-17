package ma.maroc.echecs.chessportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ma.maroc.echecs.chessportal")
public class ChessPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessPortalApplication.class, args);
	}

}
