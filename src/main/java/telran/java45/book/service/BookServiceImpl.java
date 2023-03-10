package telran.java45.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java45.book.dto.AuthorDto;
import telran.java45.book.dto.BookDto;
import telran.java45.book.dto.EntityNotFoundException;
import telran.java45.book.model.Author;
import telran.java45.book.model.Book;
import telran.java45.book.model.Publisher;
import telran.java45.dao.AuthorRepository;
import telran.java45.dao.BookRepository;
import telran.java45.dao.PublisherRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
	
	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if(bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		//Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		//Authors
		Set<Author> authors = bookDto.getAuthors().stream()
								.map(a -> authorRepository.findById(a.getName())
										.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
								.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto remove(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new EntityNotFoundException());
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto updateBook(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new EntityNotFoundException());
		book.setTitle(title);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional(readOnly = true)
	public Iterable<BookDto> findBooksByAuthor(String authorName) {
		return bookRepository.findByAuthorsName(authorName)
				.map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toList());
	}


	@Override
	@Transactional
	public Iterable<BookDto> findBooksByPublisher(String publisherName) {
		
		return bookRepository.findByPublisherPublisherName(publisherName)
				.map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<AuthorDto> findBookAuthors(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new EntityNotFoundException());
		return book.getAuthors().stream()
				.map(a -> modelMapper.map(a, AuthorDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<String> findPublishersByAuthor(String authorName) {
		
		return null;
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String authorName) {
		//Hitler
//		Author author = authorRepository.findById(authorName).orElseThrow(() -> new EntityNotFoundException());
//		bookRepository.deleteByAuthorsName(authorName);
//		authorRepository.delete(author);
//		return modelMapper.map(author, AuthorDto.class);
		//Stalin
		Author author = authorRepository.findById(authorName).orElseThrow(() -> new EntityNotFoundException());
		Iterable<Book> booksByAuthor = bookRepository.findByAuthorsName(authorName)
				.collect(Collectors.toList());
		for (Book book : booksByAuthor) {
			Set<Author> authors = book.getAuthors();
			authors.remove(author);
		}
		authorRepository.delete(author);
		return modelMapper.map(author, AuthorDto.class);
	}

}
