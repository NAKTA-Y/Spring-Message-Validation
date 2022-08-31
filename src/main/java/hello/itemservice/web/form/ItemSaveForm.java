package hello.itemservice.web.form;

import hello.itemservice.domain.item.check.SaveCheck;
import hello.itemservice.domain.item.check.UpdateCheck;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ItemSaveForm {
    @NotBlank
    private String name;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer quantity;
}
