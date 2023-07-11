package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import Utils.AppUtils;
import daos.BookDAO;
import daos.CategoryDAO;
import daos.CommentDAO;
import daos.RatingDAO;
import models.Book;
import models.BookDetail;
import models.Category;
import models.Comment;
import models.Rating;

@RestController
@RequestMapping("/v1/book-api")
@CrossOrigin
public class BookController {
	public static final String PROJECT_PATH = System.getProperty("user.dir");
	public static final String IMAGE_LOCAL_STORE_PATH = "images/";
	
	private BookDAO bookDao = new BookDAO();
	private CategoryDAO categoryDAO = new CategoryDAO();
	private CommentDAO commnetDAO = new CommentDAO();
	private RatingDAO ratingDAO = new RatingDAO();
	
	@GetMapping("/books")
	public List<Book> getBooks() {
		try {
			return bookDao.getBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/book-detail/{id}")
	public ResponseEntity<?> getBookDetailById(@PathVariable int id) {
		try {
			Book book = bookDao.getBookById(id);
			List<Comment> cm = commnetDAO.getCommnetByBookId(id);
			List<Rating> rt = ratingDAO.getRatings(id);
			
			return ResponseEntity.ok().body(rt);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
	
	@GetMapping("/book/{id}")
	public Book getBookById(@PathVariable int id) {
		try {
			return bookDao.getBookById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/image/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(IMAGE_LOCAL_STORE_PATH  + imageName));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte[] imageBytes = bos.toByteArray();
        return ResponseEntity.ok().body(imageBytes);
    }
	
	@PostMapping("/upload-image")
	public ResponseEntity<?> uploadBookWithImage(
			@RequestParam("id") int id,
    		@RequestParam("title") String title,
    		@RequestParam("author") String author,
    		@RequestParam("description") String description,
    		@RequestParam("release") String date,
    		@RequestParam("pages") int pages,
    		@RequestParam("category") String category,
    		@RequestParam("price") long price,
    		@RequestParam("cover") MultipartFile file,
    		@RequestParam("availability") boolean availability) {
        try { 	
        	
            String fileName = file.getOriginalFilename();

            Book book = new Book(title, author, description, fileName, AppUtils.stringToDate(date), pages, category, price);
            book.setId(id);
            book.setAvailability(availability);         
            
            int addedBookId = bookDao.upload(book);
            if(addedBookId > -1) {
            	file.transferTo(new File(PROJECT_PATH + "/" + IMAGE_LOCAL_STORE_PATH + addedBookId + "-" + fileName));
	            return ResponseEntity.ok("{\"message\":\"Book uploaded successfully\"}");
            } else if(addedBookId == -2) {
            	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
 	                    .body("{\"message\":\"This Already exist books with title and author\"}");
            } else
            	 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
 	                    .body("{\"message\":\"Fail to add\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

	@PostMapping("/upload")
    public ResponseEntity<String> uploadBookWithoutImage(
    		@RequestParam("id") int id,
    		@RequestParam("title") String title,
    		@RequestParam("author") String author,
    		@RequestParam("description") String description,
    		@RequestParam("release") String date,
    		@RequestParam("pages") int pages,
    		@RequestParam("price") long price,
    		@RequestParam("category") String category,
    		@RequestParam("availability") boolean availability) {
        try {
            Book book = new Book(title, author, description, "", AppUtils.stringToDate(date), pages, category, price);
            book.setId(id);
            book.setAvailability(availability);
            
            int addedBookId = bookDao.upload(book);
            if(addedBookId > -1) {
	            return ResponseEntity.ok("{\"message\":" + "\"Book uploaded successfully\"}");
            } else if(addedBookId == -2) {
            	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
 	                    .body("{\"message\":\"This Already exist books with title and author\"}");
            } else
            	 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
 	                    .body("{\"message\":\"Fail to add\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() +"\"}");
        }
    }
	
	@DeleteMapping("/delete/{id}")
	 public ResponseEntity<String> deleteBook(@PathVariable int id) {
	        try {
	        	Book book = bookDao.getBookById(id);
	            boolean isSuccessfull = bookDao.delete(id);
	            if(isSuccessfull) {
	            	String imagePath = IMAGE_LOCAL_STORE_PATH + book.getId() + "-" + book.getCoverPath();
	                File imageFile = new File(imagePath);
	                if (imageFile.exists()) {
	                    boolean deleted = imageFile.delete();
	                    if (deleted) {
	                        System.out.println("Image file deleted successfully.");
	                    } else {
	                        System.out.println("Failed to delete the image file.");
	                    }
	                }
		            return ResponseEntity.ok("{\"message\":" + "\"Book id delete successfully\"}");
	            } else 
	            	 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	 	                    .body("{\"message\":\"Fail to delete\"}");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("{\"message\":\"" + e.getMessage() +"\"}");
	        }
	    }
	
	@GetMapping("/categories")
	public List<Category> getCategories() {
		return categoryDAO.getCategories();
	}
	
	
}
