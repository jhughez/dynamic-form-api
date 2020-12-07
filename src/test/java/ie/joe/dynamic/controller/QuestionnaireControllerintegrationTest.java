package ie.joe.dynamic.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.joe.dynamic.DynamicApplication;
import ie.joe.dynamic.constants.ApiVersion;
import ie.joe.dynamic.dto.QuestionnaireDTO;
import ie.joe.dynamic.dto.QuestionnaireTemplateDTO;
import ie.joe.dynamic.dto.GroupDTO;
import ie.joe.dynamic.dto.QuestionDTO;
import ie.joe.dynamic.dto.SectionDTO;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
    webEnvironment = WebEnvironment.MOCK,
    classes = DynamicApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class QuestionnaireControllerintegrationTest {

  private final String baseUrl = "/api/" + ApiVersion.CURRENT + "/questionnaires";

  private final String createJson = "{\n"
      + "    \"templateId\": 21,\n"
      + "    \"answers\": [\n"
      + "        {\n"
      + "            \"questionId\": 84,\n"
      + "            \"answerValue\": \"John Smith\"\n"
      + "        },\n"
      + "        {\n"
      + "            \"questionId\": 85,\n"
      + "            \"answerValue\": \"Some Stupid Stuff\"\n"
      + "        }        \n"
      + "    ]\n"
      + "}";

  private final String updateJson = "{\n"
      + "    \"templateId\": 21,\n"
      + "    \"answers\": [\n"
      + "        {\n"
      + "            \"questionId\": 84,\n"
      + "            \"answerValue\": \"This has been changed\"\n"
      + "        },\n"
      + "        {\n"
      + "            \"questionId\": 85,\n"
      + "            \"answerValue\": \"Some More Stupid Stuff\"\n"
      + "        }        \n"
      + "    ]\n"
      + "}";

  @Autowired
  private MockMvc mockMvc;

  @Test
  void findAllTest() throws Exception {
    MvcResult result = mockMvc.perform( get(baseUrl + "/")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

    String json = result.getResponse().getContentAsString();
    QuestionnaireDTO[] questionnaires = new ObjectMapper().readValue(json, QuestionnaireDTO[].class);

    assertTrue(questionnaires.length > 0);

    QuestionnaireTemplateDTO questionnaireTemplateDTO = questionnaires[1].getQuestionnaireTemplate();
    SectionDTO sectionDTO = questionnaireTemplateDTO.getSection().iterator().next();
    GroupDTO groupDTO = sectionDTO.getGroup().iterator().next();
    QuestionDTO questionDTO = groupDTO.getQuestion().iterator().next();

    assertEquals("inspection questionnaire", questionnaireTemplateDTO.getTemplateDesc());
    assertEquals("Identity Fields Section", sectionDTO.getSectionTitle());
    assertEquals(26, groupDTO.getGroupId());
    assertEquals(84, questionDTO.getQuestionId());
    assertEquals("TEXTBOX", questionDTO.getQuestionTypeName());
    assertEquals("bla", questionDTO.getAnswer());
  }

  @Test
  void findByIdNotFoundTest() {
    Exception exception = assertThrows(NestedServletException.class, () -> mockMvc.perform(get(baseUrl + "/1")));
    assertTrue(exception.getCause() instanceof NoSuchElementException, "Expected no such element exception to be thrown");
    assertEquals("Unable to find questionnaire with id: [1]", exception.getCause().getMessage());
  }

  @Test
  void createTest() throws Exception {
    MvcResult result =  mockMvc.perform( MockMvcRequestBuilders
          .post(baseUrl)
          .content(createJson)
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk()).andReturn();

    String json = result.getResponse().getContentAsString();
    QuestionnaireDTO questionnaireDTO = new ObjectMapper().readValue(json, QuestionnaireDTO.class);

    QuestionnaireTemplateDTO questionnaireTemplateDTO = questionnaireDTO.getQuestionnaireTemplate();
    GroupDTO groupDTO = questionnaireTemplateDTO.getSection().iterator().next().getGroup().iterator().next();
    List<QuestionDTO> questions = groupDTO.getQuestion()
        .stream()
        .filter(x -> x.getQuestionId() == 84 || x.getQuestionId() == 85)
        .sorted(Comparator.comparingLong(QuestionDTO::getQuestionId))
        .collect(Collectors.toList());

    assertEquals(21, questionnaireTemplateDTO.getTemplateId());
    assertEquals(84, questions.get(0).getQuestionId());
    assertEquals("John Smith", questions.get(0).getAnswer());
    assertEquals(85, questions.get(1).getQuestionId());
    assertEquals("Some Stupid Stuff", questions.get(1).getAnswer());
  }

  @Test
  void createIdExistsTest() throws Exception {
    MvcResult result = mockMvc.perform( MockMvcRequestBuilders
        .post(baseUrl)
        .content("{\"questionnaireId\": 42}")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andReturn();

    String content = result.getResponse().getErrorMessage();
    assertEquals("You cannot create a questionnaire with an ID.  Questionnaire ID is an auto generated field.",
        content);
  }

  @Test
  void updateTest() throws Exception{

    MvcResult result = mockMvc.perform( MockMvcRequestBuilders
        .put(baseUrl + "/42")
            .content(updateJson)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();

    String json = result.getResponse().getContentAsString();
    QuestionnaireDTO questionnaireDTO = new ObjectMapper().readValue(json, QuestionnaireDTO.class);

    QuestionnaireTemplateDTO questionnaireTemplateDTO = questionnaireDTO.getQuestionnaireTemplate();
    GroupDTO groupDTO = questionnaireTemplateDTO.getSection().iterator().next().getGroup().iterator().next();
    List<QuestionDTO> questions = groupDTO.getQuestion()
        .stream()
        .filter(x -> x.getQuestionId() == 84 || x.getQuestionId() == 85)
        .sorted(Comparator.comparingLong(QuestionDTO::getQuestionId))
        .collect(Collectors.toList());

    assertEquals(21, questionnaireTemplateDTO.getTemplateId());
    assertEquals(84, questions.get(0).getQuestionId());
    assertEquals("This has been changed", questions.get(0).getAnswer());
    assertEquals(85, questions.get(1).getQuestionId());
    assertEquals("Some More Stupid Stuff", questions.get(1).getAnswer());
  }

  @Test
  void deleteTest() throws Exception {

    mockMvc.perform( get(baseUrl + "/21")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/21"))
        .andExpect(status().isOk());

    Exception exception = assertThrows(NestedServletException.class, () -> mockMvc.perform(get(baseUrl + "/21")));
    assertTrue(exception.getCause() instanceof NoSuchElementException, "Expected no such element exception to be thrown");
    assertEquals("Unable to find questionnaire with id: [21]", exception.getCause().getMessage());

  }

}
