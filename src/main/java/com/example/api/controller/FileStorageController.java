package com.example.api.controller;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.api.service.FileStorageService;
import com.example.api.util.vo.v1.upload.UploadFileResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "File EndPoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileStorageController {
    
    private Logger logger = Logger.getLogger(FileStorageController.class.getName());
    
    @Autowired
    private FileStorageService service;

    @PostMapping(value = "/uploadFile")
    @Operation(
        summary = "Upload a file to server", 
        description = "Upload a file to server - v1", 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = UploadFileResponseVO.class))
            ),
            @ApiResponse(
                description = "Internal Error", 
                responseCode = "500", 
                content = @Content
            ),
        }
    )
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Storing file to disk");

        var filename = service.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/file/v1/downloadFile/")
            .path(filename)
            .toUriString();
        
        return new UploadFileResponseVO(filename, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping(value = "/uploadMultipleFiles")
    @Operation(
        summary = "Upload multiple files to server", 
        description = "Upload multiple files to server - v1", 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = UploadFileResponseVO.class)))
            ),
            @ApiResponse(
                description = "Internal Error", 
                responseCode = "500", 
                content = @Content
            ),
        }
    )
    public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        logger.info("Storing files to disk");

        List<UploadFileResponseVO> voList = new ArrayList<>();
        for(MultipartFile file : files) {
            voList.add(uploadFile(file));
        }

        return voList;

        // return Arrays.asList(files)
        //     .stream()
        //     .map(file -> uploadFile(file))
        //     .collect(Collectors.toList());
    }

    @GetMapping(value = "/downloadFile/{filename:.+}")
    @Operation(
        summary = "Download a file from server", 
        description = "Download a file from server - v1", 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content
            ),
            @ApiResponse(
                description = "File Not Found", 
                responseCode = "404", 
                content = @Content
            ),
            @ApiResponse(
                description = "Internal Error", 
                responseCode = "500", 
                content = @Content
            ),
        }
    )
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filename,
            HttpServletRequest request) {

        logger.info("Reading a file on disk");

        Resource resource = service.loadFileAsResource(filename);
        String contentType = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.info("Could not determine file type!");
        }
        
        if(contentType.isBlank())
            contentType = "application/octet-stream";
        
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""+resource.getFilename()+"\""
            )
            .body(resource);
    }
}
