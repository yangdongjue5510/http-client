package com.yangdongjue5510.httpclientstudy;

import com.yangdongjue5510.httpclientstudy.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync")
public class ExampleSyncController {

    @GetMapping
    public ResponseEntity<Response> getSync() {
        final Response response = new Response("GET sync request success");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response> postSync() {
        final Response response = new Response("POST sync request success");
        return ResponseEntity.ok(response);
    }
}
