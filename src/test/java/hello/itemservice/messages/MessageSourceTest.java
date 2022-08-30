package hello.itemservice.messages;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired MessageSource ms;

    @Test
    void helloMessage() {
        //given
        String resultKo = ms.getMessage("hello", null, null);
        String resultEn = ms.getMessage("hello", null, Locale.ENGLISH);

        //when

        //then
        assertThat(resultKo).isEqualTo("안녕");
        assertThat(resultEn).isEqualTo("hello");
    }

    @Test
    void notFoundMessageDefault() {
        //given
        String resultNoMsg = ms.getMessage("no_code", null, "기본 메시지", null);

        //when
        //then
        assertThat(resultNoMsg).isEqualTo("기본 메시지");
    }

    @Test
    void notFoundMessage() {
        //given
        //when
        //then
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null)).isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void argumentMessage() {
        //given
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);

        //when
        //then
        assertThat(result).isEqualTo("안녕 Spring");
    }
}
