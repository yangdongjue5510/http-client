package com.yangdongjue5510.httpclientstudy;

import com.yangdongjue5510.httpclientstudy.dto.RequestDto;
import com.yangdongjue5510.httpclientstudy.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync")
public class ExampleSyncController {

    @GetMapping
    public ResponseEntity<ResponseDto> getSync() {
        final ResponseDto responseDto = new ResponseDto("GET sync request success");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> postSync(@RequestBody final RequestDto requestDto) {
        final ResponseDto responseDto = new ResponseDto(requestDto.getMessage() + " success");
        return ResponseEntity.ok(responseDto);
    }
}
