package com.teletronics.ms.service;

import io.sixhours.memcached.cache.MemcachedCacheProperties;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.ClientMode;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MemcachedHealthIndicator implements HealthIndicator {

    private static final String NO_AVAILABLE_MEMCACHED_SERVERS_CAN_BE_FOUND = "No available memcached servers can be found";
    private static final String ONE_OR_MORE_MEMCACHED_SERVERS_IS_UNAVAILABLE = "One or more memcached servers is unavailable: ";
    private static final String UNABLE_TO_DETERMINE_MEMCACHED_SERVER_STATUS = "Unable to determine memcached server status.";
    private static final String MESSAGE = "message";
    private final MemcachedCacheProperties properties;
    private MemcachedClient client;

    public MemcachedHealthIndicator(MemcachedCacheProperties properties) {
        this.properties = properties;
    }

    @Override
    public Health health() {
        try {
            initMemcachedClient();

            if (client.getAvailableServers().isEmpty()) {
                log.warn(NO_AVAILABLE_MEMCACHED_SERVERS_CAN_BE_FOUND);
                return Health.down().outOfService().withDetail(MESSAGE, NO_AVAILABLE_MEMCACHED_SERVERS_CAN_BE_FOUND).build();
            }

            Collection<SocketAddress> unavailableServers = client.getUnavailableServers();

            if (!unavailableServers.isEmpty()) {
                String description = ONE_OR_MORE_MEMCACHED_SERVERS_IS_UNAVAILABLE + unavailableServers;
                log.warn(description);
                return Health.down().down().withDetail(MESSAGE, description).build();
            }

            refreshConnectionStatus(client);

        } catch (Exception e) {
            client = null;

            log.warn(UNABLE_TO_DETERMINE_MEMCACHED_SERVER_STATUS);

            return Health.down().down().withException(e)
                    .withDetail(MESSAGE, UNABLE_TO_DETERMINE_MEMCACHED_SERVER_STATUS).build();
        }
        return Health.up().build();
    }

    private void initMemcachedClient() throws IOException {
        if (Objects.isNull(client))
            this.client = getNewMemcachedClient();
    }

    private MemcachedClient getNewMemcachedClient() throws IOException {
        final List<InetSocketAddress> servers = properties.getServers();
        final ClientMode mode = properties.getMode();
        final MemcachedCacheProperties.Protocol protocol = properties.getProtocol();

        final ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder()
                .setClientMode(mode)
                .setOpTimeout(10000)// 10 seconds
                .setProtocol(protocol.value());

        return new MemcachedClient(connectionFactoryBuilder.build(), servers);
    }

    private void refreshConnectionStatus(MemcachedClient client) {
        client.flush();
    }
}