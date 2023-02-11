package com.example.api.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.example.api.controller.BookController;
import com.example.api.repository.BookRepository;
import com.example.api.util.exception.RequiredObjectIsNullException;
import com.example.api.util.exception.ResourceNotFoundException;
import com.example.api.util.mapper.BookMapper;
import com.example.api.util.vo.v1.BookVO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookVO> assembler;

    private Logger logger = Logger.getLogger(BookService.class.getName());

    public BookVO findById(Long id) throws Exception {
        logger.info("Find Book by ID - V1");

        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        
        BookVO vo = BookMapper.INSTANCE.bookToVO(entity);

        // Hateoas
        Link link = linkTo(methodOn(BookController.class).findById(id)).withSelfRel();
        vo.add(link);

        return vo;
    }

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) throws Exception {
        logger.info("Find all Books");

        var page = repository.findAll(pageable);

        var voPage = page.map(p -> BookMapper.INSTANCE.bookToVO(p));

        // Hateoas
        voPage.map(
            p -> {
                try {
                    return p.add(linkTo(methodOn(BookController.class).findById(p.getId())).withSelfRel());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return p;
            }
        );
        Link link = linkTo(
            methodOn(BookController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString().toLowerCase().endsWith("desc") ? "desc" : "asc")
        ).withSelfRel();
        
        return assembler.toModel(voPage, link);
    }

    public BookVO create(BookVO bookVO) throws Exception {
        logger.info("Create one book - V1");

        if(bookVO == null)
            throw new RequiredObjectIsNullException();

        var entity = BookMapper.INSTANCE.VOToBook(bookVO);
        var vo = BookMapper.INSTANCE.bookToVO(repository.save(entity));

        // Hateoas
        Link link = linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel();
        vo.add(link);

        return vo;
    }

    public BookVO update(BookVO bookVO) throws Exception {
        logger.info("Update the book - V1");

        if(bookVO == null)
            throw new RequiredObjectIsNullException();

        var entity = repository.findById(bookVO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        
        entity.setAuthor(bookVO.getAuthor());
        entity.setLaunchDate(bookVO.getLaunchDate());
        entity.setPrice(bookVO.getPrice());
        entity.setTitle(bookVO.getTitle());

        var vo = BookMapper.INSTANCE.bookToVO(repository.save(entity));

        // Hateoas
        Link link = linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel();
        vo.add(link);

        return vo;
    }

    public void delete(Long id) {
        logger.info("Delete the book");

        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        
        repository.delete(entity);
    }
}
