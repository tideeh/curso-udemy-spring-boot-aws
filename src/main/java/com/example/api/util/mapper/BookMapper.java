package com.example.api.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.api.model.Book;
import com.example.api.vo.v1.BookVO;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    @Mapping(target = "add", ignore = true)
    BookVO bookToBookVO(Book o);

    Book bookVOToBook(BookVO o);

    List<BookVO> bookListToBookVOList(List<Book> o);
    List<Book> bookVOListToBookList(List<BookVO> o);
    
}
