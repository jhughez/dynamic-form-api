package ie.joe.dynamic.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ie.joe.dynamic.constants.ApiVersion;
import ie.joe.dynamic.model.Question;
import ie.joe.dynamic.service.QuestionService;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers =  QuestionController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuestionControllerTest {

  private final String baseUrl = "/api/" + ApiVersion.CURRENT + "/questions";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean
  private QuestionService questionService;

  @Test
  void findAllTest() throws Exception {
    mockMvc.perform( MockMvcRequestBuilders
        .get(baseUrl + "/")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
  }

  @Test
  void findByIdTest() throws Exception {
    when(questionService.findById(2)).thenReturn(Optional.of(new Question()));
    ResultActions resultActions = mockMvc.perform( get(baseUrl + "/2")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void deleteTest() throws Exception {
    when(questionService.findById(1)).thenReturn(Optional.of(new Question()));
    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/1"))
        .andExpect(status().isOk());
  }

  @Test
  void deleteQuestionNotFoundTest() throws Exception {
    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/1"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deleteAllowableValuesTest() throws Exception {
    when(questionService.findById(1)).thenReturn(Optional.of(new Question()));
    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/1" + "/allowableValues/" + 1))
        .andExpect(status().isOk());
  }

  @Test
  void deleteAllowableValuesQuestionNotFoundTest() throws Exception {
    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/1" + "/allowableValues/" + 1))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deleteValidationRulesTest() throws Exception {
    when(questionService.findById(1)).thenReturn(Optional.of(new Question()));
    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/1" + "/validationRules/" + 1))
        .andExpect(status().isOk());
  }

  @Test
  void deleteValidationRulesQuestionNotFoundTest() throws Exception {
    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/1" + "/validationRules/" + 1))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deleteActionToPerquestionnaireTest() throws Exception {
    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/1" + "/actionToPerquestionnaire/" + 1))
        .andExpect(status().isOk());
  }
}
