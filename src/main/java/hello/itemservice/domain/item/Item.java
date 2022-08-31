package hello.itemservice.domain.item;

import hello.itemservice.domain.item.check.SaveCheck;
import hello.itemservice.domain.item.check.UpdateCheck;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter @Setter
@NoArgsConstructor
//@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity > 9999")
public class Item {

    @NotNull(groups = UpdateCheck.class) // 수정 요구 사항 추가
    private Long id;

    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String name;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value = 9999, groups = SaveCheck.class)
    private Integer quantity;

    public Item(String name, Integer price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}