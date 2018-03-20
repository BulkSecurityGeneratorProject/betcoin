package com.betcoin.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.betcoin.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.betcoin.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.betcoin.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.betcoin.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.betcoin.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
            cm.createCache(com.betcoin.domain.Team.class.getName(), jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Pronotype.class.getName(), jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Group.class.getName(), jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Match.class.getName(), jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Gamer.class.getName(), jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Competition.class.getName(), jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Pronostic.class.getName(), jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Gamer.class.getName() + ".pronostics", jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Group.class.getName() + ".teams", jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Team.class.getName() + ".matchshomes", jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Team.class.getName() + ".matchsaways", jcacheConfiguration);
            cm.createCache(com.betcoin.domain.Pronotype.class.getName() + ".pronostics", jcacheConfiguration);
        };
    }
}
