/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iwa.twitter.model.beans;

import com.iwa.twitter.utils.TweetUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 *
 * @author moviuro
 */
public class Tweet {
    private String text;
    private int id;
    private Date date;

    public Tweet(String tweet) throws JSONException, ParseException{
        JSONObject objectToExtractInfo = TweetUtils.parseText(tweet);
        this.text = objectToExtractInfo.getString("text");
        this.id   = objectToExtractInfo.getInt("id");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
        this.date = df.parse(objectToExtractInfo.getString("created_at"));
    }
    
    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
    
    
}
