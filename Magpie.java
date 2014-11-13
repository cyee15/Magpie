import java.util.Random;
/**
 * A program to carry on conversations with a human user.
 * This is the initial version that:  
 * <ul><li>
 *       Uses indexOf to find strings
 * </li><li>
 *       Handles responding to simple words and phrases 
 * </li></ul>
 * This version uses a nested if to handle default responses.
 * @author Laurie White
 * @version April 2012
 */
public class Magpie
{
  /**
   * Get a default greeting  
   * @return a greeting
   */
  public String getGreeting()
  {
    return "Hello, let's talk.";
  }
  
  /**
   * Gives a response to a user statement
   * 
   * @param statement
   *            the user statement
   * @return a response based on the rules given
   */
  public String getResponse(String statement)
  {
    statement = statement.toLowerCase();//changes upper cased characters to lower cased characters
    String response = "";
    if (statement.length() == 0) //Nothing is written
    {
      response = "Why so negative?";
    }
    else if (findKeyword(statement, "mother", 0) >= 0 //Keywords triggers automatic response
               || findKeyword(statement, "father", 0) >= 0
               || findKeyword(statement, "sister", 0) >= 0
               || findKeyword(statement, "brother", 0) >= 0)
    {
      response = "Tell me more about your family.";
    }
    else if (findKeyword(statement, "dog", 0) >= 0//Keywords triggers automatic response
               || findKeyword(statement, "cat", 0) >= 0)
    {
      response = "Tell me more about your pets.";//Keywords triggers automatic response
    }
    else if (findKeyword(statement, "landgraf", 0) >= 0
               || findKeyword(statement, "kiang", 0) >= 0)
    {
      response = "Oh, I've heard of him. He's everyone's favorite professional computer programmer.";
    }
    else if (findKeyword(statement, "minecraft", 0) >= 0//Keywords triggers automatic response
               || findKeyword(statement, "monopoly", 0) >= 0
               || findKeyword(statement, "solitare", 0) >= 0)
    {
      response = "I love to play that game.";
    }
    
     else if (findKeyword(statement, "Hello", 0) >= 0
                || findKeyword(statement, "Hi", 0) >=0)
     {
       response = "How are you?";
     }
     
     else if (findKeyword(statement, "How are you?", 0) >= 0)
     {
       response = "I am fine. You?";
     }
                
     // Responses which require transformations
  else if (findKeyword(statement, "I want", 0) >= 0)// calls IWantToStatement method, transforms statement into question
  {
   response = transformIWantToStatement(statement);
  }

  else if (findKeyword(statement, "I", 0) >= 0)// calls YouMeStatement method, transforms statement into question
  {
   // Look for a two word (you <something> me) pattern
   int psn = findKeyword(statement, "I", 0);

   if (psn >= 0
     && findKeyword(statement, "you", psn) >= 0)
   {
    response = transformYouMeStatement(statement);
   }
   
   else
   {
     response = "Okay";
   }
  }
   
  else if (findKeyword(statement, "is", 0)>= 0)// calls IsStatmentToQuestion, transforms statement into question
  {
  response = transformIsStatementToQuestion(statement);
  }
  
  else if (findKeyword(statement, "are", 0)>= 0)// calls AreStatmentToQuestion, transforms statement into question
  {
    response = transformAreStatementToQuestion(statement);
  }
     
  else//generates random responses if Keywords are not triggered
   {
    response = getRandomResponse();
   }
  
  return response;
 }
 
private String transformIsStatementToQuestion(String statement)//turns "is" statement into question
{  
 //removes period
  statement = statement.trim();
  String lastChar = statement.substring(statement
    .length() - 1);
  if (lastChar.equals("."))
  {
   statement = statement.substring(0, statement
     .length() - 1);
  }
  
  int psnOfis = findKeyword (statement, "is", 0);//Finds is
  String restOfStatement = statement.substring(0, psnOfis) + statement.substring(psnOfis +2).trim();//removes "is" from statment
  return "Why is " + restOfStatement + "?"; // To make statment into question, move "is" to front of statement and add "Why" beginning and add "?" to end
}   
   

private String transformAreStatementToQuestion(String statement)//truns "are" statment into Question, takes "you" into consideration
{  
 //remove period
  statement = statement.trim();
  String lastChar = statement.substring(statement
    .length() - 1);
  if (lastChar.equals("."))
  {
   statement = statement.substring(0, statement
     .length() - 1);
  } 

  
  int psnOfare = findKeyword (statement, "are", 0);//Find are
  if (findKeyword(statement, "are", 0) >= 0 && findKeyword(statement, "you", 0) < 0)//If the statement has "are" in it, and it does not contain "you"
  {
  String restOfStatement = statement.substring(0, psnOfare) + statement.substring(psnOfare +3).trim();//removes are from statment
  return "Why are " + restOfStatement + "?"; //move the are to the front and put why in front of it.
  }
  else//If the statement has "are" and "you" in it
  {
String restOfStatement = statement.substring(psnOfare + 3);//To change Are with You statement to question, get rid of words infront of "are" and add "Why am I" to beginning.
  return "Why am I " + restOfStatement + "?"; //Add "Why am I" to beginning
  }
}
   
      
 /**
  * Take a statement with "I want to <something>." and transform it into 
  * "What would it mean to <something>?"
  * @param statement the user statement, assumed to contain "I want to"
  * @return the transformed statement
  */
 private String transformIWantToStatement(String statement)//turns "I want to" statment into question
 {
  //  Remove the final period, if there is one
  statement = statement.trim();
  String lastChar = statement.substring(statement
    .length() - 1);
  if (lastChar.equals("."))
  {
   statement = statement.substring(0, statement
     .length() - 1);
  }
  int psn = findKeyword (statement, "I want", 0); 
  String restOfStatement = statement.substring(psn + 6).trim(); //removes any text before "I want" and removes "I want"
  return "Would you really be happy if you had " + restOfStatement + "?";//asks player to explain thoughts
 }

