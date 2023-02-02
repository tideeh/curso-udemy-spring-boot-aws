package com.example.api.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.api.model.Book;
import com.example.api.util.vo.v1.BookVO;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    BookVO bookToVO(Book o);
    Book VOToBook(BookVO o);

    List<BookVO> bookListToVOList(List<Book> o);
    List<Book> VOListToBookList(List<BookVO> o);
    
}
