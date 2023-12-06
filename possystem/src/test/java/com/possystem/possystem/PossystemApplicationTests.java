package com.possystem.possystem;

import com.possystem.possystem.service.implementation.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PossystemApplicationTests {

    ProductServiceImpl productService = mock(ProductServiceImpl.class);

    @Before
    public void setUp() throws Exception {
        System.out.println("Inside setup method");
    }


    @Test
    public void testPrintMessageMethod() {
        when(productService.getAll()).thenReturn(null);
        Assert.assertNotNull(productService.getAll());
    }
}
