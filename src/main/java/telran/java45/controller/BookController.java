package telran.java45.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java45.book.dto.AuthorDto;
import telran.java45.book.dto.BookDto;
import telran.java45.book.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

	final BookService bookService;

	@PostMapping("/book")
	public boolean addBook(@RequestBody BookDto bookDto) {
		
		return bookService.addBook(bookDto);
	}

	@GetMapping("/book/{isbn}")
	public BookDto findBookByIsbn(@PathVariable String isbn) {
		
		return bookService.findBookByIsbn(isbn);
	}

	@DeleteMapping("/book/{isbn}")
	public BookDto remove(@PathVariable String isbn) {
		
		return bookService.remove(isbn);
	}

	@PostMapping("/book/{isbn}/title/{title}")
	public BookDto upBook(@PathVariable String isbn,@PathVariable String title) {
		
		return bookService.updateBook(isbn, title);
	}

	@GetMapping("/book/author/{author}")
	public Iterable<BookDto> findBooksByAuthor(@PathVariable String author) {
		
		return bookService.findBooksByAuthor(author);
	}

	@GetMapping("/book/publisher/{publisher}")
	public Iterable<BookDto> findBooksByPublisher(@PathVariable String publisher) {
		
		return bookService.findBooksByPublisher(publisher);
	}

	@GetMapping("/authors/book/{isbn}")
	public Iterable<AuthorDto> findBookAuthors(@PathVariable String isbn) {
		
		return bookService.findBookAuthors(isbn);
	}

	@GetMapping("/publisher/author/{author}")
	public Iterable<String> findPublishersByAuthor(@PathVariable String authorName) {
		
		return bookService.findPublishersByAuthor(authorName);
	}

	@DeleteMapping("/author/{author}")
	public AuthorDto removeAuthor(@PathVariable String author) {
		
		return bookService.removeAuthor(author);
	}
}
