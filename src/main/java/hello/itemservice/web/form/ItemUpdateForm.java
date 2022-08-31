package hello.itemservice.web.form;

import hello.itemservice.domain.item.check.UpdateCheck;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ItemUpdateForm {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    private Integer quantity;
}
