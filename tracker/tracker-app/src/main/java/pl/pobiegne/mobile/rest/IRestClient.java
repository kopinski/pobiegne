package pl.pobiegne.mobile.rest;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;

@Rest(rootUrl = "http://10.0.2.2:54218/", converters = { MappingJacksonHttpMessageConverter.class })
public interface IRestClient {
    @Post("/Routes/Upload/")
    @Accept(MediaType.APPLICATION_JSON)
    String addRoute(String xml);
}