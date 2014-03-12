/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iwa.twitter.utils;

import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 *
 * @author moviuro
 */
public class TweetUtils {

    public static JSONObject parseText(String tweet) throws JSONException {
        return new JSONObject(tweet).getJSONObject("text");
    }
}
