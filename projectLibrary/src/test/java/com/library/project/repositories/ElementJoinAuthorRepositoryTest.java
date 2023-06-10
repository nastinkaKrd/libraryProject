package com.library.project.repositories;

import com.library.project.models.Author;
import com.library.project.models.ElementJoinAuthor;
import com.library.project.models.PrintedElement;
import com.library.project.models.PublishCompany;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ElementJoinAuthorRepositoryTest {
    @Autowired
    private PrintedElementRepository printedElementRepository;
    @Autowired
    private PublishCompanyRepository publishCompanyRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ElementJoinAuthorRepository elementJoinAuthorRepository;
    @Test
    void itShouldFindAllByPrintedElementId() {
        PrintedElement element = new PrintedElement(40, "some book3", "book", "Fantasy",
                14, 2021, 2, new PublishCompany(1, "Ballantine Books"));
        Author author = new Author(1, "Ray bradbury");
        PublishCompany publishCompany = new PublishCompany(1, "Ballantine Books");
        ElementJoinAuthor elementJoinAuthor = new ElementJoinAuthor(1, author, element);
        publishCompanyRepository.save(publishCompany);
        printedElementRepository.save(element);
        authorRepository.save(author);
        elementJoinAuthorRepository.save(elementJoinAuthor);
        ElementJoinAuthor firstData = elementJoinAuthorRepository.findAllByPrintedElementId(element).get(0);
        assert(firstData).equals(elementJoinAuthor);
    }
}