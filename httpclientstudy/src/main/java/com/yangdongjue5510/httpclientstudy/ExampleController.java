package com.yangdongjue5510.httpclientstudy;

import com.yangdongjue5510.httpclientstudy.dto.RequestDto;
import com.yangdongjue5510.httpclientstudy.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class ExampleController {

    @GetMapping("/sync")
    public ResponseEntity<ResponseDto> getSync() {
        final ResponseDto responseDto = new ResponseDto("GET sync request success");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/sync")
    public ResponseEntity<ResponseDto> postSync(@RequestBody final RequestDto requestDto) {
        final ResponseDto responseDto = new ResponseDto(requestDto.getMessage() + " success");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/slow-task")
    public ResponseEntity<Void> getSlowTask(@RequestParam int id) throws InterruptedException {
        log.info("[ID = {}] Slow task started", id);
        Thread.sleep(2000L);
        log.info("[ID = {}] Slow task ended", id);
        return ResponseEntity.ok().build();
    }
}
