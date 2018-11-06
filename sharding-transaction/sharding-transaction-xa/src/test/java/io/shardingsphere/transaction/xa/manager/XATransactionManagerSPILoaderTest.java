/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
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
 * </p>
 */

package io.shardingsphere.transaction.xa.manager;

import io.shardingsphere.core.exception.ShardingException;
import io.shardingsphere.spi.NewInstanceServiceLoader;
import io.shardingsphere.transaction.manager.xa.XATransactionManager;
import io.shardingsphere.transaction.xa.fixture.ReflectiveUtil;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public final class XATransactionManagerSPILoaderTest {
    
    @Test
    public void assertGerInstanceWithSPI() {
        assertThat(XATransactionManagerSPILoader.getInstance().getTransactionManager(), instanceOf(AtomikosTransactionManager.class));
    }
    
    @Test(expected = ShardingException.class)
    @SuppressWarnings("unchecked")
    public void assertServiceLoaderFailed() {
        XATransactionManagerSPILoader spiLoader = XATransactionManagerSPILoader.getInstance();
        NewInstanceServiceLoader<XATransactionManager> serviceLoader = mock(NewInstanceServiceLoader.class);
        doThrow(ShardingException.class).when(serviceLoader).newServiceInstances();
        ReflectiveUtil.setProperty(spiLoader, "serviceLoader", serviceLoader);
        ReflectiveUtil.methodInvoke(spiLoader, "load");
    }
}
