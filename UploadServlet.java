package com.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet{
	
	BlobstoreService blobstore = BlobstoreServiceFactory.getBlobstoreService();
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.getRequestDispatcher("/WEB-INF/uploadForm.jsp").forward(req, resp);
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, List<BlobKey>> blobs = blobstore.getUploads(req);
        List<BlobKey> blobKeys = blobs.get("updateFile");

        if (blobKeys == null || blobKeys.isEmpty()) {
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/success?blob-key=" + blobKeys.get(0).getKeyString());
        }
	}

}
