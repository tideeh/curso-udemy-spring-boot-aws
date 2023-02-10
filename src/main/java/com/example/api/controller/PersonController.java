package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.service.PersonService;
import com.example.api.util.MediaType;
import com.example.api.util.vo.v1.PersonVO;
import com.example.api.util.vo.v2.PersonVOV2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin
@RestController
@RequestMapping("/api/person")
@Tag(name = "People", description = "Endpoints for managing People")
public class PersonController {

    @Autowired
    private PersonService service;
    // private PersonServices service = new PersonServices();

    @GetMapping(
        value = "/v1/{id}",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Finds a Person", 
        description = "Finds a Person - v1", 
        tags = {"People"}, 
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
    //@CrossOrigin(origins = {"http://localhost:8080"})
    public PersonVO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(
        value = "/v1",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Finds all People", 
        description = "Finds all People - v1", 
        tags = {"People"}, 
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
        value = "/v1",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Adds a new Person", 
        description = "Adds a new Person - v1", 
        tags = {"People"}, 
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
    //@CrossOrigin(origins = {"http://localhost:8080", "http://google.com"})
    public PersonVO create(@RequestBody PersonVO person) throws Exception {
        return service.create(person);
    }

    @PutMapping(
        value = "/v1", 
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Updates a Person", 
        description = "Updates a Person - v1", 
        tags = {"People"}, 
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
        value = "/v1/{id}"
    )
    @Operation(
        summary = "Deletes a Person", 
        description = "Deletes a Person - v1", 
        tags = {"People"}, 
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
    
    /*
     * v2
     */
    @GetMapping(
        value = "/v2/{id}",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Finds a Person", 
        description = "Finds a Person - v2", 
        tags = {"People"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVOV2.class))
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
    public PersonVOV2 findByIdV2(@PathVariable(value = "id") Long id) throws Exception {
        return service.findByIdV2(id);
    }

    @GetMapping(
        value = "/v2",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Finds all People", 
        description = "Finds all People - v2", 
        tags = {"People"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonVOV2.class)))
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
    public List<PersonVOV2> findAllV2() throws Exception {
        return service.findAllV2();
    }

    @PostMapping(
        value = "/v2",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Adds a new Person", 
        description = "Adds a new Person - v2", 
        tags = {"People"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVOV2.class))
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
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person) throws Exception {
        return service.createV2(person);
    }

    @PutMapping(
        value = "/v2", 
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Updates a Person", 
        description = "Updates a Person - v2", 
        tags = {"People"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVOV2.class))
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
    public PersonVOV2 updateV2(@RequestBody PersonVOV2 person) throws Exception {
        return service.updateV2(person);
    }

    @DeleteMapping(
        value = "/v2/{id}"
    )
    @Operation(
        summary = "Deletes a Person", 
        description = "Deletes a Person - v2", 
        tags = {"People"}, 
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
    public ResponseEntity<?> deleteV2(@PathVariable(value = "id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(
        value = "/v2/{id}",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    @Operation(
        summary = "Disable a Person", 
        description = "Disable a Person by your ID - v2", 
        tags = {"People"}, 
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVOV2.class))
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
    public PersonVOV2 disablePersonV2(@PathVariable(value = "id") Long id) throws Exception {
        return service.disablePersonV2(id);
    }

}
