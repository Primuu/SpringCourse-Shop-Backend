package pl.nullpointerexception.shop.admin.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UploadedFilesNameUtilsTest {

    @ParameterizedTest
    @CsvSource({
            //given
            "test test.png, test-test.png",
            "hello world.png, hello-world.png",
            "ąęśćżźńłó.jpeg, aesczznlo.jpeg",
            "Produkt 1.png, produkt-1.png",
            "Produkt - 1.png, produkt-1.png",
            "Produkt   1.png, produkt-1.png",
            "Produkt__1.png, produkt-1.png"
    })
    void shouldSlugifyFileName(String in, String out) {
        //when
        String fileName = UploadedFilesNameUtils.slugifyFileName(in);
        //then
        assertEquals(fileName, out);
    }

}