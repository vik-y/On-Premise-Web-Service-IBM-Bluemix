Mobile app connecting to On-premise web service -  Bluemix Approach
===================

Rajesh K Jeyapaul , Cloud Architect , IBM<br>
Vikas Yadav , IIITB Bangalore<br>
Daksh Varshneya , IIITB Bangalore<br>
Suvarna Lingasai V Krishnakumar , IIITB Bangalore<br>

Scope:
---------
How Bluemix helps to  retrieve my confidential information which is available as a service in a secured environment ?

How my mobile application can access my on-premise web service running on a secured environment ?

In this Blog we would like to share our Bluemix experience of solving the above questions.

![Architecture Overview](https://raw.github.com/vik-y/On-Premise-Web-Service-IBM-Bluemix/master/assets/firewall.png)


Step 1:
---------
Bluemix Registration (Enjoy the Free 30 days trial and wealth of other free services )

http://ibm.biz/blueRajesh

or 

Bluemix.net

Step 2 : cloudfoundry command line installation and Bluemix login

1) cloudfoundry commandline “cf” download

https://github.com/cloudfoundry/cli 

2) download the stable binaries relevant to the operating system

3) Do the path setting

Windows 

```sh
Set PATH=<path where cf.exe is available>;%PATH%
set PATH=C:\myfolder\;%PATH%
```
Linux 
```sh
export PATH=<path where cf is available>:$PATH
export PATH=/home/myfolder:$PATH
```
Note: the same can be set in the .profile file as well which will be available in the login home directory


4) validate  
```sh
cf –v
cf version 6.9.0-620f841-2015-01-20T18:01:40+00:00
```
5) Bluemix Login using cf command line
```
C:\>myfolder/cf login
API endpoint> https://api.ng.bluemix.net 
```
OR (see the Note below)

https://api.eu-gb.bluemix.net
```
Email> xyz@abc.com     (Bluemix id and password)

Password>
Authenticating...
OK

Targeted org xyz@abc.com

Select a space (or press enter to skip):
1. dev
2. test
3. prod

Space> 1
Targeted space dev



API endpoint:   https://api.ng.bluemix.net (API version: 2.19.
User:           xyz@in.ibm.com
Org:            xyz@in.ibm.com
Space:          dev
```

Note: If the region is US South use the api: https://api.ng.bluemix.net


Step 3:
--------- 
Install and configure Bluemix Cloud integration service

Refer blog:
. https://www.ibm.com/developerworks/community/blogs/96960515-2ea1-4391-8170-b0515d08e4da/entry/cloud_to_on_premise_web_services_bluemix_cloud_integrators?lang=en

StackEdit stores your documents in your browser, which means all your documents are automatically saved locally and are accessible **offline!**


Step 4: 
---------
Push the server java application onto Bluemix

Requirement of the Java Application:
We are trying to secure the On-premise Web server and also limit the extent of access to different clients. To do this, we create a java application which has its API's that handles different kind of requests from the mobile application(Client). We define these API's in .jsp (say NewFile.jsp). 

The Java Application helps in retrieving my confidential information which is available as a service in a secured environment. Thus, my mobile application can access the on-premise web service running on a secured environment.

The request received from mobile application is redirected to the on-premise web server by the API (IP Address and Port number) provided by the Cloud Integration Service as in STEP 3. We took care of GET and POST in our Java Application, any further modifications can be done. 

Here's a very basic implementation of how requests will be forwarded.  
```java
if(t.equals("get_users")){
   String recv;
   String recvbuff = "";
   URL jsonpage = new URL("http://208.43.69.245:1649/users/userlist");
   URLConnection urlcon = jsonpage.openConnection();
   BufferedReader buffread = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
 
   while ((recv = buffread.readLine()) != null)
   	recvbuff += recv;
   buffread.close();
   out.println(recvbuff);
}
//GET Request
```
How it works? 
First the Java Application running on Bluemix receives a request from the client. 
```
→ UrlConnection urlcon = jsonpage.openConnection() 
```
Opens a new connection to the on Premise web server without the clients intervention.

```
else if(t.equals("add_user")){
    out.println("adding user");
    String url = "http://208.43.69.245:1649/users/adduser";
	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	//add reuqest header
	con.setRequestMethod("POST");
	con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	String username = request.getParameter("username");
	String email = request.getParameter("email");
	String fullname = request.getParameter("fullname");
	String age = request.getParameter("age");
	String location = request.getParameter("location");
	String gender = request.getParameter("gender");
	String urlParameters = "username="+username+"&email="+email+"&fullname="+fullname+"&age="+age+"&location="+location+"&gender="+gender;
	out.println(urlParameters);
	
	// Send post request
	con.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	wr.writeBytes(urlParameters);
	wr.flush();
	wr.close();
	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'POST' request to URL : " + url);
	System.out.println("Post parameters : " + urlParameters);
	System.out.println("Response Code : " + responseCode);
	BufferedReader in = new BufferedReader(
			new InputStreamReader(con.getInputStream()));
	String inputLine;
	//StringBuffer response = new StringBuffer();
	while ((inputLine = in.readLine()) != null) {
	//	response.append(inputLine);
	}
	in.close();
	//print result
	//System.out.println(response.toString());
}
//POST Request
```

Note: 
The address – http://208.43.69.245:1649/ is the public access end point we got from the cloud integration(Step 3) 

http://208.43.69.245:1649/users/userlist is the API which handles the GET request on the secure premise server.

http://208.43.69.245:1649/users/adduser is the API which handles the POST requet on the secure premise server.

Create the .war file of the Java Application

Download the starter code for liberty for Java and place the .jsp file that we created previously, in the Web-Content folder. Use 'ant' command  line utility to build the java project by issuing the command – 'ant build'. The output of this command will be a .war file which is essential to push the app to the bluemix environment.

Now change your working directory to the root directory of your java application.
Modify the manifest.yml file and issue the following command - 
```sh
cf push <Application Name> -p <war file name with extension>
```
Step 5: 
----------
Invoking the service through mobile application

Now we need to integrate the APIs which we made in Step 4 with our Mobile Application. 

The only difference here is that the url of API calls and access control (API key etc) change. And those are the changes which we made in Step5. 

Please note that there is nothing new in this method, it's same as the normal api calls but only the urls to which we are making calls are changed. 

Basic GET and POST request can be performed using in android using http libraries of apache. Here all the API requests will be sent to Bluemix which will in turn invoke its own API calls to get the data. 

Example: 

In our case, to get users, 
API Call from mobile :
```
GET
<Bluemix App URL>/NewFile.jsp?api=get_user 

//API call made by BlueMix to the On Premise Web Server: 
GET
<IP:port by Cloud Integration>/users/userlist
```
To add a user API Call by mobile :
```
POST
<Bluemix App URL>/NewFile.jsp?api=adduser
username=<username>&fullname=<fullname>&email=<email>&age=<age>&gender=<gender>&location=<location> 

//API Call made by Bluemix to the On Premise Web Server:
POST
<IP:port by Cloud Integration>/users/adduser
username=<username>&fullname=<fullname>&email=<email>&age=<age>&gender=<gender>&location=<location> 
```
This method also hides the actual API calls. 

Sample source code available at : https://github.com/vik-y/On-Premise-Web-Service-IBM-Bluemix


Happy Bluemix Experience!!
