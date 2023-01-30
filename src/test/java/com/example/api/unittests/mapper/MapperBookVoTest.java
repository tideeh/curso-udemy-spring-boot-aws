package com.example.api.unittests.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.api.util.mapper.BookMapper;
import com.example.api.vo.v1.BookVO;
import com.example.api.mocks.MockBook;
import com.example.api.model.Book;

@TestInstance(Lifecycle.PER_CLASS)
public class MapperBookVoTest {

    @Autowired
    BookMapper mapper;
    
    MockBook inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockBook();
        mapper = BookMapper.INSTANCE;
    }

    @Test
    public void bookToVOTest() throws ParseException {
        BookVO output = mapper.bookToVO(inputObject.mockEntity());
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Author Test0", output.getAuthor());
        assertTrue(output.getLaunchDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1900")));
        assertEquals(100.00, output.getPrice());
        assertEquals("Title Test0", output.getTitle());
    }

    @Test
    public void VOToBookTest() throws ParseException {
        Book output = mapper.VOToBook(inputObject.mockVO());
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Author Test0", output.getAuthor());
        assertTrue(output.getLaunchDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1900")));
        assertEquals(100.00, output.getPrice());
        assertEquals("Title Test0", output.getTitle());
    }

    @Test
    public void bookListToVOListTest() throws ParseException {
        List<BookVO> outputList = mapper.bookListToVOList(inputObject.mockEntityList());
        BookVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Author Test0", outputZero.getAuthor());
        assertTrue(outputZero.getLaunchDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1900")));
        assertEquals(100.00, outputZero.getPrice());
        assertEquals("Title Test0", outputZero.getTitle());
        
        BookVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Author Test7", outputSeven.getAuthor());
        assertTrue(outputSeven.getLaunchDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1907")));
        assertEquals(107.00, outputSeven.getPrice());
        assertEquals("Title Test7", outputSeven.getTitle());
        
        BookVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Author Test12", outputTwelve.getAuthor());
        assertTrue(outputTwelve.getLaunchDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1912")));
        assertEquals(112.00, outputTwelve.getPrice());
        assertEquals("Title Test12", outputTwelve.getTitle());
    }

    @Test
    public void VOListToBookListTest() throws ParseException {
        List<Book> outputList = mapper.VOListToBookList(inputObject.mockVOList());
        Book outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Author Test0", outputZero.getAuthor());
        assertTrue(outputZero.getLaunchDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1900")));
        assertEquals(100.00, outputZero.getPrice());
        assertEquals("Title Test0", outputZero.getTitle());
        
        Book outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Author Test7", outputSeven.getAuthor());
        assertTrue(outputSeven.getLaunchDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1907")));
        assertEquals(107.00, outputSeven.getPrice());
        assertEquals("Title Test7", outputSeven.getTitle());
        
        Book outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Author Test12", outputTwelve.getAuthor());
        assertTrue(outputTwelve.getLaunchDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1912")));
        assertEquals(112.00, outputTwelve.getPrice());
        assertEquals("Title Test12", outputTwelve.getTitle());
    }
    
}
