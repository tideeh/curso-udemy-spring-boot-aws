package com.example.api.mocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.api.model.Book;
import com.example.api.vo.v1.BookVO;

public class MockBook {

    public Book mockEntity() throws ParseException {
        return mockEntity(0);
    }
    
    public Book mockEntity(Integer number) throws ParseException {
        Book entity = new Book();

        entity.setId(number.longValue());
        entity.setAuthor("Author Test" + number);
        entity.setLaunchDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/"+(1900+number)));
        entity.setPrice(100D + number);
        entity.setTitle("Title Test" + number);

        return entity;
    }

    public BookVO mockVO() throws ParseException {
        return mockVO(0);
    }

    public BookVO mockVO(Integer number) throws ParseException {
        BookVO vo = new BookVO();

        vo.setKey(number.longValue());
        vo.setAuthor("Author Test" + number);
        vo.setLaunchDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/"+(1900+number)));
        vo.setPrice(100D + number);
        vo.setTitle("Title Test" + number);

        return vo;
    }
    
    public List<Book> mockEntityList() throws ParseException {
        List<Book> entityList = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            entityList.add(mockEntity(i));
        }
        return entityList;
    }

    public List<BookVO> mockVOList() throws ParseException {
        List<BookVO> voList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            voList.add(mockVO(i));
        }
        return voList;
    }

}
