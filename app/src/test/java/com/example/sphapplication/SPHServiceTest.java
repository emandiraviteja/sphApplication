package com.example.sphapplication;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SPHServiceTest {

    @Test(expected = JSONException.class)
    public void testFailures() throws Exception {
        SPHService sphService = new SPHService();
        sphService.parseJSON(null);
        sphService.parseJSON("{result: {}}");
        sphService.parseJSON("{result: {records: null}}");
    }

    @Test
    public void testParseJSON() throws Exception {
        SPHService sphService = new SPHService();
        assertEquals(sphService.parseJSON("{\"result\": {\"records\": [{\"volume_of_mobile_data\": \"1\", \"quarter\": \"2004-q1\", \"id\": \"2\" }]}}").size(), 1);
        //Different quaters of same year should return 1 record.
        assertEquals(sphService.parseJSON("{\"result\": {\"records\": [{\"volume_of_mobile_data\": \"1\", \"quarter\": \"2004-q1\", \"id\": \"2\" }, {\"volume_of_mobile_data\": \"1\", \"quarter\": \"2004-q2\", \"id\": \"2\" }]}}").size(), 1);
        //Quarters from tow years should return 2 records.
        assertEquals(sphService.parseJSON("{\"result\": {\"records\": [{\"volume_of_mobile_data\": \"1\", \"quarter\": \"2004-q1\", \"id\": \"2\" }, {\"volume_of_mobile_data\": \"1\", \"quarter\": \"2004-q2\", \"id\": \"2\" }, {\"volume_of_mobile_data\": \"1\", \"quarter\": \"2005-q2\", \"id\": \"2\" }]}}").size(), 2);
    }

    @Test
    public void testDeline() throws JSONException {
        SPHService sphService = new SPHService();
        TreeMap<String, YearListItem> yearList = sphService.parseJSON("{\"result\": {\"records\": [{\"volume_of_mobile_data\": \"1\", \"quarter\": \"2004-q4\", \"id\": \"2\" }, {\"volume_of_mobile_data\": \"0.5\", \"quarter\": \"2005-q1\", \"id\": \"2\" }]}}");
        assertFalse(yearList.get("2004").isDecilned());
        assertTrue(yearList.get("2005").isDecilned());
    }

    /*@Test
    public void testul(){
        MainActivity mainActivity = new MainActivity();
        IOException ioException = new IOException();
        mainActivity.showError(ioException);
    }*/
}
