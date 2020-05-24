package ie.joe.dynamic.converter.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import ie.joe.dynamic.dto.AllowableValueDTO;
import ie.joe.dynamic.dto.AnswerDTO;
import ie.joe.dynamic.dto.QuestionnaireDTO;
import ie.joe.dynamic.dto.QuestionnaireTemplateDTO;
import ie.joe.dynamic.dto.QuestionnaireUserResponseDTO;
import ie.joe.dynamic.dto.GroupDTO;
import ie.joe.dynamic.dto.QuestionDTO;
import ie.joe.dynamic.dto.SectionDTO;
import ie.joe.dynamic.dto.ValidationRuleDTO;
import ie.joe.dynamic.model.AllowableValue;
import ie.joe.dynamic.model.Answer;
import ie.joe.dynamic.model.Answer.AnswerPK;
import ie.joe.dynamic.model.Questionnaire;
import ie.joe.dynamic.model.QuestionnaireTemplate;
import ie.joe.dynamic.model.QuestionnaireGroup;
import ie.joe.dynamic.model.GroupType;
import ie.joe.dynamic.model.Question;
import ie.joe.dynamic.model.Section;
import ie.joe.dynamic.model.ValidationLink;
import ie.joe.dynamic.model.ValidationRule;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ModelMapperTest {

  private ModelMapper modelMapper;


  ModelMapperTest() {
    modelMapper = new ModelMapper();
    modelMapper.addConverter(new QuestionToIdConverter());
    modelMapper.addConverter(new LongIdToQuestion());
    modelMapper.addConverter(new AllowableValueDTOToAllowableValue());
    modelMapper.addConverter(new ValidationRuleDTOToValidationLink());
    modelMapper.addConverter(new ValidationLinkToValidationRuleDTO());
    modelMapper.addConverter(new QuestionnaireToQuestionnaireDTO(modelMapper));
    modelMapper.addConverter(new QuestionnaireUserResponseDTOToQuestionnaire());
  }

  @Test
  void allowableValueDTOToAllowableValueTest() {
    AllowableValueDTO allowableValueDTO = new AllowableValueDTO();
    allowableValueDTO.setValue("Sheep");
    allowableValueDTO.setOptionName("Sheep");

    AllowableValue allowableValue = modelMapper.map(allowableValueDTO, AllowableValue.class);

    assertNull(allowableValue.getQuestion(), "Question should not be set");
    assertEquals(allowableValue.getValue()
        , allowableValueDTO.getValue()
        , "Entity value should equal DTO value");
    assertEquals(allowableValue.getOptionName()
        , allowableValueDTO.getOptionName()
        , "Entity option name  should equal DTO option name ");
  }

  @Test
  void LongIdToQuestionTest() {
    long id = 1;
    Question question = modelMapper.map(id, Question.class);

    assertEquals(question.getQuestionId(), id, "Entity question ID should equal ID");
  }

  @Test
  void QuestionToIdConverterTest() {
    Question question = createQuestion();

    long id = modelMapper.map(question, Long.class);

    assertEquals( id, question.getQuestionId(), "Entity ID should equal question ID");
  }

  @Test
  void validationLinkToValidationRuleDTOTest() {
    ValidationLink validationLink = new ValidationLink();
    ValidationRule validationRule = new ValidationRule();
    validationRule.setRuleId(1);
    validationRule.setRuleName("test name");
    validationRule.setRuleMessage("test message");

    validationLink.setValidationRule(validationRule);
    validationLink.setRuleValue("Test Rule Value");

    ValidationRuleDTO validationRuleDTO = modelMapper.map(validationLink, ValidationRuleDTO.class);

    assertEquals(validationRuleDTO.getRuleId(),
        validationLink.getValidationRule().getRuleId(),
        "Entity rule ID should equal DTO rule ID");

    assertEquals(validationRuleDTO.getRuleName(),
        validationLink.getValidationRule().getRuleName(),
        "Entity rule Name should equal DTO rule Name");

    assertEquals(validationRuleDTO.getRuleValue(), validationLink.getRuleValue(),
        "Entity rule Value should equal DTO rule Value");

    assertEquals(validationRuleDTO.getRuleMessage(),
        validationLink.getValidationRule().getRuleMessage(),
        "Entity rule Name should equal DTO rule Name");
  }

  @Test
  void validationRuleDTOToValidationLinkTest() {
    ValidationRuleDTO validationRuleDTO = new ValidationRuleDTO();
    validationRuleDTO.setRuleId(1);
    validationRuleDTO.setRuleName("test name");
    validationRuleDTO.setRuleValue("Test Rule Value");
    validationRuleDTO.setRuleMessage("test message");


    ValidationLink validationLink = modelMapper.map(validationRuleDTO, ValidationLink.class);

    assertEquals(validationRuleDTO.getRuleId(),
        validationLink.getValidationRule().getRuleId(),
        "Entity rule ID should equal DTO rule ID");

    assertEquals(validationRuleDTO.getRuleName(),
        validationLink.getValidationRule().getRuleName(),
        "Entity rule Name should equal DTO rule Name");

    assertEquals(validationRuleDTO.getRuleValue(), validationLink.getRuleValue(),
        "Entity rule Value should equal DTO rule Value");

    assertEquals(validationRuleDTO.getRuleMessage(),
        validationLink.getValidationRule().getRuleMessage(),
        "Entity rule Name should equal DTO rule Name");
  }

  @Test
  void questionnaireUserResponseDTOToQuestionnaireTest() {
    QuestionnaireUserResponseDTO questionnaireUserResponseDTO = new QuestionnaireUserResponseDTO();
    AnswerDTO answerDTO = new AnswerDTO();
    answerDTO.setQuestionId(1);
    answerDTO.setAnswerValue("answer");

    questionnaireUserResponseDTO.setQuestionnaireId(1);
    questionnaireUserResponseDTO.setTemplateId(1);

    questionnaireUserResponseDTO.setAnswers(Collections.singletonList(answerDTO));

    Questionnaire questionnaire = modelMapper.map(questionnaireUserResponseDTO, Questionnaire.class);

    assertEquals(questionnaire.getQuestionnaireId(), questionnaireUserResponseDTO.getQuestionnaireId(),
        "Entity questionnaire ID should equal DTO questionnaire ID");
    assertEquals(questionnaire.getQuestionnaireTemplate().getTemplateId(), questionnaireUserResponseDTO.getTemplateId(),
        "template questionnaire ID should equal DTO template ID");
    assertEquals(1, questionnaire.getAnswers().size(), "Questionnaire entity should only contain one answer");

    Answer answer = questionnaire.getAnswers().iterator().next();
    assertEquals(answer.getAnswerValue(), answerDTO.getAnswerValue(),
        "Entity answer value should equal DTO Answer Value");
    assertEquals(answer.getAnswerPK().getQuestionId(), answerDTO.getQuestionId(),
        "Entity answer question ID should equal DTO Answer question ID");
  }

  @Test
  void questionnaireToQuestionnaireDTOTest() {
    Questionnaire questionnaire = createQuestionnaire();
    QuestionnaireDTO questionnaireDTO = modelMapper.map(questionnaire, QuestionnaireDTO.class);

    //Questionnaire
    assertEquals(questionnaire.getQuestionnaireId(), questionnaireDTO.getQuestionnaireId(), "Entity Questionnaire ID should equal DTO Questionnaire iD");

    //Questionnaire Template
    QuestionnaireTemplateDTO questionnaireTemplateDTO = questionnaireDTO.getQuestionnaireTemplate();
    assertEquals(questionnaire.getQuestionnaireTemplate().getTemplateId(), questionnaireTemplateDTO.getTemplateId(),
        "Entity template ID should equal DTO template ID");
    assertEquals(questionnaire.getQuestionnaireTemplate().getTemplateTitle(), questionnaireTemplateDTO.getTemplateTitle(),
        "Entity template title should equal DTO template title");
    assertEquals(questionnaire.getQuestionnaireTemplate().getTemplateDesc(), questionnaireTemplateDTO.getTemplateDesc(),
        "Entity template Desc should equal DTO template Desc");


    //Section
    assertEquals(1, questionnaireTemplateDTO.getSection().size(),
        "There should be only one section in the test data");
    SectionDTO sectionDTO = questionnaireTemplateDTO.getSection().iterator().next();
    Section section = createSection();
    assertEquals(section.getSectionId(), sectionDTO.getSectionId(),
        "Entity Section ID should equal DTO Section ID");
    assertEquals(section.getSectionTitle(), sectionDTO.getSectionTitle(),
        "Entity Section Title should equal DTO Section Title");
    assertEquals(section.getOrder(), sectionDTO.getOrder(),
        "Entity Section Order should equal DTO Section Order");

    //Group
    assertEquals(1, sectionDTO.getGroup().size(),
        "There should be only one group in the test data");
    GroupDTO groupDTO = sectionDTO.getGroup().iterator().next();
    QuestionnaireGroup group = createGroup();
    assertSame(group.getGroupId(), groupDTO.getGroupId(),
        "Entity group ID should equal DTO group ID");
    assertEquals(group.getGroupTitle(), groupDTO.getGroupTitle(),
        "Entity group Title should equal DTO group Title");
    assertSame(group.getOrder(), groupDTO.getOrder(),
        "Entity group order should equal DTO group order");
    assertEquals(group.getGroupType(), groupDTO.getGroupType(),
        "Entity group Title type equal DTO group type");

    //Question
    assertEquals(1, groupDTO.getQuestion().size(),
        "There should be only one question in the test data");
    QuestionDTO questionDTO = groupDTO.getQuestion().iterator().next();
    Question question = createQuestion();
    assertEquals(question.getQuestionId(), questionDTO.getQuestionId(),
        "Entity question ID should equal DTO question ID");

    Answer answer = createAnswer();
    assertEquals(answer.getAnswerValue(), questionDTO.getAnswer(),
        "Entity answer should equal DTO question answer");
  }

  private Questionnaire createQuestionnaire() {
    Questionnaire questionnaire = new Questionnaire();
    questionnaire.setQuestionnaireId(1);
    questionnaire.setQuestionnaireId(1);
    questionnaire.setQuestionnaireTemplate(createQuestionnaireTemplate());
    questionnaire.setAnswers(Collections.singletonList(createAnswer()));
    return questionnaire;
  }

  private Answer createAnswer() {
    AnswerPK answerPK = new AnswerPK();
    answerPK.setQuestionId(1);

    Answer answer = new Answer();
    answer.setAnswerValue("test");
    answer.setAnswerPK(answerPK);
    return answer;
  }


  private QuestionnaireTemplate createQuestionnaireTemplate() {
    QuestionnaireTemplate questionnaireTemplate = new QuestionnaireTemplate();
    questionnaireTemplate.setTemplateId(1);
    questionnaireTemplate.setTemplateTitle("Template Title");
    questionnaireTemplate.setTemplateDesc("Template Desc");
    questionnaireTemplate.setSection(Collections.singletonList(createSection()));
    return questionnaireTemplate;
  }

  private Section createSection() {
    Section section = new Section();
    section.setSectionId(1);
    section.setSectionTitle("Section Title");
    section.setOrder(1);
    section.setGroup(Collections.singletonList(createGroup()));
    return section;
  }

  private QuestionnaireGroup createGroup() {
    QuestionnaireGroup group = new QuestionnaireGroup();
    group.setGroupId(1L);
    group.setGroupTitle("Group Title");
    group.setOrder(1L);
    group.setGroupType(createGroupType());
    group.setQuestion(Collections.singletonList(createQuestion()));
    return group;
  }

  private GroupType createGroupType() {
    GroupType groupType = new GroupType();
    groupType.setGroupTypeId(1);
    groupType.setGroupTypeName("Group Type Name");
    groupType.setGroupTypeDesc("Group Type Desc");
    return groupType;
  }

  private Question createQuestion() {
    long id = 1;
    Question question = new Question();
    question.setQuestionId(id);
    return question;
  }
}
