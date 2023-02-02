package com.example.api.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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

    public List<BookVO> findAll() throws Exception {
        logger.info("Find all Books - V1");

        List<BookVO> voList = BookMapper.INSTANCE.bookListToVOList(repository.findAll());

        // Hateoas
        for (BookVO vo : voList) {
            Link link = linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel();
            vo.add(link);
        }

        return voList;
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
