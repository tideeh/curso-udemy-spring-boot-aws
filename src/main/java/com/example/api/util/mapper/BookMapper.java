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

    @Mapping(source = "id", target = "key")
    @Mapping(target = "add", ignore = true)
    BookVO bookToVO(Book o);

    @Mapping(source = "key", target = "id")
    Book VOToBook(BookVO o);

    @Mapping(source = "id", target = "key")
    List<BookVO> bookListToVOList(List<Book> o);

    @Mapping(source = "key", target = "id")
    List<Book> VOListToBookList(List<BookVO> o);
    
}
