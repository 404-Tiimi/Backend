package com._4.questionnaire;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com._4.questionnaire.domain.Inquiry;
import com._4.questionnaire.domain.InquiryRepository;
import com._4.questionnaire.domain.Question;
import com._4.questionnaire.domain.QuestionRepository;
import com._4.questionnaire.domain.User;
import com._4.questionnaire.domain.UserRepository;

@SpringBootApplication
public class QuestionnaireApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionnaireApplication.class, args);
	}

	@Bean 
	public CommandLineRunner demo(InquiryRepository iRepository,
		QuestionRepository qRepository,
		UserRepository uRepository,
		PasswordEncoder passwordEncoder){
			return (args) -> {
				if (uRepository.findByUsername("user").isEmpty()) {
					User user = new User();
					user.setRole("USER");
					user.setUsername("user");
					user.setHashPassword(passwordEncoder.encode("user"));
					uRepository.save(user);
				}

				Inquiry inquiry = new Inquiry();
				inquiry.setTitle("Ruokalan palaute kysely");
				inquiry.setDescription("Esimerkki kyselyn esimerkki kuvaus :)");
				inquiry.setCreateDate(LocalDate.now());
				inquiry.setDeadlineDate(LocalDate.of(2026, 12, 5));

				String questions[] = {
					"Kuinka usein syöt kampusravintolassa ja mihin aikaan?",
					"Mitä mieltä olit tänään valitsemastasi ruoasta (maku, lämpötila, annoskoko)?",
					"Löytyykö ruokalistalta riittävästi vaihtoehtoja sinulle (esim. kasvis-, vegaani- tai erityisruokavaliot)?",
					"Miten jonotus ja ruokailun sujuvuus toimivat ruuhka-aikaan?",
					"Mitä mieltä olet hinta-laatusuhteesta?",
					"Millaisia ruokia tai muutoksia toivoisit ruokalistalle?",
					"Muuta palautetta kampusravintolalle"
				};

				for (String text : questions){
					Question q = new Question();
					q.setQuestion(text);
					q.set_type("Text");
					inquiry.addQuestion(q);
				}

				iRepository.save(inquiry);
			};
		}
}
