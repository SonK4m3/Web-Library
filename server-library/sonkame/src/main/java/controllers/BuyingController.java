package controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import daos.BuyingDAO;
import models.Buying;
import models.BuyingDetail;


@RestController
@RequestMapping("/v1/buying-api")
@CrossOrigin
public class BuyingController {
	private BuyingDAO buyingDAO = new BuyingDAO();
	
	@GetMapping("/buyings/{id_user}")
	public ResponseEntity<?> getBuyingsByUser(@PathVariable int id_user) {
		try {
			List<Buying> ls = buyingDAO.getBuyingByUser(id_user);
			
			return ResponseEntity.ok().body(ls);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
	
	@PostMapping("/cancel/{id}")
	public ResponseEntity<?> cancelBuying(@RequestBody List<BuyingDetail> buyings, @PathVariable int id) {
		try {
			Buying buying = new Buying();
			buying.setId(id);
			buying.setBuyings(buyings);
			
			boolean successfull = buyingDAO.cancel(buying);
			if(successfull)
				return ResponseEntity.ok().body("{\"message\":\"Cancel oder successfully!\"}");
			else 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Cancel order unsuccessfully!\"}");
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
	
	@PostMapping("/order/{id_user}")
	public ResponseEntity<?> orderBuying(@RequestBody List<BuyingDetail> buyings, @PathVariable int id_user){
		try {
			Buying buying = new Buying();
			buying.setId_user(id_user);
			buying.setBuyings(buyings);
			
			boolean success = buyingDAO.buying(buying);
			
			if(success) {
				return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Order successfully!\"}");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Order unsuccessfully!\"}");

		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
}
