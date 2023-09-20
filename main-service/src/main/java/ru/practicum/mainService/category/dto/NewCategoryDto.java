package ru.practicum.mainService.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {

    @NotBlank(message = "Имя категории должно быть заполнено.")
    @Size(min = 1, max = 50, message = "Длина имени катогории должна быть от 1 до 50 символов.")
    private String name;

}