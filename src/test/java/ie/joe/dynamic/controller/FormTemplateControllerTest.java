package ie.joe.dynamic.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ie.joe.dynamic.constants.ApiVersion;
import ie.joe.dynamic.dto.FormTemplateDTO;
import ie.joe.dynamic.model.FormTemplate;
import ie.joe.dynamic.service.FormTemplateService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers =  FormTemplateController.class)
@AutoConfigureMockMvc(addFilters = false)
class FormTemplateControllerTest {

  private final String baseUrl = "/api/" + ApiVersion.CURRENT + "/templates";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean
  private FormTemplateService formTemplateService;



  @Test
  void findAllTest() throws Exception {
    mockMvc.perform( MockMvcRequestBuilders
        .get(baseUrl + "/")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
  }


  @Test
  void findByIdNotFoundTest() {
    Exception exception = assertThrows(NestedServletException.class, () -> mockMvc.perform(get(baseUrl + "/1")));
    assertTrue(exception.getCause() instanceof NoSuchElementException, "Expected no such element exception to be thrown");
    assertTrue(exception.getCause().getMessage().startsWith("Unable to find Form Template with id: [1]"));
  }

  @Test
  void findByIdTest() throws Exception {
    when(formTemplateService.findById(2)).thenReturn(Optional.of(new FormTemplate()));
    ResultActions resultActions = mockMvc.perform( get(baseUrl + "/2")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void createTest() throws Exception {

    FormTemplateDTO formTemplateDTO = new FormTemplateDTO();
    formTemplateDTO.setTemplateId(1);

    when(modelMapper.map(null, FormTemplateDTO.class)).thenReturn(formTemplateDTO);

    mockMvc.perform( MockMvcRequestBuilders
        .post(baseUrl)
        .content("{}")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.templateId").exists());
  }

  @Test
  void createIdExistsTest() throws Exception {
    MvcResult result = mockMvc.perform( MockMvcRequestBuilders
        .post(baseUrl)
        .content("{\"templateId\": 42}")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    assertEquals(
        "You cannot create a template with an ID.  Template ID is an auto generated field.",
        content);
  }

  @Test
  void updateTest() throws Exception{
    FormTemplateDTO formTemplateDTO = new FormTemplateDTO();
    formTemplateDTO.setTemplateId(42);

    when(modelMapper.map(null, FormTemplateDTO.class)).thenReturn(formTemplateDTO);

    mockMvc.perform( MockMvcRequestBuilders
        .put(baseUrl + "/42")
        .content("{\"formId\": 42}")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.templateId").exists());
  }



  @Test
  void deleteTest() throws Exception {
    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/42"))
        .andExpect(status().isOk());
  }
}
