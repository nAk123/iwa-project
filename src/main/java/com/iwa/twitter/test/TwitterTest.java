/**
 * Copyright 2013 Twitter, Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package com.iwa.twitter.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterTest {

	// enter your keys
	private static final String CONSUMER_KEY = "ygOKsB9q0jkEz7GZ6Xw";
	private static final String CONSUMER_SECRET = "wD7DUq2SQc9g0OKZzhA4YcMSJ2UZq2c3sagFf1cfY";
	private static final String ACCESS_TOKEN = "127364996-SyODZbYeZ3osNWWhJ59g8eJNf9uQeM7oInxEEcvH";
	private static final String ACCESS_TOKEN_SECRET = "Ro2uR5laVvCqqxCXp7HsPOUDkPs2Jn42mc1LVCHcnucRA";

	private static Twitter twitter = null;
	private static Authentication auth = null;

	// construct Twitter instance using configuration
	static {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(CONSUMER_KEY);
		cb.setOAuthConsumerSecret(CONSUMER_SECRET);
		cb.setOAuthAccessToken(ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

		Configuration conf = cb.build();

		twitter = new TwitterFactory(conf).getInstance();
		auth = new OAuth1(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN,
				ACCESS_TOKEN_SECRET);
	}

	public static void streaming(String... track) throws InterruptedException {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		
		// add some track terms
		endpoint.trackTerms(Lists.newArrayList(track));

		// Create a new BasicClient. By default gzip is enabled.
		Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
				.endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();

		// Establish a connection
		client.connect();

		// Do whatever needs to be done with messages
		for (int msgRead = 0; msgRead < 1000; msgRead++) {
			String msg = queue.take();
			System.out.println(msg);
		}

		client.stop();
	}

	/**
	 * Gets list of trend names by Where On Earth ID
	 * 
	 * @param woeid
	 *            Where On Earth ID of the place to get trends
	 * @return List of trend names
	 * @throws TwitterException
	 */
	private static List<String> getTrendsByWoeid(int woeid)
			throws TwitterException {
		List<String> trendNames = new ArrayList<String>();
		@SuppressWarnings("deprecation")
		Trends trends = twitter.getLocationTrends(woeid);
		Trend[] trend = trends.getTrends();
		for (Trend t : trend) {
			trendNames.add(t.toString());
		}
		return trendNames;
	}

	public static void main(String[] args) {
		try {
			streaming("brazil", "india", "france");
			
			List<String> trendingTopics = getTrendsByWoeid(455831);
			
			for (String tweet : trendingTopics) {
				System.out.println(tweet);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}