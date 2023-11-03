package com.kh.spring22.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

	private int bookId, bookPageCount, bookPrice;
	private String bookTitle, bookAuthor, bookPublicationDate, bookPublisher, bookGenre;

	@Schema(hidden = true)
	public boolean isEmpty() {
		return bookAuthor == null && bookGenre == null && bookId == 0 && bookPageCount == 0 && bookPrice == 0
				&& bookPublicationDate == null && bookPublisher == null && bookTitle == null;
	}
}
