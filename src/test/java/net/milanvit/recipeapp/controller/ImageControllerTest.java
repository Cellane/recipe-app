package net.milanvit.recipeapp.controller;

import net.milanvit.recipeapp.command.RecipeCommand;
import net.milanvit.recipeapp.service.ImageService;
import net.milanvit.recipeapp.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {
    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController controller;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getImageForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        command.setId("1");

        when(recipeService.findCommandById(anyString())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/image"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findCommandById(anyString());
    }

    @Test
    public void handleImage() throws Exception {
        MockMultipartFile multipartFile =
            new MockMultipartFile("imageFile", "test.txt", "text/plain", "Spring Framework".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", "/recipe/1/show"));
        verify(imageService).saveImageFile(anyString(), any());
    }

    @Test
    public void renderImageFromDB() throws Exception {
        RecipeCommand command = new RecipeCommand();
        String s = "fake image text";
        Byte[] bytes = new Byte[s.getBytes().length];
        int i = 0;

        for (byte primitiveByte : s.getBytes()) {
            bytes[i++] = primitiveByte;
        }

        command.setId("1");
        command.setImage(bytes);

        when(recipeService.findCommandById(anyString())).thenReturn(command);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipe-image"))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, responseBytes.length);
    }
}
