package telran.java45.dao;

import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java45.book.model.Book;

public interface BookRepository extends CrudRepository<Book, String> {

	Stream<Book> findByAuthorsName(String name);
	
	Stream<Book> findByPublisherPublisherName(String name);

	void deleteByAuthorsName(String authorName);
}