 /**
  * Take a statement with "you <something> me" and transform it into 
  * "What makes you think that I <something> you?"
  * @param statement the user statement, assumed to contain "you" followed by "me"
  * @return the transformed statement
  */
 private String transformYouMeStatement(String statement)//turns "you" and "I" statement into question
 {
  //  Remove the final period, if there is one
  statement = statement.trim();
  String lastChar = statement.substring(statement
    .length() - 1);
  if (lastChar.equals("."))
  {
   statement = statement.substring(0, statement
     .length() - 1);
  }
  
  int psnOfYou = findKeyword (statement, "I", 0);//Finds "I" and "you"
  int psnOfMe = findKeyword (statement, "you", psnOfYou + 1);
  
  String restOfStatement = statement.substring(psnOfYou + 1, psnOfMe).trim();//removes all text except the text between "I" and "you" 
  return "Why do you " + restOfStatement + " me?";//inserts the text between "I" and "you" into texts "Why do you " and "me?"
  }
  
 
 
  private int findKeyword(String statement, String goal,int startPos) //finds and returns psn
  {
    String phrase = statement.trim();
    // The only change to incorporate the startPos is in
    // the line below
    int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
    
    // Refinement--make sure the goal isn't part of a
    // word
    while (psn >= 0)
    {
      // Find the string of length 1 before and after
      // the word
      String before = " ", after = " ";
      if (psn > 0)
      {
        before = phrase.substring(psn - 1, psn)
          .toLowerCase();
      }
      if (psn + goal.length() < phrase.length())
      {
        after = phrase.substring(psn + goal.length(),psn + goal.length() + 1).toLowerCase();
      }
      
      // If before and after aren't letters, we've
      // found the word
      if (((before.compareTo("a") < 0) || (before.compareTo("z") > 0)) // before is not a
            // letter
            && ((after.compareTo("a") < 0) || (after.compareTo("z") > 0)))
      {
        return psn;
      }
      
      // The last position didn't work, so let's find
      // the next, if there is one.
      psn = phrase.indexOf(goal.toLowerCase(),psn + 1);
      
    }
    
    return -1;
  }
  

 private String getRandomResponse ()//a default response to use if nothing else fits.
 {
  Random r = new Random ();
  return randomResponses [r.nextInt(randomResponses.length)];
 }
 
 private String [] randomResponses = {"Interesting, tell me more",//random responses are condensed
   "Hmmm.",
   "Do you really think so?",
   "You don't say.",
    "I love you.",
   "Stop talking to me. You're boring.",
   "You are a sad humnan.",
   "Why do you say that?",
   "And?",
   "You should go do homework now."
   
 };
   
//expanded code
//a default response to use if nothing else fits
 // private String getRandomResponse()
  {
   // final int NUMBER_OF_RESPONSES = 6;
    // double r = Math.random();
    //int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
    //String response = "";
    
    //if (whichResponse == 0)
    {
     // response = "Interesting, tell me more.";
    }
    //else if (whichResponse == 1)
    {
     // response = "Hmmm.";
    }
    //else if (whichResponse == 2)
    {
      //response = "Do you really think so?";
    }
    //else if (whichResponse == 3)
    {
      //response = "You don't say.";
    }
    //else if (whichResponse == 4)
    {
     // response = "I love you";
    }
    //else if (whichResponse == 5)
    {
     // response = "Stop talking to me. You're boring.";
    }
    
    //return response;
  }
  
  
  
