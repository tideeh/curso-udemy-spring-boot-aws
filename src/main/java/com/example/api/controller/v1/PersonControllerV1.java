package com.example.api.controller.v1;

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

import com.example.api.service.PersonService;
import com.example.api.util.MediaType;
import com.example.api.vo.v1.PersonVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People v1", description = "Endpoints for managing People")
public class PersonControllerV1 {

    @Autowired
    private PersonService service;
    // private PersonServices service = new PersonServices();

    @GetMapping(
        value = "/{id}",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Finds a Person", 
        description = "Finds a Person", 
        tags = {"People v1"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVO.class))
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
    public PersonVO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(
        value = "",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Finds all People", 
        description = "Finds all People", 
        tags = {"People v1"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))
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
    public List<PersonVO> findAll() throws Exception {
        return service.findAll();
    }

    @PostMapping(
        value = "",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Adds a new Person", 
        description = "Adds a new Person", 
        tags = {"People v1"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVO.class))
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
    public PersonVO create(@RequestBody PersonVO person) throws Exception {
        return service.create(person);
    }

    @PutMapping(
        value = "", 
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Updates a Person", 
        description = "Updates a Person", 
        tags = {"People v1"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVO.class))
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
    public PersonVO update(@RequestBody PersonVO person) throws Exception {
        return service.update(person);
    }

    @DeleteMapping(
        value = "/{id}"
    )
    @Operation(
        summary = "Deletes a Person", 
        description = "Deletes a Person", 
        tags = {"People v1"}, 
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
