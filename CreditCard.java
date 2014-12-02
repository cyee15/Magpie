//Carolyn Yee

public class CreditCard
{  

public int findType(String num)
{
  if (num.substring(0,2).equals("51") ||  //if first two elements equal 51, 52, 53, 54, 55 then mastercard, return 1
     num.substring(0,2).equals("52") ||
      num.substring(0,2).equals("53") ||
      num.substring(0,2).equals("54") ||
      num.substring(0,2).equals("55"))
    return 1;
  else if (num.substring(0,1).equals("4")) //first element equals 4 then visa card
    return 2;
  else if (num.substring(0,4).equals("6011"))//first four elements equals 6011 then discover
    return 3;
  else if (num.substring(0,2).equals("34") ||
          num.substring(0,2).equals("37"))//first two elements equal 34, 37 then amex
    return 4;
  else if (num.substring(0,2).equals("36") ||
          num.substring(0,2).equals("38") ||//first two elements equal 36, 38 then carte blanch/binor's
          num.substring(0,3).equals("300") ||//or first three elements equals 300, 301, 302, 303, 304, 305 then carte blanch/binor's
          num.substring(0,3).equals("301") ||
          num.substring(0,3).equals("302") ||
          num.substring(0,3).equals("303") ||
          num.substring(0,3).equals("304") ||
          num.substring(0,3).equals("305"))
   return 5;
  else
    return 0;
}
  

// verify with Luhn Check

public boolean verify (String num)
{
  int sum = 0;
  boolean doubled = false; //first number is not doubled
  for (int i = num.length()-1;i >= 0; i--) { //runs through entire string
   if (doubled == false) //when number is not doubled, (when it is odd from end)
  {
    sum += Integer.valueOf(num.substring(i, i + 1));
    doubled = true;//makes next number even (and so doubled)    
  }
  else //when number is doubled, (when it is even from end)
  {
    int temp = 2 * Integer.valueOf(num.substring(i, i + 1));
    if (temp >= 10)//if temp is bigger than 9, add the two digits together
    {
      temp = 1 + temp % 10; //ten's digit always one, one's digit is the remainder of ten
    }
    sum += temp; //if temp is smaller than 10, add temp directly to sum
                 //if temp is larger than or equal to 10, temp is added to sum after adding digits
    doubled = false;//makes next number odd (and so not doubled)
  }
 }
 
 return sum % 10 == 0; //checking if sum is divisable by ten, if it is, will return true because credit card is valid
 }
   
}
  
  