/*
 *  Copyright (c) 2019, Salesforce.com, Inc.
 *  All rights reserved.
 *  Licensed under the BSD 3-Clause license.
 *  For full license text, see LICENSE.txt file in the repo root  or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.reactorgrpc.stub;

import com.salesforce.reactivegrpc.common.AbstractClientStreamObserverAndPublisher;
import com.salesforce.reactivegrpc.common.Consumer;
import io.grpc.stub.CallStreamObserver;
import reactor.core.Fuseable;
import reactor.util.concurrent.Queues;

/**
 * TODO: Explain what this class does.
 * @param <T>
 */
class ReactorClientStreamObserverAndPublisher<T>
        extends AbstractClientStreamObserverAndPublisher<T> implements Fuseable.QueueSubscription<T>, Fuseable {

    ReactorClientStreamObserverAndPublisher(Consumer<CallStreamObserver<?>> onSubscribe) {
        super(Queues.<T>get(DEFAULT_CHUNK_SIZE).get(), onSubscribe);
    }

    ReactorClientStreamObserverAndPublisher(
            Consumer<CallStreamObserver<?>> onSubscribe,
            Runnable onTerminate) {
        super(Queues.<T>get(DEFAULT_CHUNK_SIZE).get(), onSubscribe, onTerminate);
    }

    @Override
    public int requestFusion(int requestedMode) {
        if ((requestedMode & Fuseable.ASYNC) != 0) {
            outputFused = true;
            return Fuseable.ASYNC;
        }
        return Fuseable.NONE;
    }
}
