package ru.practicum.mainService.category.service;

import ru.practicum.mainService.category.dto.CategoryDto;
import ru.practicum.mainService.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto create(NewCategoryDto newCategoryDto);

    CategoryDto getById(Long catId);

    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto update(NewCategoryDto newCategoryDto, Long catId);

    void delete(Long catId);

}
