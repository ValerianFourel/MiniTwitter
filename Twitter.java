

import java.util.ArrayList;

public class Twitter {

	//ADD YOUR CODE BELOW HERE
	 private MyHashTable<String, Tweet> byMessage;
	    private MyHashTable<String, ArrayList<Tweet>> byAuthor;
	    private MyHashTable<String, ArrayList<Tweet>> byDateAndTime;

	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
 			byMessage = new MyHashTable<String,Tweet>(100);
			byAuthor = new MyHashTable<String, ArrayList<Tweet>>(100);
			byDateAndTime = new MyHashTable<String, ArrayList<Tweet>>(100);

		//ADD CODE ABOVE HERE

			for(Tweet tweet: tweets){
				addTweet(tweet);
			}
	}


    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE
		if(this.byMessage.size() == 0) {
    		byMessage = new MyHashTable<String, Tweet>(1);
    		byMessage.put(t.getMessage(), t);
    	} else {
    		byMessage.put(t.getMessage(), t);
    	}
        if(byAuthor.size() == 0) {
        	byAuthor = new MyHashTable<String, ArrayList<Tweet>>(1);
        }
        if(byAuthor.get(t.getAuthor()) == null) {
        	ArrayList<Tweet> newTweet = new ArrayList<Tweet>();
        	newTweet.add(t);
        	byAuthor.put(t.getAuthor(), newTweet);
        } else {
        	byAuthor.get(t.getAuthor()).add(t);
        }
        if(byDateAndTime.size() == 0) {
        	byDateAndTime = new MyHashTable<String, ArrayList<Tweet>>(1);
        }
        if(byDateAndTime.get(t.getDateAndTime()) == null) {
        	ArrayList<Tweet> newTweet = new ArrayList<Tweet>();
        	newTweet.add(t);
        	byDateAndTime.put(t.getDateAndTime(), newTweet);
        } else {
        	byDateAndTime.get(t.getDateAndTime()).add(t);
        }
		//ADD CODE ABOVE HERE
	}


    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the
     * method returns null.
     * O(1)
     */
    public Tweet latestTweetByAuthor(String author) {
        //ADD CODE BELOW HERE

    	return byAuthor.get(author).get(byAuthor.get(author).size()-1);

        //ADD CODE ABOVE HERE
    }

    /**
     * Search this Twitter for Tweets by `date' and return an
     * ArrayList of all such Tweets. If there are no tweets on
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE


    	ArrayList<Tweet> DDay = new ArrayList<Tweet>();
    	for(int i =0; i<byDateAndTime.size();i++){

    		if(date.equals(truncate(byDateAndTime.keys().get(i),date.length()))){
    			System.out.println(i);
    			DDay.add((byDateAndTime.get(byDateAndTime.keys().get(i)).get(0)));

    			//System.out.println((byDateAndTime.get(byDateAndTime.keys().get(i))));
    		}


    	}



      		return DDay;

        //ADD CODE ABOVE HERE
    }






    private String truncate(String key, int length){

    	if(key.length()>length){
    		return key.substring(0, length);

    	} else {



    	return key;

    	}
    }

	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once.
	 */








 //   @SuppressWarnings("static-access")
	public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE

    //	MyHashTable topics = new MyHashTable<K,V>(100);
    //	byDateAndTime.slowSort((MyHashTable<K, V>) byDateAndTime);


	    MyHashTable<String, Integer> topics = new MyHashTable<String, Integer>(100);





    	ArrayList<String> trendingTweets = new ArrayList<String>();
    	ArrayList<String> sentence = new ArrayList<String>();
    	Integer value =0;

    	for(int i =0; i< byDateAndTime.size();i++){

    		sentence = getWords(byDateAndTime.get(byDateAndTime.keys().get(i)).toString());
    	sentence = removeDuplicates(sentence);
    	for(int k = 0; k<sentence.size();k++){
    		sentence.set(k, sentence.get(k).toLowerCase());
    		if(topics.get(sentence.get(k))==null){
    			value =1;
    		} else {
    			value = topics.get(sentence.get(k).toLowerCase())+1;
    		}
    		topics.put(sentence.get(k),value);
    	}

    	}

    	System.out.println(topics.size());
    	trendingTweets = topics.fastSort(topics);

    	for(int i = 0 ; i<20;i++){
    		System.out.println(trendingTweets.get(i)+"        "+topics.get(trendingTweets.get(i)));
    	}
    	return trendingTweets;

        //ADD CODE ABOVE HERE
    }




    private static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    /**
     * An helper method you can use to obtain an ArrayList of words from a
     * String, separating them based on apostrophes and space characters.
     * All character that are not letters from the English alphabet are ignored.
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;

    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

}
