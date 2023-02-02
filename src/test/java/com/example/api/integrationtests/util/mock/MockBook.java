package com.example.api.integrationtests.util.mock;

import java.time.LocalDate;

import com.example.api.integrationtests.util.vo.v1.BookVO;

public class MockBook {

    public static BookVO mockVO() {
        BookVO bookVO = new BookVO();
        bookVO.setTitle("O Código Da Vinci");
		bookVO.setAuthor("Dan Brown");
		bookVO.setPrice(35.46);
		bookVO.setLaunchDate(LocalDate.of(2021, 04, 15));
        return bookVO;
    }
    
}
