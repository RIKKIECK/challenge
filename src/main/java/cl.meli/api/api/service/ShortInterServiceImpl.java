package cl.meli.api.api.service;

import cl.meli.api.api.redis.ShortUrl;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ShortInterServiceImpl implements ShortInterService{

    @Autowired
    Environment environment;
    @Autowired
    private RedisTemplate<String, ShortUrl> redisTemplate;

    @Override
    public String getOriginalUrlByKey(String key) {
        ShortUrl shortUrl = redisTemplate.opsForValue().get(key);
        if (shortUrl == null) return null;
        return shortUrl.getOriginalUrl();
    }

    @Override
    public ShortUrl shortenUrl(String originalUrl) {
        String key = Hashing.murmur3_32_fixed().hashString(originalUrl, Charset.defaultCharset()).toString();
        ShortUrl shortUrl = ShortUrl.builder().originalUrl(originalUrl).key(key).build();
        redisTemplate.opsForValue().set(key,
                shortUrl,
                environment.getProperty("redis.cache.expiration", Integer.class, 42600),
                TimeUnit.SECONDS);
        return shortUrl;
    }

}