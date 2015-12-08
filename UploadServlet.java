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

// TODO: Auto-generated Javadoc
/**
 * The Class UploadServlet.
 */
@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet{
	
	/** The blobstore. */
	BlobstoreService blobstore = BlobstoreServiceFactory.getBlobstoreService();
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.getRequestDispatcher("/WEB-INF/uploadForm.jsp").forward(req, resp);
    }
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
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
