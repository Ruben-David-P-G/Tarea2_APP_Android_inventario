package mx.com.ecube.inventory.inventory_android;

import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class CustomStringRequest extends StringRequest {
    private Map<String, String> params;

    public CustomStringRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener, Map<String, String> params) {
        super(method, url, listener, errorListener);
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }
}
