package com.example.vramachandran.karma.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;

import com.example.vramachandran.karma.RobolectricGradleTestRunner;

import junit.framework.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by vramachandran on 9/20/2015.
 * DateUtils Tests.
 * Reference - http://www.vogella.com/tutorials/Robolectric/article.html
 */
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@RunWith(RobolectricGradleTestRunner.class)
public class DateUtilsTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    /**
     * Test Setup.
     */
    @Before
    public void setup() {
    }

    @Test
    public void daysBetweenTest() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        Assert.assertEquals(DateUtils.daysBetween(today, tomorrow), 1);
    }
}
