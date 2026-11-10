package com._4.questionnaire.web.dto;

import java.util.List;

public record SaveAnswersRequest(Integer sessionId,List<AnswerRequest> answers) {

}
