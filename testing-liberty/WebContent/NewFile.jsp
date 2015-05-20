<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>



<%@page import="java.io.*" %>
<%@page import="java.net.*" %>
<%@page import="java.net.HttpURLConnection" %>
<%@page import="javax.net.ssl.HttpsURLConnection" %>
<%@page import="java.net.URL" %>
<%
String t = request.getParameter("api");
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
else if(t.equals("delete_user")){
    out.println("delete user");
}  
%>
