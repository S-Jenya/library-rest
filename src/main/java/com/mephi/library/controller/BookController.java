package com.mephi.library.controller;

import com.mephi.library.model.*;
import com.mephi.library.postRequestResponse.response.BookInfoResponse;
import com.mephi.library.postRequestResponse.response.MessageResponse;
import com.mephi.library.postRequestResponse.response.bookResponse;
import com.mephi.library.service.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final UserService userService;
    private final CommentService commentService;

    public BookController(GenreService genreService, AuthorService authorService, BookService bookService, UserService userService, CommentService commentService) {
        this.genreService = genreService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/admin/getBookByGenre/{idGenre}")
    public List<Book> getBookByGenre(@PathVariable Long idGenre) {
        Genre genre = genreService.findGenreById(idGenre);
        return bookService.getBookByGenre(genre);
    }

    @PostMapping(value = "/admin/uploadBookInetImage")
    public void UploadBookInetImage(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "genre") Long idGenre,
            @RequestParam(name = "author") Long idAuthor,
            @RequestParam(name = "fileContent") MultipartFile cont) {
        try {
            Book book = new Book();
            book.setName(name);
            book.setYear(year);
            book.setDescription(description);
            Genre genre = genreService.findGenreById(idGenre);
            book.setGenre(genre);
            Author author = authorService.getAuthorById(idAuthor);
            book.setAuthor(author);
            book.setContent(cont.getBytes());
            bookService.saveBook(book);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
        }
    }

    @PostMapping(value = "/admin/uploadBookUserImage")
    public void UploadBookUserImage(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "genre") Long idGenre,
            @RequestParam(name = "author") Long idAuthor,
            @RequestParam(name = "fileImage") MultipartFile img,
            @RequestParam(name = "fileContent") MultipartFile cont) {
        try {
            Book book = new Book();
            book.setName(name);
            book.setYear(year);
            book.setDescription(description);
            Genre genre = genreService.findGenreById(idGenre);
            book.setGenre(genre);
            Author author = authorService.getAuthorById(idAuthor);
            book.setAuthor(author);
            book.setImage(img.getBytes());
            book.setContent(cont.getBytes());
            bookService.saveBook(book);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
        }
    }

    @GetMapping("/cards/getImg/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Book book = bookService.getFile(Long.parseLong(id));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + book.getName() + ".jpg\"")
                .body(book.getImage());
    }

    @GetMapping("/book/getInfo/{id}")
    public ResponseEntity<BookInfoResponse> getBookInfo(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        List<Comment> comments = commentService.getCommentByBookId(book);
        boolean flag = book.imageIsExist();
        List<String> comm = new ArrayList<String>();
        for (Comment comment : comments) {
            comm.add(comment.getText());
        }
        BookInfoResponse files = null;
        Map<String, Object> map = new HashMap<>();
        if (flag) {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/cards/getImg/")
                    .path(book.getIdBook().toString())
                    .toUriString();

            files = new BookInfoResponse(
                    id,
                    true,
                    book.getName(),
                    book.getYear(),
                    book.getDescription(),
                    book.getAuthor().getLastName() + " " + book.getAuthor().getFirstName() + " " + book.getAuthor().getPatronymic(),
                    book.getGenre().getName(),
                    fileDownloadUri,
                    comments
            );
        } else {
            files = new BookInfoResponse(
                    id,
                    false,
                    book.getName(),
                    book.getYear(),
                    book.getDescription(),
                    book.getAuthor().getLastName() + " " + book.getAuthor().getFirstName() + " " + book.getAuthor().getPatronymic(),
                    book.getGenre().getName(),
                    "https://www.googleapis.com/books/v1/volumes?q=" + book.getName(),
                    comments
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


    @Transactional
    @GetMapping("/book/downloadBookContent/{idUser}/{idBook}")
    public ResponseEntity<byte[]> getBookContent(@PathVariable Long idUser, @PathVariable Long idBook) {

        Book book = bookService.getFile(idBook);
        User user = userService.findUserById(idUser);

        try {
            user.addBook(book);
            userService.saveUser(user);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
        }


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + book.getName() + ".pdf\"")
                .body(book.getContent());
    }

    @DeleteMapping(value = "/admin/book/deleteBook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Book book;
        try {
            List<User> user = userService.findListUserBooks(id);
            for (User userList: user){
                for (Book bookItem: userList.getBooks()){
                    if(bookItem.getIdBook().equals(id)) {
                        userList.getBooks().remove(bookItem);
                        userService.saveUser(userList);
                    }
                }
            }
            List<Comment> comments = commentService.findListCommentsByIdBook(id);
            for(Comment commentItemId: comments) {
                commentService.deleteCommentById(commentItemId.getIdComment());
            }

            book = bookService.getBookById(id);
            bookService.delBook(book);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Ошибка! Подробнее: " + ex.getMessage()));
        }
        return ResponseEntity.ok().body(new MessageResponse("Книга: \"" + book.getName() + "\" успешно удалёна"));
    }

    @PostMapping(value = "/admin/updBookData")
    public ResponseEntity<?> updBookData(
            @RequestParam(name = "idBook") Long idBook,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "genre") Long idGenre,
            @RequestParam(name = "author") Long idAuthor,
            @RequestParam(name = "imgFromEthernet", required = false) String imgFromEthernet,
            @RequestParam(name = "fileImage", required = false) MultipartFile img,
            @RequestParam(name = "fileContent", required = false) MultipartFile cont) {
        try {
            Book book = bookService.getBookById(idBook);
            book.setName(name);
            book.setYear(year);
            book.setDescription(description);
            Genre genre = genreService.findGenreById(idGenre);
            book.setGenre(genre);
            Author author = authorService.getAuthorById(idAuthor);
            book.setAuthor(author);
            if(imgFromEthernet != null) {
                book.setImage(null);
            }
            if(img != null) {
                book.setImage(img.getBytes());
            }
            if(cont != null) {
                book.setContent(cont.getBytes());
            }
            bookService.saveBook(book);
        } catch (Exception ex) {
            String strEx = ex.getCause().getCause().getMessage();
            return ResponseEntity.badRequest().body(new MessageResponse(strEx));
        }

        return ResponseEntity.ok().body(new MessageResponse("Унига успешно обновлена"));
    }

}
