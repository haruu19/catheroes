package com.web.blog.utill.amazon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class AmazonClient {

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	private Logger logger = LoggerFactory.getLogger(AmazonClient.class);

	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	// 파일 업로드
	public String uploadFile(MultipartFile multipartFile, long catId, String path) throws IOException {
		String fileUrl = "";
		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileName = multipartFile.getOriginalFilename();
			fileUrl = "static/"+ path + catId+this.getExt(fileName);
			uploadFileTos3bucket(Long.toString(catId) +this.getExt(fileName) , file, path);
			file.delete();
		} /*
			 * catch (Exception e) { e.printStackTrace(); }
			 */
		catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());

		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		} catch (IOException ioe) {
			logger.info("IOE Error Message: " + ioe.getMessage());

		}
		return fileUrl;

	}

	// 파일 삭제
	public String deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
		return "Successfully deleted";
	}

	// S3에 업로드
	private void uploadFileTos3bucket(String fileName, File file, String path) {
		s3client.putObject(new PutObjectRequest(bucketName, "static/" + path + fileName, file));
		// .withCannedAcl(CannedAccessControlList.PublicRead));
	}

	// 이름 변경
	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	// 확장자 가져오기(.확장자)
	private String getExt(String fileName) {
		return "."+fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	// 파일 변환
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	// 디렉토리 파일 검사
	public void isDir(String root) {
		ObjectListing objectListing = s3client.listObjects(bucketName, root);
		for (S3ObjectSummary s : objectListing.getObjectSummaries()) {
			// 파일이름
			System.out.println("file name : " + s.getKey());
			// 확장자 가져오기
			System.out.println("extension : " + this.getExt(s.getKey()));
		}
	}
}