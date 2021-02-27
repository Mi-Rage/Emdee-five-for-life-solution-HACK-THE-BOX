# Emdee-five-for-life-solution-HACK-THE-BOX
The program for getting the secret flag from the site, the Hack The Box challenge.

Set the desired IP in the TARGET_URL variable.

We need to get the User-Agent and Cookie fields. I got them using Firefox and Burp. We will use these values in GET and POST requests. Without them, we see the message " Too slow!".

I took the source text by the indexes of the response string to the GET request, you can take RegEx, the code is commented out.

We need to use the dependency commons-codec-1.9.jar to convert text to MD5.

It's time to send a POST request with our headers and the MD5 value. The key for the MD5 value can be seen in the source code of the page.

In the response to the POST request, we will see the desired flag. Enjoy!
