/*
 * Copyright 2014 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.reactivex.netty.servo;

import io.reactivex.netty.client.ClientMetricsEvent;
import io.reactivex.netty.client.RxClient;
import io.reactivex.netty.metrics.MetricEventsListenerFactory;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.udp.client.UdpClient;
import io.reactivex.netty.protocol.udp.server.UdpServer;
import io.reactivex.netty.server.RxServer;
import io.reactivex.netty.server.ServerMetricsEvent;
import io.reactivex.netty.servo.http.HttpClientListener;
import io.reactivex.netty.servo.http.HttpServerListener;
import io.reactivex.netty.servo.tcp.TcpClientListener;
import io.reactivex.netty.servo.tcp.TcpServerListener;
import io.reactivex.netty.servo.udp.UdpClientListener;
import io.reactivex.netty.servo.udp.UdpServerListener;

/**
 * @author Nitesh Kant
 */
public class ServoEventsListenerFactory implements MetricEventsListenerFactory {

    private final String clientMetricNamePrefix;
    private final String serverMetricNamePrefix;

    public ServoEventsListenerFactory() {
        this("rxnetty-client-", "rxnetty-server-");
    }

    public ServoEventsListenerFactory(String clientMetricNamePrefix, String serverMetricNamePrefix) {
        this.clientMetricNamePrefix = clientMetricNamePrefix;
        this.serverMetricNamePrefix = serverMetricNamePrefix;
    }

    @Override
    public TcpClientListener<ClientMetricsEvent<ClientMetricsEvent.EventType>> forTcpClient(@SuppressWarnings("rawtypes") RxClient client) {
        return TcpClientListener.newListener(clientMetricNamePrefix + client.name());
    }

    @Override
    public HttpClientListener forHttpClient(@SuppressWarnings("rawtypes") HttpClient client) {
        return HttpClientListener.newHttpListener(clientMetricNamePrefix + client.name());
    }

    @Override
    public UdpClientListener forUdpClient(@SuppressWarnings("rawtypes") UdpClient client) {
        return UdpClientListener.newUdpListener(clientMetricNamePrefix + client.name());
    }

    @Override
    public TcpServerListener<ServerMetricsEvent<ServerMetricsEvent.EventType>> forTcpServer( @SuppressWarnings("rawtypes") RxServer server) {
        return TcpServerListener.newListener(serverMetricNamePrefix + server.getServerPort());
    }

    @Override
    public HttpServerListener forHttpServer(@SuppressWarnings("rawtypes") HttpServer server) {
        return HttpServerListener.newHttpListener(serverMetricNamePrefix + server.getServerPort());
    }

    @Override
    public UdpServerListener forUdpServer(@SuppressWarnings("rawtypes") UdpServer server) {
        return UdpServerListener.newUdpListener(serverMetricNamePrefix + server.getServerPort());
    }
}
