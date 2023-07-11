package controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import daos.CommentDAO;
import models.Comment;

@RestController
@RequestMapping("/v1/comment-api")
@CrossOrigin
public class CommentController {
	private CommentDAO commentDAO = new CommentDAO();
	
	@GetMapping("/comments/{id_book}")
	public ResponseEntity<?> getCommentByIdBook(@PathVariable int id_book) {
		try {
			List<Comment> cm = commentDAO.getCommnetByBookId(id_book);
			
			return ResponseEntity.ok().body(cm);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
	
	@PostMapping("/comment")
	public ResponseEntity<?> commentByUser(@RequestParam int id_user, @RequestParam int id_book, @RequestParam String comment) {
		try {
			Comment cm = new Comment(id_user, id_book, comment);
			boolean successfull = commentDAO.addComment(cm);
			if(successfull)
				return ResponseEntity.ok().body("{\"message\":\"Comment successfully!\"}");
			else 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Comment unsuccessfully!\"}");
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
}
