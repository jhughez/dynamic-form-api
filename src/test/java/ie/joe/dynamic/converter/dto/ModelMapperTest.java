package ie.joe.dynamic.converter.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import ie.joe.dynamic.dto.AllowableValueDTO;
import ie.joe.dynamic.dto.AnswerDTO;
import ie.joe.dynamic.dto.FormDTO;
import ie.joe.dynamic.dto.FormTemplateDTO;
import ie.joe.dynamic.dto.FormUserResponseDTO;
import ie.joe.dynamic.dto.GroupDTO;
import ie.joe.dynamic.dto.QuestionDTO;
import ie.joe.dynamic.dto.SectionDTO;
import ie.joe.dynamic.dto.ValidationRuleDTO;
import ie.joe.dynamic.model.AllowableValue;
import ie.joe.dynamic.model.Answer;
import ie.joe.dynamic.model.Answer.AnswerPK;
import ie.joe.dynamic.model.Form;
import ie.joe.dynamic.model.FormTemplate;
import ie.joe.dynamic.model.Group;
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
    modelMapper.addConverter(new FormToFormDTO(modelMapper));
    modelMapper.addConverter(new FormUserResponseDTOToForm());
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
  void formUserResponseDTOToFormTest() {
    FormUserResponseDTO formUserResponseDTO = new FormUserResponseDTO();
    AnswerDTO answerDTO = new AnswerDTO();
    answerDTO.setQuestionId(1);
    answerDTO.setAnswerValue("answer");

    formUserResponseDTO.setFormId(1);
    formUserResponseDTO.setInspId(1);
    formUserResponseDTO.setTemplateId(1);

    formUserResponseDTO.setAnswers(Collections.singletonList(answerDTO));

    Form form = modelMapper.map(formUserResponseDTO, Form.class);

    assertEquals(form.getFormId(), formUserResponseDTO.getFormId(),
        "Entity form ID should equal DTO form ID");
    assertEquals(form.getInspId(), formUserResponseDTO.getInspId(),
        "Entity inspection ID should equal DTO inspection ID");
    assertEquals(form.getFormTemplate().getTemplateId(), formUserResponseDTO.getTemplateId(),
        "template form ID should equal DTO template ID");
    assertEquals(1, form.getAnswers().size(), "Form entity should only contain one answer");

    Answer answer = form.getAnswers().iterator().next();
    assertEquals(answer.getAnswerValue(), answerDTO.getAnswerValue(),
        "Entity answer value should equal DTO Answer Value");
    assertEquals(answer.getAnswerPK().getQuestionId(), answerDTO.getQuestionId(),
        "Entity answer question ID should equal DTO Answer question ID");
  }

  @Test
  void formToFormDTOTest() {
    Form form = createForm();
    FormDTO formDTO = modelMapper.map(form, FormDTO.class);

    //Form
    assertEquals(form.getFormId(), formDTO.getFormId(), "Entity Form ID should equal DTO Form iD");

    assertEquals(form.getInspId(), formDTO.getInspId(), "Entity insp ID should equal DTO insp ID");

    //Form Template
    FormTemplateDTO formTemplateDTO = formDTO.getFormTemplate();
    assertEquals(form.getFormTemplate().getTemplateId(), formTemplateDTO.getTemplateId(),
        "Entity template ID should equal DTO template ID");
    assertEquals(form.getFormTemplate().getTemplateTitle(), formTemplateDTO.getTemplateTitle(),
        "Entity template title should equal DTO template title");
    assertEquals(form.getFormTemplate().getTemplateDesc(), formTemplateDTO.getTemplateDesc(),
        "Entity template Desc should equal DTO template Desc");
    assertEquals(form.getFormTemplate().getResultFormId(), formTemplateDTO.getResultFormId(),
        "Entity template ResultForm ID should equal DTO template ResultForm ID");


    //Section
    assertEquals(1, formTemplateDTO.getSection().size(),
        "There should be only one section in the test data");
    SectionDTO sectionDTO = formTemplateDTO.getSection().iterator().next();
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
    Group group = createGroup();
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

  private Form createForm() {
    Form form = new Form();
    form.setFormId(1);
    form.setInspId(1);
    form.setFormTemplate(createFormTemplate());
    form.setAnswers(Collections.singletonList(createAnswer()));
    return form;
  }

  private Answer createAnswer() {
    AnswerPK answerPK = new AnswerPK();
    answerPK.setQuestionId(1);

    Answer answer = new Answer();
    answer.setAnswerValue("test");
    answer.setAnswerPK(answerPK);
    return answer;
  }


  private FormTemplate createFormTemplate() {
    FormTemplate formTemplate = new FormTemplate();
    formTemplate.setTemplateId(1);
    formTemplate.setTemplateTitle("Template Title");
    formTemplate.setTemplateDesc("Template Desc");
    formTemplate.setResultFormId("1");
    formTemplate.setSection(Collections.singletonList(createSection()));
    return formTemplate;
  }

  private Section createSection() {
    Section section = new Section();
    section.setSectionId(1);
    section.setSectionTitle("Section Title");
    section.setOrder(1);
    section.setGroup(Collections.singletonList(createGroup()));
    return section;
  }

  private Group createGroup() {
    Group group = new Group();
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
