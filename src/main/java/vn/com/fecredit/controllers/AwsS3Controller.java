package vn.com.fecredit.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import vn.com.fecredit.models.ApiResponse;
import vn.com.fecredit.models.UrlModel;
import vn.com.fecredit.services.AmazonS3Service;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/awss3")
public class AwsS3Controller {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3Controller.class);

	@Autowired
	private AmazonS3Service amazonS3Service;

	@GetMapping("/")
	public String index(){
		return "Hello world!";
	}

	@PostMapping("/getPresignUrl")
	public ResponseEntity<ApiResponse> getPresignUrl(@Valid @RequestBody UrlModel model) {
		LOGGER.info("Enter controller endpoint!");
		LOGGER.info(model.toString());
		ApiResponse res = new ApiResponse();
		String fileKey = model.getUrl();
		String url = amazonS3Service.presignFile(fileKey);
		Map<String,String> obj = new HashMap<>();
		if (!url.isEmpty()){
			res.setStatus(HttpStatus.OK.value());
			obj.put("url", url);
			res.setData(obj);
			res.setMessage("Successfully created!");
		}else {
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			obj.put("error", "Something went wrong");
			res.setData(obj);
			res.setMessage("Failed to generate presigned url!");
		}

		LOGGER.info("Return response: " + res.toString());
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);

	}

}
