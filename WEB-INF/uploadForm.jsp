<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
    <head>
        <title>Upload Test</title>
    </head>
    <body>
    	<h1>Update Movie Database with .tsv file</h1>
    	<a href="/">&laquo; Back to MovieMap</a>
    	
    	<p>Select a file and hit "upload" button.</p>
        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
            <input type="file" name="updateFile">
            <input type="submit" value="upload and update">
        </form>
    </body>
</html>
