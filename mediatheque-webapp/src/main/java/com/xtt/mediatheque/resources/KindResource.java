package com.xtt.mediatheque.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.service.KindService;

@RestController
public class KindResource {

	@Autowired
	private KindService kindService;

	@GetMapping("/getKinds")
	public ResponseEntity<List<KindsDTO>> getKinds() throws TechnicalAccessException {
		return new ResponseEntity<List<KindsDTO>>(kindService.getKinds(), HttpStatus.OK);
	}

}
