package ar.edu.uade.pfi.pep.service;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class FileService {

	@Autowired
	private GridFsOperations gridOperations;

	public byte[] getFileBytesById(String fileId) throws Exception, IOException {
		Criteria c = Criteria.where("_id").is(new ObjectId(fileId));
		GridFSFile fsFile = this.gridOperations.findOne(new Query(c));
		GridFsResource file = this.gridOperations.getResource(fsFile.getFilename());
		return IOUtils.toByteArray(file.getInputStream());
	}
}
