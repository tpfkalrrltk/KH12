package com.kh.spring22.restcontroller;

import java.util.List;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring22.dao.PocketmonDao;
import com.kh.spring22.dto.PocketmonDto;

@CrossOrigin
//@CrossOrigin(value={"http://localhost:3000","http://localhost:5500"})
@RestController
@RequestMapping("/pocketmon")
public class PocketmonRestController {

// 아래 다섯개의 매핑을 이용하여 프론트엔드에 대응하는 Rest 기능을 구현
//	@GetMapping - 조회
//	@PostMapping - 등록
//	@PutMapping - 전체 수정
//	@PatchMapping - 일부 수정
//	@DeleteMapping - 삭제

	@Autowired
	private PocketmonDao pocketmonDao;

	@GetMapping("/")
	public List<PocketmonDto> list() {
		return pocketmonDao.selectList();
	}

	@PostMapping("/")
	// public void insert(@ModelAttribute PocketmonDto pocketmonDto) { //form-data
	// 수신용
	public void insert(
			@ParameterObject
			@RequestBody PocketmonDto pocketmonDto) { // request body 직접해석(ex : JSON)
		pocketmonDao.insert(pocketmonDto);
	}

	// 파라미터는 주소가 매우 지저분해지므로 최대한 경로변수를 활용
	@DeleteMapping("/{no}")
	// public boolean delete(@PathVariable int no) {//데이터를 반환하면 상태설정이 불가능
	public ResponseEntity<String> delete(@PathVariable int no) {// 상태 설정이 가능한 객체를 반환
		boolean result = pocketmonDao.delete(no);
		if (result) {
			// return ResponseEntity.ok().build();
			return ResponseEntity.status(200).build();
		} else {
			// return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).build();
		}
	}

	@GetMapping("/{no}")
	public ResponseEntity<PocketmonDto> find(@PathVariable int no) {
		PocketmonDto pocketmonDto = pocketmonDao.selelctOne(no);

		if (pocketmonDto != null) {
			// return ResponseEntity.ok(pocketmonDto);
			return ResponseEntity.ok().body(pocketmonDto);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping("/{no}")
	public ResponseEntity<String> edit(@PathVariable int no, @RequestBody PocketmonDto pocketmonDto) {
		boolean result = pocketmonDao.edit(no, pocketmonDto);
		return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@PatchMapping("/{no}")
	public ResponseEntity<String> editUnit(
			@PathVariable int no, @RequestBody PocketmonDto pocketmonDto) {
		if(pocketmonDto.isEmpty()) {
			ResponseEntity.badRequest().build();
		}
		boolean result = pocketmonDao.editUnit(no, pocketmonDto);
		return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
