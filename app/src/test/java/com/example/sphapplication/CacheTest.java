package com.example.sphapplication;

import android.accounts.NetworkErrorException;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOError;
import java.io.IOException;

public class CacheTest {

    @Test(expected = JSONException.class)
    public void testFailures() throws Exception {
        SPHService sphService = new SPHService();
        sphService.parseJSON(null);
        sphService.parseJSON("{result: {}}");
        sphService.parseJSON("{result: {records: null}}");
    }

    @Test(expected = NullPointerException.class)
    public void testFailuress() throws Exception {
        MainActivity mainActivity = new MainActivity();
        mainActivity.showCachedResponse();
        Assert.fail("Should have thrown ioexception");
    }
}
