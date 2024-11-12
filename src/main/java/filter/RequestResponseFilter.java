package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.RequestResponseContext;

import java.io.IOException;

@WebFilter("/*")
public class RequestResponseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        // Kiểm tra null trước khi lưu trữ
        if (req == null || resp == null) {
            throw new ServletException("Request or Response is null");
        }
        RequestResponseContext.set(req, resp);
        try {
            // Tiến hành xử lý yêu cầu
            chain.doFilter(request, response);
        } finally {
            // Đảm bảo rằng chỉ xóa request và response sau khi yêu cầu đã được xử lý hoàn tất
            RequestResponseContext.clear();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
