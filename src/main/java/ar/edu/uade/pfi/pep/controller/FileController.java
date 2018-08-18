package ar.edu.uade.pfi.pep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.pfi.pep.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileService;

	@GetMapping(value="/{fileId}", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> activate(@PathVariable("fileId") String fileId) {
		try {
		    return ResponseEntity.ok(this.fileService.getFileBytesById(fileId));
		} catch (Exception e) {
			FileController.LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().build();
		}
	}
}
