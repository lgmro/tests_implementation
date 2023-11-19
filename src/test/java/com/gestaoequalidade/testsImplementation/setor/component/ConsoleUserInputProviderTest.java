package com.gestaoequalidade.testsImplementation.setor.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConsoleUserInputProviderTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @InjectMocks
    private ConsoleUserInputProvider consoleUserInputProvider;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void dado_um_texto_quando_chamar_set_user_output_deve_imprimir_o_texto() {
        // Arrange
        String textTest = "text";

        // Act
        consoleUserInputProvider.setUserOutput(textTest);

        // Assert
        assertEquals(textTest + "\n", outContent.toString());
    }
}
