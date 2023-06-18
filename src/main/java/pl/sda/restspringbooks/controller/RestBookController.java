package pl.sda.restspringbooks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.sda.restspringbooks.model.Book;

import java.net.URI;
import java.util.*;
import java.util.stream.LongStream;

@RestController
@RequestMapping("/api/v1/books")
public class RestBookController {
    private List<Book> books = new ArrayList<>(
            List.of(
                    Book
                            .builder()
                            .id(1)
                            .editionYear(1999)
                            .authors("Freeman")
                            .title("Java")
                            .build(),
                    Book
                            .builder()
                            .id(2)
                            .editionYear(2012)
                            .authors("Block | Stock")
                            .title("Spring")
                            .build()
            ));

    @GetMapping("")
    public List<Book> getBooks() {
        return books;
    }

    @PostMapping("")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        final OptionalLong max = books.stream().mapToLong(b -> b.getId()).max();
        book.setId(max.getAsLong() + 1);
        books.add(book);
        return ResponseEntity.created(URI.create("/api/v1/books" + book.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updatedBook(@RequestParam long id,
                                            @RequestBody Book book
    ) {
        if (id != book.getId()) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<Book> optionalBook = books
                .stream()
                .filter(b -> b.getId() == id)
                .findFirst();
        if (optionalBook.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        books.set(books.indexOf(optionalBook.get()), book);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        final Optional<Book> optionalBook = books
                .stream()
                .filter(b -> b.getId() == id)
                .findFirst();
        return ResponseEntity.of(optionalBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        final Optional<Book> optionalBook = books
                .stream()
                .filter(b -> b.getId() == id)
                .findFirst();
        if (optionalBook.isPresent()) {
            books.remove(optionalBook.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/authors")
    public ResponseEntity<List<String>> getBookAuthors(@RequestParam long id) {
        final Optional<Book> optionalBook = books
                .stream()
                .filter(b -> b.getId() == id)
                .findFirst();
        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalBook.get().getAuthors());
    }
}
