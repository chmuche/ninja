package ninja.template;

import java.io.IOException;

import org.slf4j.Logger;

import ninja.Context;
import ninja.Result;
import ninja.utils.ResponseStreams;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class TemplateEngineJsonGson implements TemplateEngine {

	private final Logger logger;
	
	@Inject
	public TemplateEngineJsonGson(Logger logger) {
		this.logger = logger;
		
	}
	
	
	@Override
    public void invoke(Context context, Result result) {

		
		
		ResponseStreams responseStreams = context.finalizeHeaders(result);
		
		Object object = result.getRenderable();
		
		
		Gson gson = new Gson();
		String json = gson.toJson(object);
		
		try {
			responseStreams.getWriter().write(json);
			responseStreams.getWriter().flush();
			responseStreams.getWriter().close();
			
		} catch (IOException e) {
			logger.error("Error while writing out Gson Json", e);
		}
	    
    }

	@Override
	public String getSuffixOfTemplatingEngine() {
		//does not use disk based templates...
		return null;
	}

    @Override
    public String getContentType() {
        return Result.APPLICATON_JSON;
    }
}
