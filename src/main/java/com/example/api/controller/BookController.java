package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.service.BookService;
import com.example.api.util.MediaType;
import com.example.api.util.vo.v1.BookVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoints for managing Books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping(
        value = "/{id}",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Finds a Book", 
        description = "Finds a Book - v1", 
        tags = {"Book"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = BookVO.class))
            ),
            @ApiResponse(
                description = "No Content", 
                responseCode = "204", 
                content = @Content
            ),
            @ApiResponse(
                description = "Bad Request", 
                responseCode = "400", 
                content = @Content
            ),
            @ApiResponse(
                description = "Unauthorized", 
                responseCode = "401", 
                content = @Content
            ),
            @ApiResponse(
                description = "Not Found", 
                responseCode = "404", 
                content = @Content
            ),
            @ApiResponse(
                description = "Internal Errorr", 
                responseCode = "500", 
                content = @Content
            ),
        }
    )
    public BookVO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(
        value = "",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Finds all Books", 
        description = "Finds all Books - v1", 
        tags = {"Book"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookVO.class)))
            ),
            @ApiResponse(
                description = "Bad Request", 
                responseCode = "400", 
                content = @Content
            ),
            @ApiResponse(
                description = "Unauthorized", 
                responseCode = "401", 
                content = @Content
            ),
            @ApiResponse(
                description = "Not Found", 
                responseCode = "404", 
                content = @Content
            ),
            @ApiResponse(
                description = "Internal Errorr", 
                responseCode = "500", 
                content = @Content
            ),
        }
    )
    public List<BookVO> findAll() throws Exception {
        return service.findAll();
    }

    @PostMapping(
        value = "",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Adds a new Book", 
        description = "Adds a new Book - v1", 
        tags = {"Book"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = BookVO.class))
            ),
            @ApiResponse(
                description = "Bad Request", 
                responseCode = "400", 
                content = @Content
            ),
            @ApiResponse(
                description = "Unauthorized", 
                responseCode = "401", 
                content = @Content
            ),
            @ApiResponse(
                description = "Internal Errorr", 
                responseCode = "500", 
                content = @Content
            ),
        }
    )
    public BookVO create(@RequestBody BookVO book) throws Exception {
        return service.create(book);
    }

    @PutMapping(
        value = "", 
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Updates a Book", 
        description = "Updates a Book - v1", 
        tags = {"Book"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = BookVO.class))
            ),
            @ApiResponse(
                description = "Bad Request", 
                responseCode = "400", 
                content = @Content
            ),
            @ApiResponse(
                description = "Unauthorized", 
                responseCode = "401", 
                content = @Content
            ),
            @ApiResponse(
                description = "Not Found", 
                responseCode = "404", 
                content = @Content
            ),
            @ApiResponse(
                description = "Internal Errorr", 
                responseCode = "500", 
                content = @Content
            ),
        }
    )
    public BookVO update(@RequestBody BookVO book) throws Exception {
        return service.update(book);
    }

    @DeleteMapping(
        value = "/{id}"
    )
    @Operation(
        summary = "Deletes a Book", 
        description = "Deletes a Book - v1", 
        tags = {"Book"}, 
        responses = {
            @ApiResponse(
                description = "No content", 
                responseCode = "204", 
                content = @Content
            ),
            @ApiResponse(
                description = "Bad Request", 
                responseCode = "400", 
                content = @Content
            ),
            @ApiResponse(
                description = "Unauthorized", 
                responseCode = "401", 
                content = @Content
            ),
            @ApiResponse(
                description = "Not Found", 
                responseCode = "404", 
                content = @Content
            ),
            @ApiResponse(
                description = "Internal Errorr", 
                responseCode = "500", 
                content = @Content
            ),
        }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
