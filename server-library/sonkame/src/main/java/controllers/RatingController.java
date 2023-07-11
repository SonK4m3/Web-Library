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

import daos.RatingDAO;
import models.Rating;

@RestController
@RequestMapping("/v1/rating-api")
@CrossOrigin
public class RatingController {
	private RatingDAO ratingDAO = new RatingDAO();
	
	@GetMapping("/rates/{id_book}")
	public ResponseEntity<?> getRating(@PathVariable int id_book) {
		try {
			List<Rating> rt = ratingDAO.getRatings(id_book);
			double total_rating = 0;
			int n = 0;
			for(Rating r: rt) {
				total_rating += r.getNumber();
				n += 1;
			}
			
			total_rating = total_rating / n;
			
			return ResponseEntity.ok().body("{\"rates\": \"" + total_rating + "\"}");
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
	
	@PostMapping("/rating")
	public ResponseEntity<?> ratingByUser(@RequestParam int id_user, @RequestParam int id_book, @RequestParam int number) {
		try {
			Rating rt = new Rating(id_user, id_book, number);
			boolean successfull = ratingDAO.addRating(rt);
			
			return ResponseEntity.ok().body("{\"message\":\"Rating successfully!\"}");
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
}
