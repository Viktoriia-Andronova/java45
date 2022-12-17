package telran.java45.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java45.book.model.Author;

public interface AuthorRepository  extends CrudRepository<Author, String>{

}