   private String expandContraction(String statement)//epands Contractions
  {
    String temp = statement;
   
    //expands can't <- exception
    while (findKeyword(temp, "can't",0) >= 0) {
      int psn = findKeyword(temp, "can't", 0); //the position of the contraction
      String beginning = temp.substring(0,psn); //the piece before the contraction
      String contraction = "can't"; //the contraction
      String end = temp.substring(psn + 5); // the piece after the contraction
      contraction = contraction.substring(0,contraction.length() - 2) + "not"; //expands the contraction
      temp = beginning + contraction + end; //pieces together the new contraction
    }
   
    //expands I'm <- exception
    while (findKeyword(temp, "I'm", 0) >= 0) {
      int psn = findKeyword(temp, "I'm", 0); //the position of the contraction
      String beginning = temp.substring(0,psn); //the piece before the contraction
      String contraction = "I'm"; //the contraction
      String end = temp.substring(psn + 3); // the piece after the contraction
      contraction = contraction.substring(0,contraction.length() - 2) + " am"; //expands the contraction
      temp = beginning + contraction + end; //pieces together the new contraction
    }
   
    String [] nPattern = { // the words that follow the pattern <something>n't = <something> not
      "aren't",
      "couldn't",
      "didn't",
      "doesn't",
      "don't",
      "hadn't",
      "hasn't",
      "haven't",
      "shouldn't",
      "weren't",
      "wouldn't",
      "isn't",
      "mustn't",
      "mightn't"
    };
   
    //expands all the contractions with the <something>n't pattern
    for (int i = 0; i < nPattern.length; i++) {
      while (findKeyword(temp, nPattern[i], 0) >= 0) { //while there are still contractions
        int psn = findKeyword(temp, nPattern[i], 0); //the position of the contraction
        String beginning = temp.substring(0,psn); //the piece before the contraction
        String contraction = temp.substring(psn,psn + nPattern[i].length()); //the contraction
        String end = temp.substring(psn + contraction.length()); // the piece after the contraction
        contraction = contraction.substring(0,contraction.length() - 3) + " not"; //expands the contraction
        temp = beginning + contraction + end; //pieces together the new contraction
      }
    }
   
    String [] rePattern = { // the words that follow the pattern <something>'re = <something> are
      "you're",
      "we're",
      "they're"
    };
   
    //expands all the contractions with the <something>'re pattern
    for (int i = 0; i < rePattern.length; i++) {
      while (findKeyword(temp, rePattern[i], 0) >= 0) { //while there are still contractions
        int psn = findKeyword(temp, rePattern[i], 0); //the position of the contraction
        String beginning = temp.substring(0,psn); //the piece before the contraction
        String contraction = temp.substring(psn,psn + rePattern[i].length()); //the contraction
        String end = temp.substring(psn + contraction.length()); // the piece after the contraction
        contraction = contraction.substring(0,contraction.length() - 3) + " are"; //expands the contraction
        temp = beginning + contraction + end; //pieces together the new contraction
      }
    }
   
    String [] sPattern = { // the words that follow the pattern <something>'s = <something> is
      "he's",
      "she's",
      "it's",
      "that's",
      "who's",
      "what's",
      "when's",
      "where's",
      "why's",
      "how's"
    };
   
    //expands all the contractions with the <something>'s pattern
    for (int i = 0; i < sPattern.length; i++) {
      while (findKeyword(temp, sPattern[i], 0) >= 0) { //while there are still contractions
        int psn = findKeyword(temp, sPattern[i], 0); //the position of the contraction
        String beginning = temp.substring(0,psn); //the piece before the contraction
        String contraction = temp.substring(psn,psn + sPattern[i].length()); //the contraction
        String end = temp.substring(psn + contraction.length()); // the piece after the contraction
        contraction = contraction.substring(0,contraction.length() - 2) + " is"; //expands the contraction
        temp = beginning + contraction + end; //pieces together the new contraction
      }
    }
   
    String [] willPattern = { // the words that follow the pattern <something>'ll = <something> will
      "I'll",
      "you'll",
      "he'll",
      "she'll",
      "it'll",
      "we'll",
      "they'll",
      "that'll",
      "who'll",
      "what'll",
      "when'll",
      "where'll",
      "why'll",
      "how'll"
    };
   
    //expands all the contractions with the <something>'ll pattern
    for (int i = 0; i < willPattern.length; i++) {
      while (findKeyword(temp, willPattern[i],0) >= 0) { //while there are still contractions
        int psn = findKeyword(temp, willPattern[i],0); //the position of the contraction
        String beginning = temp.substring(0,psn); //the piece before the contraction
        String contraction = temp.substring(psn,psn + willPattern[i].length()); //the contraction
        String end = temp.substring(psn + contraction.length()); // the piece after the contraction
        contraction = contraction.substring(0,contraction.length() - 3) + " will"; //expands the contraction
        temp = beginning + contraction + end; //pieces together the new contraction
      }
    }
   
    String [] vePattern = { // the words that follow the pattern <something>'ve = <something> have
      "I've",
      "you've",
      "we've",
      "they've",
      "would've",
      "should've",
      "could've",
      "might've",
      "must've"
    };
   
    //expands all the contractions with the <something>'ve pattern
    for (int i = 0; i < vePattern.length; i++) {
      while (findKeyword(temp, vePattern[i], 0) >= 0) { //while there are still contractions
        int psn = findKeyword(temp, vePattern[i], 0); //the position of the contraction
        String beginning = temp.substring(0,psn); //the piece before the contraction
        String contraction = temp.substring(psn,psn + vePattern[i].length()); //the contraction
        String end = temp.substring(psn + contraction.length()); // the piece after the contraction
        contraction = contraction.substring(0,contraction.length() - 3) + " have"; //expands the contraction
        temp = beginning + contraction + end; //pieces together the new contraction
      }
    }
   
    return temp;
  }
}
