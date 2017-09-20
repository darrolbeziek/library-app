package com.library.app.category.services.impl;

import com.library.app.category.exception.CategoryExistentException;
import com.library.app.category.model.Category;
import com.library.app.category.repository.CategoryRepository;
import com.library.app.common.exception.FieldNotValidException;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static com.library.app.commontests.category.CategoryForTestsRepository.categoryWithId;
import static com.library.app.commontests.category.CategoryForTestsRepository.java;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServicesUTest {

    private CategoryServices categoryServices;

    private Validator validator;

    private CategoryRepository categoryRepository;

    @Before
    public void initTestCase() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        categoryRepository = mock(CategoryRepository.class);


        categoryServices = new CategoryServicesImpl();
        ((CategoryServicesImpl)categoryServices).validator = validator;
        ((CategoryServicesImpl)categoryServices).categoryRepository = categoryRepository;


    }

    @Test
    public void addCategoryWithNullName() {
        try{
            categoryServices.add(new Category());
            fail("An error should not have been thrown");
        }
        catch (FieldNotValidException e) {
            assertThat(e.getFieldName(), is(equalTo("name")));
        }

    }

    @Test
    public void addCategoryWithShortName() {
        addCategoryWithInvalidName("A");
    }

    @Test
    public void addCategoryWithLongName() {
        addCategoryWithInvalidName("this is a long name that will cause an exception tobe thrown");
    }

    @Test(expected = CategoryExistentException.class)
    public void addCategoryWithExistentName() {
        when(categoryRepository.alreadyExists(java())).thenReturn(true);

        categoryServices.add(java());
    }

    @Test()
    public void addValidCategory() {
        when(categoryRepository.alreadyExists(java())).thenReturn(false);
        when(categoryRepository.add(java())).thenReturn(categoryWithId(java(), 1L));

        Category categoryAdded = categoryServices.add(java());
        assertThat(categoryAdded.getId(), is(equalTo(1L)));

    }

    private void addCategoryWithInvalidName(String name) {
        try{
            categoryServices.add(new Category(name));
            fail("An error should not have been thrown");
        }
        catch (FieldNotValidException e) {
            assertThat(e.getFieldName(), is(equalTo("name")));
        }

    }

}
