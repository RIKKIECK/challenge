package cl.meli.api.api.controller;

import cl.meli.api.api.redis.ShortUrl;
import cl.meli.api.api.service.ShortInterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

public class ShortController {

    @Autowired
    private ShortInterService shortInterService;

    @GetMapping("/u/{key}")
    public ResponseEntity<String> getUrl(@PathVariable String key) {
        String originalUrl = shortInterService.getOriginalUrlByKey(key);
        return ResponseEntity.ok(originalUrl);
    }

    @GetMapping("/r/{key}")
    public ResponseEntity<String> redirectToUrl(@PathVariable String key) {
        String originalUrl = shortInterService.getOriginalUrlByKey(key);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).headers(headers).body(originalUrl);
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrl> shortenUrl(@RequestParam String url) {
        ShortUrl shortUrl = shortInterService.shortenUrl(url);
        return ResponseEntity.ok(shortUrl);
    }

}
