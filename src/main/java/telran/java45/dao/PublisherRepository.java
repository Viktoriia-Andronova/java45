package telran.java45.dao;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java45.book.model.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher, String>{

	@Query("select distinct p.publisherName from Book b join b.authors a join b.publisher p"
			+ "where a.name=?1")
	List<String> findPublishersByAuthor(String authorName);
}
