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
import ie.joe.dynamic.dto.FormDTO;
import ie.joe.dynamic.model.Form;
import ie.joe.dynamic.service.FormService;
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
import org.springframework.web.util.NestedServletException;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FormController.class)
@AutoConfigureMockMvc(addFilters = false)
class FormControllerTest {

  private final String baseUrl = "/api/" + ApiVersion.CURRENT + "/forms";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean
  private FormService formService;

  @Test
  void findAllTest() throws Exception {
    mockMvc.perform( MockMvcRequestBuilders
        .get(baseUrl + "/")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void findByIdNotFoundTest() {
    Exception exception = assertThrows(NestedServletException.class, () -> mockMvc.perform(get(baseUrl + "/1")));
    assertTrue(exception.getCause() instanceof NoSuchElementException, "Expected no such element exception to be thrown");
    assertEquals("Unable to find form with id: [1]", exception.getCause().getMessage());
  }

  @Test
  void findByIdTest() throws Exception {
    when(formService.findById(2)).thenReturn(Optional.of(new Form()));
    ResultActions resultActions = mockMvc.perform( get(baseUrl + "/2")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findByInspIdNotFoundTest() {
    Exception exception = assertThrows(NestedServletException.class, () ->
      mockMvc.perform(get(baseUrl + "/byInsp/1"))
    );
    assertTrue(exception.getCause() instanceof NoSuchElementException, "Expected no such element exception to be thrown");
    assertEquals("Unable to find Form with inspId: [1]", exception.getCause().getMessage());
  }

  @Test
  void findByInspIdTest() throws Exception {
    when(formService.findByInspId(2)).thenReturn(java.util.Optional.of(new Form()));
    mockMvc.perform( get(baseUrl + "/byInsp/2")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void createTest() throws Exception {

    FormDTO formDTO = new FormDTO();
    formDTO.setFormId(1);

    when(modelMapper.map(null, FormDTO.class)).thenReturn(formDTO);

    mockMvc.perform( MockMvcRequestBuilders
          .post(baseUrl)
          .content("{}")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.formId").exists());
  }

  @Test
  void createIdExistsTest() throws Exception {
    MvcResult result = mockMvc.perform( MockMvcRequestBuilders
        .post(baseUrl)
        .content("{\"formId\": 42}")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    assertEquals("You cannot create a form with an ID.  Form ID is an auto generated field.",
        content);
  }

  @Test
  void updateTest() throws Exception{
    FormDTO formDTO = new FormDTO();
    formDTO.setFormId(42);

    when(modelMapper.map(null, FormDTO.class)).thenReturn(formDTO);

    mockMvc.perform( MockMvcRequestBuilders
        .put(baseUrl + "/42")
        .content("{\"formId\": 42}")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.formId").exists());
  }

  @Test
  void deleteTest() throws Exception {
    mockMvc.perform( MockMvcRequestBuilders
        .delete(baseUrl + "/42"))
        .andExpect(status().isOk());
  }

}
