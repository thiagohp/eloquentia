// Copyright 2013 Thiago H. de Paula Figueiredo
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// Copyright 2013 Thiago H. de Paula Figueiredo
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package br.com.arsmachina.eloquentia.tapestry.pages.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.ContentType;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.TextStreamResponse;

import br.com.arsmachina.eloquentia.controller.FileController;
import br.com.arsmachina.eloquentia.entity.File;

/**
 * Serves an uploaded file.
 * 
 * @author Thiago H. de Paula Figueiredo (http://machina.com.br/thiago)
 */
@ContentType("text/css")
public class Index {
	
	@Inject
	private FileController fileController;
	
	@Inject
	private Messages messages;
	
	Object onActivate(EventContext context) {
		Object returnValue = null;
		final String fileName = context.get(String.class, 0);
		File file = null;
		if (context.getCount() == 1) {
			file = fileController.findByName(fileName);
		}
		if (file == null) {
			 returnValue = new HttpError(404, messages.format("eloquentia.error-file-not-found", fileName));
		}
		else {
			return new FileStreamResponse(file);
		}
		return returnValue;
	}
	
	final private static class FileStreamResponse implements StreamResponse {
	    
	    private final File file;

        public FileStreamResponse(File file) {
            this.file = file;
        }

        @Override
        public String getContentType() {
            // FIXME: some better way of determining content types
        	final String contentType;
        	
        	final String name = file.getName().toLowerCase();
        	if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
        		contentType = "image/jpeg";
        	}
        	else if (name.endsWith(".png")) {
        		contentType = "image/png";
        	}
        	else if (name.endsWith(".gif")) {
        		contentType = "image/gif";
        	}
        	else if (name.endsWith(".svg") || name.endsWith(".svgz")) {
        		contentType = "image/svg+xml";
        	}
        	else if (name.endsWith(".pdf")) {
        		contentType = "application/pdf";
        	}
        	else {
        		contentType = "application/octet-stream";
        	}
            return contentType;
        }

        @Override
        public InputStream getStream() throws IOException {
            return new ByteArrayInputStream(file.getContent());
        }

        @Override
        public void prepareResponse(Response response) {
            response.setHeader("Content-Length", String.valueOf(file.getContent().length));
        }
	    
	}

}
