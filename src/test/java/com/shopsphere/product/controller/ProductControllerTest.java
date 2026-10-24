
package com.shopsphere.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;
import com.shopsphere.product.dto.ProductRequest;
import com.shopsphere.product.dto.ProductResponse;
import com.shopsphere.product.service.ProductService;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    void createProduct_shouldReturnCreatedResponse() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Keyboard");
        request.setDescription("Wireless keyboard");
        request.setPrice(new BigDecimal("1499.00"));

        ProductResponse response = new ProductResponse(
                1L,
                "Keyboard",
                "Wireless keyboard",
                new BigDecimal("1499.00"),
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 10, 0));

        when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Keyboard"))
                .andExpect(jsonPath("$.price").value(1499.00));
    }
    

@Test
void createProduct_shouldReturnBadRequestWhenNameIsBlank() throws Exception {
    String invalidRequest = """
            {
                "name": "",
                "description": "Wireless keyboard",
                "price": 1499.00
            }
            """;

    mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidRequest))
            .andExpect(status().isBadRequest());

    verifyNoInteractions(productService);
}

@Test
void getAllProducts_shouldReturnProductList() throws Exception {
    ProductResponse product = new ProductResponse(
            1L,
            "Keyboard",
            "Wireless keyboard",
            new BigDecimal("1499.00"),
            LocalDateTime.of(2026, 1, 1, 10, 0),
            LocalDateTime.of(2026, 1, 1, 10, 0));

    when(productService.getAllProducts()).thenReturn(List.of(product));

    mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Keyboard"))
            .andExpect(jsonPath("$").isArray());
}

@Test
void getProductById_shouldReturnProduct() throws Exception {
    ProductResponse product = new ProductResponse(
            1L,
            "Keyboard",
            "Wireless keyboard",
            new BigDecimal("1499.00"),
            LocalDateTime.of(2026, 1, 1, 10, 0),
            LocalDateTime.of(2026, 1, 1, 10, 0));

    when(productService.getProductById(1L)).thenReturn(product);

    mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Keyboard"))
            .andExpect(jsonPath("$.price").value(1499.00));
}


@Test
void updateProduct_shouldReturnUpdatedProduct() throws Exception {
    ProductRequest request = new ProductRequest();
    request.setName("Gaming Keyboard");
    request.setDescription("Updated keyboard");
    request.setPrice(new BigDecimal("1999.00"));

    ProductResponse response = new ProductResponse(
            1L,
            "Gaming Keyboard",
            "Updated keyboard",
            new BigDecimal("1999.00"),
            LocalDateTime.of(2026, 1, 1, 10, 0),
            LocalDateTime.of(2026, 1, 2, 10, 0));

    when(productService.updateProduct(
            org.mockito.ArgumentMatchers.eq(1L),
            any(ProductRequest.class))).thenReturn(response);

    mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
            .put("/api/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Gaming Keyboard"))
            .andExpect(jsonPath("$.price").value(1999.00));
}

@Test
void deleteProduct_shouldReturnNoContent() throws Exception {
    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                    .delete("/api/products/1"))
            .andExpect(status().isNoContent());

    org.mockito.Mockito.verify(productService).deleteProduct(1L);
}


}
