/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.core.internal.paths;

import org.assertj.core.internal.PathsBaseTest;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.error.ShouldEndWithPath.shouldEndWith;
import static org.assertj.core.test.TestFailures.wasExpectingAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("AutoBoxing")
public class Paths_assertEndsWithRaw_Test
    extends PathsBaseTest
{
    private Path actual;
    private Path other;

    @Before
    public void init()
    {
        actual = mock(Path.class);
        other = mock(Path.class);
    }

    @Test
    public void should_fail_if_actual_is_null()
    {
        thrown.expectAssertionError(actualIsNull());
        paths.assertEndsWithRaw(info, null, other);
    }

    @Test
    public void should_fail_if_other_is_null()
    {
        try {
            paths.assertEndsWithRaw(info, actual, null);
            fail("expected a NullPointerException here");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "other should not be null");
        }
    }

    @Test
    public void should_fail_if_actual_does_not_end_with_other()
    {
        // This is the default, but let's make this explicit
        when(actual.endsWith(other)).thenReturn(false);

        try {
            paths.assertEndsWithRaw(info, actual, other);
            wasExpectingAssertionError();
        } catch (AssertionError e) {
            verify(failures).failure(info, shouldEndWith(actual, other));
        }
    }

    @Test
    public void should_succeed_if_actual_ends_with_other()
    {
        when(actual.endsWith(other)).thenReturn(true);

        paths.assertEndsWithRaw(info, actual, other);
    }
}
