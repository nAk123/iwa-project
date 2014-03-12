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

    public static String parseText(String tweet) throws JSONException {
        JSONObject json = new JSONObject(tweet);

        JSONObject jsonText = json.getJSONObject("text");
        String text = jsonText.getString("text");
        return text;
    }
}
