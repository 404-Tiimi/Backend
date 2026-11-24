package com._4.questionnaire.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com._4.questionnaire.domain.AnswerRepository;
import com._4.questionnaire.domain.Inquiry;
import com._4.questionnaire.domain.InquiryRepository;
import com._4.questionnaire.domain.Question;
import com._4.questionnaire.domain.QuestionRepository;
import com._4.questionnaire.domain.UserRepository;

class QuestionControllerTest {

    @Test
    void saveUpdatesExistingQuestionsAndAddsOnlyNewQuestions() {
        Inquiry inquiry = new Inquiry();
        Question first = question("Original first");
        Question second = question("Unchanged second");
        inquiry.addQuestion(first);
        inquiry.addQuestion(second);

        InquiryRepository inquiryRepository = mock(InquiryRepository.class);
        when(inquiryRepository.findById(1)).thenReturn(Optional.of(inquiry));
        QuestionController controller = new QuestionController(
                mock(AnswerRepository.class),
                inquiryRepository,
                mock(QuestionRepository.class),
                mock(UserRepository.class));

        controller.save(1, "Updated title",
                new String[] {"Updated first", " Unchanged second ", "New third"},
                new String[] {"TEXT", "TEXT", "TEXT"},
                new String[] {"", "", ""});

        assertSame(first, inquiry.getQuestions().get(0));
        assertSame(second, inquiry.getQuestions().get(1));
        assertEquals("Updated first", first.getQuest());
        assertEquals("Unchanged second", second.getQuest());
        assertEquals(3, inquiry.getQuestions().size());
        assertEquals("New third", inquiry.getQuestions().get(2).getQuest());
        verify(inquiryRepository).save(inquiry);
    }

    @Test
    void saveRemovesOnlyQuestionsNoLongerSubmitted() {
        Inquiry inquiry = new Inquiry();
        Question retained = question("Keep this");
        Question removed = question("Remove this");
        inquiry.addQuestion(retained);
        inquiry.addQuestion(removed);

        InquiryRepository inquiryRepository = mock(InquiryRepository.class);
        when(inquiryRepository.findById(1)).thenReturn(Optional.of(inquiry));
        QuestionController controller = new QuestionController(
                mock(AnswerRepository.class),
                inquiryRepository,
                mock(QuestionRepository.class),
                mock(UserRepository.class));

        controller.save(1, "Title",
                new String[] {"Keep this"}, new String[] {"TEXT"}, new String[] {""});

        assertEquals(1, inquiry.getQuestions().size());
        assertSame(retained, inquiry.getQuestions().get(0));
        assertNull(removed.getInquiry());
        verify(inquiryRepository).save(inquiry);
    }

    @Test
    void saveStoresRadioTypeAndOptionsAndClearsThemForText() {
        Inquiry inquiry = new Inquiry();
        Question first = question("Was it good?");
        first.setType("RADIO");
        first.getOptions().addAll(List.of("Old option"));
        inquiry.addQuestion(first);

        InquiryRepository inquiryRepository = mock(InquiryRepository.class);
        when(inquiryRepository.findById(1)).thenReturn(Optional.of(inquiry));
        QuestionController controller = new QuestionController(
                mock(AnswerRepository.class),
                inquiryRepository,
                mock(QuestionRepository.class),
                mock(UserRepository.class));

        controller.save(1, "Title",
                new String[] {"Was it good?", "", "Pick one"},
                new String[] {"TEXT", "RADIO", "RADIO"},
                new String[] {"Old option", "ignored", "Kyllä, ehdottomasti\r\n\r\n Ei \n"});

        assertEquals(2, inquiry.getQuestions().size());
        assertEquals("TEXT", first.getType());
        assertEquals(List.of(), first.getOptions());
        Question radio = inquiry.getQuestions().get(1);
        assertEquals("RADIO", radio.getType());
        assertEquals(List.of("Kyllä, ehdottomasti", "Ei"), radio.getOptions());
    }

    @Test
    void addCreatesMixedQuestions() {
        InquiryRepository inquiryRepository = mock(InquiryRepository.class);
        QuestionController controller = new QuestionController(
                mock(AnswerRepository.class),
                inquiryRepository,
                mock(QuestionRepository.class),
                mock(UserRepository.class));

        controller.add("Title",
                new String[] {"Open", "Choice"},
                new String[] {"TEXT", "RADIO"},
                new String[] {"", "A\nB"});

        ArgumentCaptor<Inquiry> captor = ArgumentCaptor.forClass(Inquiry.class);
        verify(inquiryRepository).save(captor.capture());
        List<Question> saved = captor.getValue().getQuestions();
        assertEquals("TEXT", saved.get(0).getType());
        assertEquals("RADIO", saved.get(1).getType());
        assertEquals(List.of("A", "B"), saved.get(1).getOptions());
    }

    @Test
    void legacyQuestionWithoutTypeIsText() {
        assertEquals("TEXT", new Question().getType());
    }

    private Question question(String text) {
        Question question = new Question();
        question.setQuestion(text);
        return question;
    }
}
